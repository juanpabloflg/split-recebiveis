package br.com.splitrecebiveis.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Recebivel - debitar")
class RecebivelDebitarTest {

    private Recebivel recebivel;

    @BeforeEach
    void setUp() {
        recebivel = new Recebivel(1L, "Venda janeiro",
                new BigDecimal("1000.00"), LocalDate.now().plusDays(5));
    }

    @Test
    @DisplayName("UT-75 debitar valor válido reduz o saldo disponível")
    void debitar_valorValido_reduzSaldo() {
        recebivel.debitar(new BigDecimal("400.00"));
        assertEquals(new BigDecimal("600.00"), recebivel.getValorDisponivel());
    }

    @Test
    @DisplayName("UT-76 debitar valor inválido lança exceção")
    void debitar_valorInvalido_lancaExcecao() {
        assertAll(
            () -> assertThrows(IllegalArgumentException.class,
                    () -> recebivel.debitar(null)),
            () -> assertThrows(IllegalArgumentException.class,
                    () -> recebivel.debitar(BigDecimal.ZERO)),
            () -> assertThrows(IllegalArgumentException.class,
                    () -> recebivel.debitar(new BigDecimal("-100.00"))),
            () -> assertThrows(IllegalArgumentException.class,
                    () -> recebivel.debitar(new BigDecimal("1500.00")))
        );
        // saldo permanece intacto após qualquer exceção
        assertEquals(new BigDecimal("1000.00"), recebivel.getValorDisponivel());
    }
}