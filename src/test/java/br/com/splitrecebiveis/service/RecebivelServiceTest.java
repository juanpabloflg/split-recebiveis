package br.com.splitrecebiveis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.splitrecebiveis.domain.Recebivel;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;

class RecebivelServiceTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 20);

    private RecebivelService service;

    // Cada teste começa com um repositório novo e vazio.
    @BeforeEach
    void setUp() {
        service = new RecebivelService(new RecebivelRepositoryMemoria());
    }

    @Test
    @DisplayName("UT-29: cadastrar atribui identificadores sequenciais")
    void deveAtribuirIdentificadoresSequenciais() {
        Recebivel primeiro = service.cadastrar("Venda 1", new BigDecimal("100"), DATA);
        Recebivel segundo = service.cadastrar("Venda 2", new BigDecimal("200"), DATA);

        assertEquals(1, primeiro.getId());
        assertEquals(2, segundo.getId());
    }

    @Test
    @DisplayName("UT-30: cadastrar armazena o recebível no repositório")
    void deveArmazenarRecebivelCadastrado() {
        Recebivel cadastrado = service.cadastrar("Venda 1", new BigDecimal("100"), DATA);

        List<Recebivel> todos = service.listarTodos();

        assertEquals(1, todos.size());
        assertSame(cadastrado, todos.get(0));
    }

    @Test
    @DisplayName("UT-31: cadastro inválido lança exceção, não salva e não consome identificador")
    void naoDeveSalvarNemConsumirIdQuandoCadastroForInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("Venda", BigDecimal.ZERO, DATA));

        assertTrue(service.listarTodos().isEmpty());
        assertEquals(1, service.cadastrar("Venda", new BigDecimal("100"), DATA).getId());
    }

    @Test
    @DisplayName("UT-32: listarTodos retorna lista vazia quando não há recebíveis")
    void deveRetornarListaVaziaQuandoNaoHaRecebiveis() {
        assertTrue(service.listarTodos().isEmpty());
    }

    @Test
    @DisplayName("UT-33: listarTodos retorna uma cópia da lista interna")
    void deveRetornarCopiaDaListaInterna() {
        service.cadastrar("Venda 1", new BigDecimal("100"), DATA);

        List<Recebivel> copia = service.listarTodos();
        copia.clear();

        assertNotSame(copia, service.listarTodos());
        assertEquals(1, service.listarTodos().size());
    }
}