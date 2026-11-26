package br.com.splitrecebiveis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.StatusSplit;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.HistoricoRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;

@DisplayName("SplitService - fila de prioridade e execução (RN04 e RN07)")
class SplitServiceExecucaoTest {

    private static final LocalDate HOJE        = LocalDate.of(2027, 1, 1);
    private static final LocalDate DATA_REPASSE = HOJE.plusDays(1);

    private SplitService     splitService;
    private RecebivelService recebivelService;

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(
                HOJE.atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());

        BeneficiarioService beneficiarioService =
                new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        recebivelService = new RecebivelService(new RecebivelRepositoryMemoria());
        HistoricoService historicoService =
                new HistoricoService(new HistoricoRepositoryMemoria(), clock);

        splitService = new SplitService(new SplitRepositoryMemoria(),
                beneficiarioService, recebivelService, clock, historicoService);

        beneficiarioService.cadastrar(
                "Maria", TipoPessoa.PF, "111.111.111-11", "001", "1234", "56789-0");
        beneficiarioService.aprovar(1L);

        recebivelService.cadastrar("Venda", new BigDecimal("1000.00"), DATA_REPASSE);
    }

    @Test
    @DisplayName("UT-80 filaDaData retorna Splits pendentes da data em ordem de ID (RN07)")
    void filaDaData_ordemDeIdEFiltros() {
        // recebível extra para permitir criar Split em DATA_REPASSE.plusDays(1)
        recebivelService.cadastrar("Venda extra", new BigDecimal("500.00"), DATA_REPASSE.plusDays(1));

        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE);          // ID 1
        splitService.criar(1L, new BigDecimal("200.00"), DATA_REPASSE);          // ID 2
        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE.plusDays(1)); // outra data

        List<Split> fila = splitService.filaDaData(DATA_REPASSE);

        assertEquals(2, fila.size());
        assertEquals(1L, fila.get(0).getId());
        assertEquals(2L, fila.get(1).getId());
    }

    @Test
    @DisplayName("UT-81 filaDaData ignora Splits não-pendentes")
    void filaDaData_ignoraNaoPendentes() {
        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE); // ID 1 → será executado
        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE); // ID 2 → permanece pendente
        splitService.executarDaData(DATA_REPASSE);

        // após execução não há mais pendentes
        assertTrue(splitService.filaDaData(DATA_REPASSE).isEmpty());
    }

    @Test
    @DisplayName("UT-82 executarDaData com saldo suficiente marca EXECUTADO e debita (RN04)")
    void executarDaData_saldoSuficiente_executadoEDebita() {
        splitService.criar(1L, new BigDecimal("800.00"), DATA_REPASSE);

        List<Split> resultado = splitService.executarDaData(DATA_REPASSE);

        assertEquals(StatusSplit.EXECUTADO, resultado.get(0).getStatus());
        assertEquals(0, new BigDecimal("200.00")
                .compareTo(recebivelService.saldoDisponivelPorData(DATA_REPASSE)));
    }

    @Test
    @DisplayName("UT-83 executarDaData sem saldo marca RECUSADO com motivo sem debitar (RN04)")
    void executarDaData_saldoInsuficiente_recusadoSemDebito() {
        recebivelService.debitarPorData(DATA_REPASSE, new BigDecimal("950.00")); // saldo → 50
        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE);

        BigDecimal saldoAntes = recebivelService.saldoDisponivelPorData(DATA_REPASSE);
        List<Split> resultado = splitService.executarDaData(DATA_REPASSE);

        assertEquals(StatusSplit.RECUSADO, resultado.get(0).getStatus());
        assertTrue(resultado.get(0).getMotivoRecusa().contains("Saldo insuficiente"));
        assertEquals(0, saldoAntes.compareTo(
                recebivelService.saldoDisponivelPorData(DATA_REPASSE)));
    }

    @Test
    @DisplayName("UT-84 executarDaData respeita prioridade com saldo parcial (RN07 + RN04)")
    void executarDaData_prioridadeMista() {
        // saldo = 1.000 → ID1 800 executado (saldo→200), ID2 500 recusado, ID3 100 executado (saldo→100)
        splitService.criar(1L, new BigDecimal("800.00"), DATA_REPASSE);
        splitService.criar(1L, new BigDecimal("500.00"), DATA_REPASSE);
        splitService.criar(1L, new BigDecimal("100.00"), DATA_REPASSE);

        List<Split> resultado = splitService.executarDaData(DATA_REPASSE);

        assertEquals(StatusSplit.EXECUTADO, resultado.get(0).getStatus());
        assertEquals(StatusSplit.RECUSADO,  resultado.get(1).getStatus());
        assertEquals(StatusSplit.EXECUTADO, resultado.get(2).getStatus());
    }

    @Test
    @DisplayName("UT-85 executarDaData retorna lista vazia sem Splits pendentes")
    void executarDaData_semPendentes_listaVazia() {
        assertTrue(splitService.executarDaData(DATA_REPASSE).isEmpty());
    }
}