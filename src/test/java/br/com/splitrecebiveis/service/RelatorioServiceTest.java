package br.com.splitrecebiveis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.splitrecebiveis.domain.RelatorioResumo;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.HistoricoRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;

@DisplayName("RelatorioService")
class RelatorioServiceTest {

    private static final LocalDate HOJE        = LocalDate.of(2027, 1, 1);
    private static final LocalDate DATA_REPASSE = HOJE.plusDays(1);

    private BeneficiarioService beneficiarioService;
    private RecebivelService    recebivelService;
    private SplitService        splitService;
    private RelatorioService    relatorioService;

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(
                HOJE.atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());

        beneficiarioService = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        recebivelService    = new RecebivelService(new RecebivelRepositoryMemoria());
        HistoricoService historicoService =
                new HistoricoService(new HistoricoRepositoryMemoria(), clock);
        splitService = new SplitService(new SplitRepositoryMemoria(),
                beneficiarioService, recebivelService, clock, historicoService);
        relatorioService = new RelatorioService(
                beneficiarioService, recebivelService, splitService, historicoService);
    }

    @Test
    @DisplayName("UT-91 sistema vazio devolve tudo zerado")
    void gerarResumo_sistemaVazio_tudoZerado() {
        RelatorioResumo resumo = relatorioService.gerarResumo();

        assertEquals(0, resumo.totalBeneficiarios());
        assertEquals(0, resumo.totalRecebiveis());
        assertEquals(0, resumo.totalSplits());
        assertEquals(0, BigDecimal.ZERO.compareTo(resumo.valorTotalRecebiveis()));
        assertEquals(0, resumo.operacoesRegistradas());
    }

    @Test
    @DisplayName("UT-92 conta beneficiários aprovados e pendentes separadamente")
    void gerarResumo_beneficiarios_aprovadosEPendentes() {
        beneficiarioService.cadastrar(
                "Maria", TipoPessoa.PF, "111.111.111-11", "001", "1234", "11111-1");
        beneficiarioService.aprovar(1L);
        beneficiarioService.cadastrar(
                "João", TipoPessoa.PF, "222.222.222-22", "001", "1234", "22222-2");

        RelatorioResumo resumo = relatorioService.gerarResumo();

        assertEquals(2, resumo.totalBeneficiarios());
        assertEquals(1, resumo.beneficiariosAprovados());
        assertEquals(1, resumo.beneficiariosPendentes());
    }

    @Test
    @DisplayName("UT-93 totaliza valor e saldo dos recebíveis")
    void gerarResumo_recebiveis_totalizaValores() {
        recebivelService.cadastrar("R1", new BigDecimal("600.00"), DATA_REPASSE);
        recebivelService.cadastrar("R2", new BigDecimal("400.00"), DATA_REPASSE);

        RelatorioResumo resumo = relatorioService.gerarResumo();

        assertEquals(0, new BigDecimal("1000.00").compareTo(resumo.valorTotalRecebiveis()));
        assertEquals(0, new BigDecimal("1000.00").compareTo(resumo.saldoDisponivelRecebiveis()));
    }

    @Test
    @DisplayName("UT-94 classifica Splits por status e soma valores corretamente")
    void gerarResumo_splits_contaEsomaStatus() {
        beneficiarioService.cadastrar(
                "Maria", TipoPessoa.PF, "111.111.111-11", "001", "1234", "11111-1");
        beneficiarioService.aprovar(1L);
        recebivelService.cadastrar("R1", new BigDecimal("1000.00"), DATA_REPASSE);

        // ID1=800 executado (saldo→200), ID2=500 recusado, ID3=100 executado (saldo→100)
        splitService.criar(1L, new BigDecimal("800.00"), DATA_REPASSE);
        splitService.criar(1L, new BigDecimal("500.00"), DATA_REPASSE);
        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE);
        splitService.executarDaData(DATA_REPASSE);

        RelatorioResumo resumo = relatorioService.gerarResumo();

        assertEquals(3, resumo.totalSplits());
        assertEquals(2, resumo.splitsExecutados());
        assertEquals(1, resumo.splitsRecusados());
        assertEquals(0, resumo.splitsPendentes());
        assertEquals(0, new BigDecimal("900.00").compareTo(resumo.valorExecutado()));
        assertEquals(0, new BigDecimal("500.00").compareTo(resumo.valorRecusado()));
        assertEquals(0, BigDecimal.ZERO.compareTo(resumo.valorPendente()));
    }

    @Test
    @DisplayName("UT-95 conta operações do histórico")
    void gerarResumo_historico_contaOperacoes() {
        beneficiarioService.cadastrar(
                "Maria", TipoPessoa.PF, "111.111.111-11", "001", "1234", "11111-1");
        beneficiarioService.aprovar(1L);
        recebivelService.cadastrar("R1", new BigDecimal("1000.00"), DATA_REPASSE);
        splitService.criar(1L, new BigDecimal("500.00"), DATA_REPASSE);

        assertEquals(1, relatorioService.gerarResumo().operacoesRegistradas());
    }

    @Test
    @DisplayName("UT-96 toString contém todas as seções")
    void relatorioResumo_toString_contemSecoes() {
        String str = relatorioService.gerarResumo().toString();
        assertTrue(str.contains("Beneficiários"));
        assertTrue(str.contains("Recebíveis"));
        assertTrue(str.contains("Splits"));
        assertTrue(str.contains("Histórico"));
    }
}