package br.com.splitrecebiveis.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Split - recusar(motivo) (RN04)")
class SplitRecusarComMotivoTest {

    private Split split;

    @BeforeEach
    void setUp() {
        Beneficiario beneficiario = new Beneficiario(
                1L, "Maria", TipoPessoa.PF, "111.111.111-11", "001", "1234", "56789-0");
        beneficiario.aprovar();
        split = new Split(1L, beneficiario, new BigDecimal("500.00"),
                LocalDate.now().plusDays(2));
    }

    @Test
    @DisplayName("UT-71 recusar sem motivo mantém motivo nulo")
    void recusarSemMotivo_motivoNulo() {
        split.recusar();
        assertEquals(StatusSplit.RECUSADO, split.getStatus());
        assertNull(split.getMotivoRecusa());
    }

    @Test
    @DisplayName("UT-72 recusar com motivo armazena a mensagem")
    void recusarComMotivo_armazenaMotivo() {
        String motivo = "Saldo insuficiente: disponível R$ 100,00 para um Split de R$ 500,00.";
        split.recusar(motivo);
        assertEquals(StatusSplit.RECUSADO, split.getStatus());
        assertEquals(motivo, split.getMotivoRecusa());
    }

    @Test
    @DisplayName("UT-73 recusar Split não-pendente lança exceção")
    void recusarNaoPendente_lancaExcecao() {
        split.marcarComoExecutado();
        assertThrows(IllegalStateException.class, () -> split.recusar());

        Beneficiario b2 = new Beneficiario(
                2L, "João", TipoPessoa.PF, "222.222.222-22", "001", "1234", "22222-2");
        b2.aprovar();
        Split outro = new Split(2L, b2, new BigDecimal("100.00"), LocalDate.now().plusDays(2));
        outro.recusar();
        assertThrows(IllegalStateException.class, () -> outro.recusar("novo motivo"));
    }

    @Test
    @DisplayName("UT-74 toString inclui motivo da recusa quando presente")
    void toString_incluiMotivoRecusa() {
        split.recusar("Saldo insuficiente.");
        assertTrue(split.toString().contains("Motivo da recusa: Saldo insuficiente."));
    }
}