package br.com.splitrecebiveis.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;
 
class SplitServiceListarTest {
 
    private static final LocalDate HOJE = LocalDate.of(2026, 11, 20);
    private static final LocalDate DATA_FUTURA = LocalDate.of(2026, 11, 25);
 
    private SplitService service;
    private Beneficiario beneficiario;
 
    // Cada teste começa com repositórios novos e um beneficiário aprovado, com recebível de R$ 1.000,00.
    @BeforeEach
    void setUp() {
        ZoneId zona = ZoneId.systemDefault();
        Clock clock = Clock.fixed(HOJE.atStartOfDay(zona).toInstant(), zona);
 
        BeneficiarioService beneficiarioService = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        RecebivelService recebivelService = new RecebivelService(new RecebivelRepositoryMemoria());
        service = new SplitService(new SplitRepositoryMemoria(), beneficiarioService, recebivelService, clock);
 
        beneficiario = beneficiarioService.cadastrar(
                "Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
        beneficiarioService.aprovar(beneficiario.getId());
        recebivelService.cadastrar("Venda", new BigDecimal("1000.00"), DATA_FUTURA);
    }
 
    @Test
    @DisplayName("UT-55: listarTodos retorna lista vazia quando não há Splits")
    void deveRetornarListaVaziaQuandoNaoHaSplits() {
        assertTrue(service.listarTodos().isEmpty());
    }
 
    @Test
    @DisplayName("UT-56: listarTodos retorna os Splits na ordem de criação")
    void deveListarSplitsNaOrdemDeCriacao() {
        Split primeiro = service.criar(beneficiario.getId(), new BigDecimal("300.00"), DATA_FUTURA);
        Split segundo = service.criar(beneficiario.getId(), new BigDecimal("200.00"), DATA_FUTURA);
 
        List<Split> splits = service.listarTodos();
 
        assertEquals(2, splits.size());
        assertSame(primeiro, splits.get(0));
        assertSame(segundo, splits.get(1));
    }
 
    @Test
    @DisplayName("UT-57: listarTodos retorna uma cópia da lista interna")
    void deveRetornarCopiaDaListaInterna() {
        service.criar(beneficiario.getId(), new BigDecimal("300.00"), DATA_FUTURA);
 
        List<Split> copia = service.listarTodos();
        copia.clear();
 
        assertNotSame(copia, service.listarTodos());
        assertEquals(1, service.listarTodos().size());
    }
}