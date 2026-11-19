package br.com.splitrecebiveis.domain;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.math.BigDecimal;
import java.time.LocalDate;
 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
class SplitTest {
 
    private static final LocalDate DATA = LocalDate.of(2026, 11, 25);
 
    private Beneficiario criarBeneficiario() {
        return new Beneficiario(1, "Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
    }
 
    @Test
    @DisplayName("UT-34: cria Split válido com status PENDENTE")
    void deveCriarSplitValidoComStatusPendente() {
        Beneficiario beneficiario = criarBeneficiario();
 
        Split split = new Split(1, beneficiario, new BigDecimal("800.50"), DATA);
 
        assertEquals(1, split.getId());
        assertSame(beneficiario, split.getBeneficiario());
        assertEquals(0, new BigDecimal("800.50").compareTo(split.getValor()));
        assertEquals(DATA, split.getDataRepasse());
        assertEquals(StatusSplit.PENDENTE, split.getStatus());
        assertTrue(split.isPendente());
    }
 
    @Test
    @DisplayName("UT-35: rejeita identificador menor ou igual a zero")
    void deveRejeitarIdentificadorNaoPositivo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Split(0, criarBeneficiario(), new BigDecimal("10"), DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Split(-1, criarBeneficiario(), new BigDecimal("10"), DATA));
    }
 
    @Test
    @DisplayName("UT-36: rejeita beneficiário nulo")
    void deveRejeitarBeneficiarioNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Split(1, null, new BigDecimal("10"), DATA));
    }
 
    @Test
    @DisplayName("UT-37: rejeita valor nulo, zero ou negativo")
    void deveRejeitarValorNuloZeroOuNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Split(1, criarBeneficiario(), null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Split(1, criarBeneficiario(), BigDecimal.ZERO, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Split(1, criarBeneficiario(), new BigDecimal("-5.00"), DATA));
    }
 
    @Test
    @DisplayName("UT-38: rejeita valor com mais de 2 casas decimais")
    void deveRejeitarMaisDeDuasCasasDecimais() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> new Split(1, criarBeneficiario(), new BigDecimal("10.999"), DATA));
 
        assertEquals("O valor deve ter no máximo 2 casas decimais.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-39: rejeita data do repasse nula")
    void deveRejeitarDataNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Split(1, criarBeneficiario(), new BigDecimal("10"), null));
    }
}