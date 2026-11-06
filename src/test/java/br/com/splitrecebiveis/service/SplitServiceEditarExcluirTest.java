package br.com.splitrecebiveis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;

class SplitServiceEditarExcluirTest {

    private static final LocalDate HOJE = LocalDate.of(2026, 11, 20);
    private static final LocalDate DATA_FUTURA = LocalDate.of(2026, 11, 25);
    private static final LocalDate OUTRA_DATA = LocalDate.of(2026, 11, 30);

    private SplitService service;
    private Beneficiario beneficiario;

    // Cada teste começa com repositórios novos, um beneficiário aprovado e recebíveis
    // de R$ 1.000,00 nas duas datas usadas nos testes.
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

        recebivelService.cadastrar("Venda 1", new BigDecimal("1000.00"), DATA_FUTURA);
        recebivelService.cadastrar("Venda 2", new BigDecimal("1000.00"), OUTRA_DATA);
    }

    private Split criarSplitPendente() {
        return service.criar(beneficiario.getId(), new BigDecimal("800.00"), DATA_FUTURA);
    }

    @Test
    @DisplayName("UT-63: edita o valor e a data de um Split pendente")
    void deveEditarSplitPendente() {
        Split split = criarSplitPendente();

        Split editado = service.editar(split.getId(), new BigDecimal("500.00"), OUTRA_DATA);

        assertEquals(0, new BigDecimal("500.00").compareTo(editado.getValor()));
        assertEquals(OUTRA_DATA, editado.getDataRepasse());
    }

    @Test
    @DisplayName("UT-64: RN03 - editar com valor acima dos recebíveis é rejeitado e mantém o Split")
    void deveRejeitarEdicaoComValorAcimaDoRecebivel() {
        Split split = criarSplitPendente();

        assertThrows(IllegalArgumentException.class,
                () -> service.editar(split.getId(), new BigDecimal("1500.00"), DATA_FUTURA));

        assertEquals(0, new BigDecimal("800.00").compareTo(split.getValor()));
        assertEquals(DATA_FUTURA, split.getDataRepasse());
    }

    @Test
    @DisplayName("UT-65: RN02 - editar com data de hoje é rejeitado e mantém o Split")
    void deveRejeitarEdicaoComDataSemAntecedencia() {
        Split split = criarSplitPendente();

        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.editar(split.getId(), new BigDecimal("800.00"), HOJE));

        assertEquals("O Split deve ser configurado com no mínimo 1 dia de antecedência.", erro.getMessage());
        assertEquals(DATA_FUTURA, split.getDataRepasse());
    }

    @Test
    @DisplayName("UT-66: editar Split inexistente lança IllegalArgumentException")
    void deveRejeitarEdicaoDeSplitInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> service.editar(99, new BigDecimal("500.00"), DATA_FUTURA));
    }

    @Test
    @DisplayName("UT-67: RN05 - editar Split executado lança IllegalStateException")
    void deveRejeitarEdicaoDeSplitExecutado() {
        Split split = criarSplitPendente();
        split.marcarComoExecutado();

        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> service.editar(split.getId(), new BigDecimal("500.00"), DATA_FUTURA));

        assertEquals("Apenas Splits pendentes podem ser editados.", erro.getMessage());
    }

    @Test
    @DisplayName("UT-68: exclui um Split pendente")
    void deveExcluirSplitPendente() {
        Split split = criarSplitPendente();

        service.excluir(split.getId());

        assertTrue(service.listarTodos().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(split.getId()));
    }

    @Test
    @DisplayName("UT-69: excluir Split inexistente lança IllegalArgumentException")
    void deveRejeitarExclusaoDeSplitInexistente() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.excluir(99));

        assertEquals("Split não encontrado.", erro.getMessage());
    }

    @Test
    @DisplayName("UT-70: RN06 - excluir Split executado ou recusado lança IllegalStateException")
    void deveRejeitarExclusaoDeSplitExecutadoOuRecusado() {
        Split executado = criarSplitPendente();
        executado.marcarComoExecutado();
        Split recusado = service.criar(beneficiario.getId(), new BigDecimal("100.00"), OUTRA_DATA);
        recusado.recusar();

        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> service.excluir(executado.getId()));
        assertEquals("Apenas Splits pendentes podem ser excluídos.", erro.getMessage());
        assertThrows(IllegalStateException.class, () -> service.excluir(recusado.getId()));

        assertEquals(2, service.listarTodos().size());
    }
}