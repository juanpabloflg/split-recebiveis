package br.com.splitrecebiveis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;

@DisplayName("RecebivelService - saldo e débito por data")
class RecebivelServiceSaldoDebitoTest {

    private RecebivelService service;
    private static final LocalDate DATA = LocalDate.of(2027, 6, 1);

    @BeforeEach
    void setUp() {
        service = new RecebivelService(new RecebivelRepositoryMemoria());
    }

    @Test
    @DisplayName("UT-77 saldo por data soma apenas a data informada")
    void saldoDisponivel_somaApenaDataInformada() {
        // zero quando não há recebíveis
        assertEquals(0, BigDecimal.ZERO.compareTo(service.saldoDisponivelPorData(DATA)));

        service.cadastrar("R1", new BigDecimal("600.00"), DATA);
        service.cadastrar("R2", new BigDecimal("400.00"), DATA);
        service.cadastrar("R3", new BigDecimal("300.00"), DATA.plusDays(1)); // outra data, ignorada

        assertEquals(0, new BigDecimal("1000.00").compareTo(service.saldoDisponivelPorData(DATA)));
    }

    @Test
    @DisplayName("UT-78 debitarPorData distribui entre recebíveis da data")
    void debitarPorData_distribuiEntreRecebiveis() {
        service.cadastrar("R1", new BigDecimal("300.00"), DATA);
        service.cadastrar("R2", new BigDecimal("700.00"), DATA);

        service.debitarPorData(DATA, new BigDecimal("800.00"));

        assertEquals(0, new BigDecimal("200.00").compareTo(service.saldoDisponivelPorData(DATA)));
    }

    @Test
    @DisplayName("UT-79 saldo insuficiente lança exceção sem debitar nada")
    void debitarPorData_saldoInsuficiente_lancaExcecaoESaldoIntacto() {
        service.cadastrar("R1", new BigDecimal("200.00"), DATA);

        assertThrows(IllegalArgumentException.class,
                () -> service.debitarPorData(DATA, new BigDecimal("500.00")));

        assertEquals(0, new BigDecimal("200.00").compareTo(service.saldoDisponivelPorData(DATA)));
    }
}