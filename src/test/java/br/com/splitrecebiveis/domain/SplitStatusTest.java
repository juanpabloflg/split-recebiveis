package br.com.splitrecebiveis.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SplitStatusTest {

    private static final LocalDate DATA = LocalDate.of(2026, 11, 25);
    private static final LocalDate OUTRA_DATA = LocalDate.of(2026, 11, 30);

    private Split criarSplitPendente() {
        Beneficiario beneficiario = new Beneficiario(1, "Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
        return new Split(1, beneficiario, new BigDecimal("800.00"), DATA);
    }

    @Test
    @DisplayName("UT-58: editar Split pendente altera valor e data")
    void deveEditarSplitPendente() {
        Split split = criarSplitPendente();

        split.editar(new BigDecimal("500.50"), OUTRA_DATA);

        assertEquals(0, new BigDecimal("500.50").compareTo(split.getValor()));
        assertEquals(OUTRA_DATA, split.getDataRepasse());
        assertEquals(StatusSplit.PENDENTE, split.getStatus());
    }

    @Test
    @DisplayName("UT-59: RN05 - editar Split executado lança IllegalStateException")
    void deveRejeitarEdicaoDeSplitExecutado() {
        Split split = criarSplitPendente();
        split.marcarComoExecutado();

        assertThrows(IllegalStateException.class,
                () -> split.editar(new BigDecimal("500.00"), OUTRA_DATA));
    }

    @Test
    @DisplayName("UT-60: RN05 - editar Split recusado lança IllegalStateException")
    void deveRejeitarEdicaoDeSplitRecusado() {
        Split split = criarSplitPendente();
        split.recusar();

        assertThrows(IllegalStateException.class,
                () -> split.editar(new BigDecimal("500.00"), OUTRA_DATA));
    }

    @Test
    @DisplayName("UT-61: marcarComoExecutado altera o status e só vale para Split pendente")
    void deveMarcarSplitComoExecutadoUmaUnicaVez() {
        Split split = criarSplitPendente();

        split.marcarComoExecutado();

        assertEquals(StatusSplit.EXECUTADO, split.getStatus());
        assertFalse(split.isPendente());
        assertThrows(IllegalStateException.class, split::marcarComoExecutado);
        assertThrows(IllegalStateException.class, split::recusar);
    }

    @Test
    @DisplayName("UT-62: recusar altera o status e só vale para Split pendente")
    void deveRecusarSplitUmaUnicaVez() {
        Split split = criarSplitPendente();

        split.recusar();

        assertEquals(StatusSplit.RECUSADO, split.getStatus());
        assertThrows(IllegalStateException.class, split::recusar);
        assertThrows(IllegalStateException.class, split::marcarComoExecutado);
    }
}