package br.com.splitrecebiveis.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.StatusBeneficiario;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
 
class BeneficiarioServiceTest {
 
    private BeneficiarioService service;
 
    // Cada teste começa com um repositório novo e vazio.
    @BeforeEach
    void setUp() {
        service = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
    }
 
    private Beneficiario cadastrarMaria() {
        return service.cadastrar("Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
    }
 
    private Beneficiario cadastrarEmpresa() {
        return service.cadastrar("Empresa XYZ", TipoPessoa.PJ, "12345678000199", "237", "4321", "98765-4");
    }
 
    @Test
    @DisplayName("UT-10: cadastrar atribui identificadores sequenciais e status PENDENTE")
    void deveAtribuirIdentificadoresSequenciais() {
        Beneficiario primeiro = cadastrarMaria();
        Beneficiario segundo = cadastrarEmpresa();
 
        assertEquals(1, primeiro.getId());
        assertEquals(2, segundo.getId());
        assertEquals(StatusBeneficiario.PENDENTE, primeiro.getStatus());
        assertEquals(StatusBeneficiario.PENDENTE, segundo.getStatus());
    }
 
    @Test
    @DisplayName("UT-11: cadastrar armazena o beneficiário no repositório")
    void deveArmazenarBeneficiarioCadastrado() {
        Beneficiario cadastrado = cadastrarMaria();
 
        List<Beneficiario> todos = service.listarTodos();
 
        assertEquals(1, todos.size());
        assertSame(cadastrado, todos.get(0));
    }
 
    @Test
    @DisplayName("UT-12: cadastro inválido lança exceção, não salva e não consome identificador")
    void naoDeveSalvarNemConsumirIdQuandoCadastroForInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> service.cadastrar("", TipoPessoa.PF, "123", "001", "1234", "5678"));
 
        assertTrue(service.listarTodos().isEmpty());
        assertEquals(1, cadastrarMaria().getId());
    }
 
    @Test
    @DisplayName("UT-13: listarTodos retorna lista vazia quando não há beneficiários")
    void deveRetornarListaVaziaQuandoNaoHaBeneficiarios() {
        assertTrue(service.listarTodos().isEmpty());
    }
 
    @Test
    @DisplayName("UT-14: listarTodos retorna uma cópia da lista interna")
    void deveRetornarCopiaDaListaInterna() {
        cadastrarMaria();
 
        List<Beneficiario> copia = service.listarTodos();
        copia.clear();
 
        assertNotSame(copia, service.listarTodos());
        assertEquals(1, service.listarTodos().size());
    }
 
    @Test
    @DisplayName("UT-15: listarPendentes retorna apenas beneficiários pendentes")
    void deveListarApenasBeneficiariosPendentes() {
        Beneficiario maria = cadastrarMaria();
        Beneficiario empresa = cadastrarEmpresa();
        service.aprovar(maria.getId());
 
        List<Beneficiario> pendentes = service.listarPendentes();
 
        assertEquals(1, pendentes.size());
        assertSame(empresa, pendentes.get(0));
    }
 
    @Test
    @DisplayName("UT-16: aprovar altera o status e remove o beneficiário dos pendentes")
    void deveAprovarBeneficiarioExistente() {
        Beneficiario maria = cadastrarMaria();
 
        Beneficiario aprovado = service.aprovar(maria.getId());
 
        assertEquals(StatusBeneficiario.APROVADO, aprovado.getStatus());
        assertTrue(service.listarPendentes().isEmpty());
    }
 
    @Test
    @DisplayName("UT-17: aprovar beneficiário inexistente lança IllegalArgumentException")
    void deveRejeitarAprovacaoDeBeneficiarioInexistente() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.aprovar(99));
 
        assertEquals("Beneficiário não encontrado.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-18: aprovar beneficiário já aprovado lança IllegalStateException")
    void deveRejeitarAprovacaoDuplicada() {
        Beneficiario maria = cadastrarMaria();
        service.aprovar(maria.getId());
 
        assertThrows(IllegalStateException.class, () -> service.aprovar(maria.getId()));
    }
 
    @Test
    @DisplayName("UT-19: buscarPorId retorna o beneficiário ou lança exceção se não existir")
    void deveBuscarBeneficiarioPorId() {
        Beneficiario maria = cadastrarMaria();
 
        assertSame(maria, service.buscarPorId(maria.getId()));
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(99));
    }
}