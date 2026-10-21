package br.com.splitrecebiveis.domain;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
class BeneficiarioTest {
 
    // Cria um beneficiário válido para os testes que não se importam com os dados.
    private Beneficiario criarValido() {
        return new Beneficiario(1, "Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
    }
 
    @Test
    @DisplayName("UT-01: cria beneficiário válido com status PENDENTE")
    void deveCriarBeneficiarioValidoComStatusPendente() {
        Beneficiario beneficiario = criarValido();
 
        assertEquals(1, beneficiario.getId());
        assertEquals("Maria Silva", beneficiario.getNome());
        assertEquals(TipoPessoa.PF, beneficiario.getTipoPessoa());
        assertEquals("12345678901", beneficiario.getDocumento());
        assertEquals("001", beneficiario.getBanco());
        assertEquals("1234", beneficiario.getAgencia());
        assertEquals("56789-0", beneficiario.getConta());
        assertEquals(StatusBeneficiario.PENDENTE, beneficiario.getStatus());
        assertFalse(beneficiario.isAprovado());
    }
 
    @Test
    @DisplayName("UT-02: rejeita identificador menor ou igual a zero")
    void deveRejeitarIdentificadorNaoPositivo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(0, "Maria", TipoPessoa.PF, "123", "001", "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(-1, "Maria", TipoPessoa.PF, "123", "001", "1234", "5678"));
    }
 
    @Test
    @DisplayName("UT-03: rejeita nome nulo, vazio ou só com espaços")
    void deveRejeitarNomeInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, null, TipoPessoa.PF, "123", "001", "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "", TipoPessoa.PF, "123", "001", "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "   ", TipoPessoa.PF, "123", "001", "1234", "5678"));
    }
 
    @Test
    @DisplayName("UT-04: rejeita tipo de pessoa nulo")
    void deveRejeitarTipoPessoaNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", null, "123", "001", "1234", "5678"));
    }
 
    @Test
    @DisplayName("UT-05: rejeita documento nulo, vazio ou só com espaços")
    void deveRejeitarDocumentoInvalido() {
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, null, "001", "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "", "001", "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "   ", "001", "1234", "5678"));
    }
 
    @Test
    @DisplayName("UT-06: rejeita banco, agência ou conta ausentes")
    void deveRejeitarDadosBancariosAusentes() {
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "123", null, "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "123", "  ", "1234", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "123", "001", null, "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "123", "001", "", "5678"));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "123", "001", "1234", null));
        assertThrows(IllegalArgumentException.class,
                () -> new Beneficiario(1, "Maria", TipoPessoa.PF, "123", "001", "1234", "  "));
    }
 
    @Test
    @DisplayName("UT-07: aprovar altera o status para APROVADO")
    void deveAprovarBeneficiarioPendente() {
        Beneficiario beneficiario = criarValido();
 
        beneficiario.aprovar();
 
        assertEquals(StatusBeneficiario.APROVADO, beneficiario.getStatus());
        assertTrue(beneficiario.isAprovado());
    }
 
    @Test
    @DisplayName("UT-08: aprovar beneficiário já aprovado lança IllegalStateException")
    void deveRejeitarAprovacaoDeBeneficiarioJaAprovado() {
        Beneficiario beneficiario = criarValido();
        beneficiario.aprovar();
 
        assertThrows(IllegalStateException.class, beneficiario::aprovar);
    }
 
    @Test
    @DisplayName("UT-09: toString exibe ID e status")
    void deveExibirIdEStatusNoToString() {
        String texto = criarValido().toString();
 
        assertTrue(texto.contains("ID: 1"));
        assertTrue(texto.contains("Status: PENDENTE"));
    }
}