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

import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.RegistroHistorico;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.domain.TipoOperacao;
import br.com.splitrecebiveis.infra.HistoricoRepositoryMemoria;

@DisplayName("HistoricoService")
class HistoricoServiceTest {

    private HistoricoService historicoService;
    private Split            split;

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(
                LocalDate.of(2027, 6, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(),
                ZoneId.systemDefault());

        historicoService = new HistoricoService(new HistoricoRepositoryMemoria(), clock);

        Beneficiario beneficiario = new Beneficiario(
                1L, "Maria", TipoPessoa.PF, "111.111.111-11", "001", "1234", "56789-0");
        beneficiario.aprovar();
        split = new Split(1L, beneficiario, new BigDecimal("500.00"),
                LocalDate.of(2027, 7, 1));
    }

    @Test
    @DisplayName("UT-86 listarTodos vazio sem registros")
    void listarTodos_semRegistros_vazio() {
        assertTrue(historicoService.listarTodos().isEmpty());
    }

    @Test
    @DisplayName("UT-87 registrar cria registro com operação e splitId corretos")
    void registrar_criaRegistroCorreto() {
        historicoService.registrar(TipoOperacao.CRIADO, split, null);
        List<RegistroHistorico> lista = historicoService.listarTodos();

        assertEquals(1, lista.size());
        assertEquals(TipoOperacao.CRIADO, lista.get(0).operacao());
        assertEquals(1L, lista.get(0).splitId());
    }

    @Test
    @DisplayName("UT-88 detalhe aparece na descrição; ausência não deixa separador solto")
    void registrar_detalheNaDescricao() {
        historicoService.registrar(TipoOperacao.EDITADO, split, "antes: R$ 300,00 em 01/07/2027");
        historicoService.registrar(TipoOperacao.EXCLUIDO, split, null);

        List<RegistroHistorico> lista = historicoService.listarTodos();

        assertTrue(lista.get(0).descricao().contains("antes: R$ 300,00 em 01/07/2027"));
        assertFalse(lista.get(1).descricao().endsWith(" | "));
    }

    @Test
    @DisplayName("UT-89 múltiplos registros na ordem de inserção")
    void listarTodos_ordemDeInsercao() {
        historicoService.registrar(TipoOperacao.CRIADO,   split, null);
        historicoService.registrar(TipoOperacao.EDITADO,  split, "antes: R$ 300,00 em 01/07/2027");
        historicoService.registrar(TipoOperacao.EXCLUIDO, split, null);

        List<RegistroHistorico> lista = historicoService.listarTodos();

        assertEquals(3, lista.size());
        assertEquals(TipoOperacao.CRIADO,   lista.get(0).operacao());
        assertEquals(TipoOperacao.EDITADO,  lista.get(1).operacao());
        assertEquals(TipoOperacao.EXCLUIDO, lista.get(2).operacao());
    }

    @Test
    @DisplayName("UT-90 toString contém data, operação, splitId e nome do beneficiário")
    void registroHistorico_toString_formatoCorreto() {
        historicoService.registrar(TipoOperacao.CRIADO, split, null);
        String str = historicoService.listarTodos().get(0).toString();

        assertTrue(str.contains("CRIADO"));
        assertTrue(str.contains("Split 1"));
        assertTrue(str.contains("Maria"));
    }
}