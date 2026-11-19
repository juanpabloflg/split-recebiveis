package br.com.splitrecebiveis.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecebivelTest {

    private static final LocalDate DATA = LocalDate.of(2026, 10, 20);

    // BigDecimal deve ser comparado com compareTo: "1000" e "1000.00" não são iguais no equals.
    private void assertValor(String esperado, BigDecimal obtido) {
        assertEquals(0, new BigDecimal(esperado).compareTo(obtido),
                "Esperado " + esperado + " mas foi " + obtido);
    }

    @Test
    @DisplayName("UT-20: cria recebível válido com valor disponível igual ao valor total")
    void deveCriarRecebivelValido() {
        Recebivel recebivel = new Recebivel(1, "Venda cartão", new BigDecimal("1500.50"), DATA);

        assertEquals(1, recebivel.getId());
        assertEquals("Venda cartão", recebivel.getDescricao());
        assertValor("1500.50", recebivel.getValorTotal());
        assertValor("1500.50", recebivel.getValorDisponivel());
        assertEquals(DATA, recebivel.getDataLiquidacao());
    }

    @Test
    @DisplayName("UT-21: rejeita identificador menor ou igual a zero")
    void deveRejeitarIdentificadorNaoPositivo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(0, "Venda", new BigDecimal("10"), DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(-1, "Venda", new BigDecimal("10"), DATA));
    }

    @Test
    @DisplayName("UT-22: rejeita descrição nula, vazia ou só com espaços")
    void deveRejeitarDescricaoInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, null, new BigDecimal("10"), DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "", new BigDecimal("10"), DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "   ", new BigDecimal("10"), DATA));
    }

    @Test
    @DisplayName("UT-23: rejeita valor nulo, zero ou negativo")
    void deveRejeitarValorNuloZeroOuNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "Venda", null, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "Venda", BigDecimal.ZERO, DATA));
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "Venda", new BigDecimal("-5.00"), DATA));
    }

    @Test
    @DisplayName("UT-24: aceita o menor valor possível, R$ 0,01 (caso de borda)")
    void deveAceitarValorMinimo() {
        Recebivel recebivel = new Recebivel(1, "Venda", new BigDecimal("0.01"), DATA);

        assertValor("0.01", recebivel.getValorTotal());
    }

    @Test
    @DisplayName("UT-25: rejeita valor com mais de 2 casas decimais")
    void deveRejeitarMaisDeDuasCasasDecimais() {
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "Venda", new BigDecimal("10.999"), DATA));

        assertEquals("O valor deve ter no máximo 2 casas decimais.", erro.getMessage());
    }

    @Test
    @DisplayName("UT-26: rejeita data de liquidação nula")
    void deveRejeitarDataNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Recebivel(1, "Venda", new BigDecimal("10"), null));
    }

    @Test
    @DisplayName("UT-27: normaliza o valor para 2 casas decimais")
    void deveNormalizarValorParaDuasCasasDecimais() {
        Recebivel recebivel = new Recebivel(1, "Venda", new BigDecimal("1000"), DATA);

        assertEquals(2, recebivel.getValorTotal().scale());
        assertEquals(2, recebivel.getValorDisponivel().scale());
        assertValor("1000.00", recebivel.getValorTotal());
    }

    @Test
    @DisplayName("UT-28: toString exibe valor em reais e data no formato dd/MM/aaaa")
    void deveFormatarValorEDataNoToString() {
        String texto = new Recebivel(1, "Venda", new BigDecimal("1500.5"), DATA).toString();

        assertTrue(texto.contains("Valor total: R$ 1.500,50"));
        assertTrue(texto.contains("Valor disponível: R$ 1.500,50"));
        assertTrue(texto.contains("Data de liquidação: 20/10/2026"));
    }
}