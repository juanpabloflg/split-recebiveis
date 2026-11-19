package br.com.splitrecebiveis.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
 
import java.math.BigDecimal;
import java.time.LocalDate;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
 
class RecebivelTotalPorDataTest {
 
    private static final LocalDate DATA = LocalDate.of(2026, 10, 20);
 
    private RecebivelService service;
 
    @BeforeEach
    void setUp() {
        service = new RecebivelService(new RecebivelRepositoryMemoria());
    }
 
    @Test
    @DisplayName("UT-40: totalPorData soma apenas os recebíveis da data informada")
    void deveSomarRecebiveisDaData() {
        service.cadastrar("Venda 1", new BigDecimal("1500.50"), DATA);
        service.cadastrar("Venda 2", new BigDecimal("500.00"), DATA);
        service.cadastrar("Venda 3", new BigDecimal("999.00"), DATA.plusDays(1));
 
        assertEquals(0, new BigDecimal("2000.50").compareTo(service.totalPorData(DATA)));
    }
 
    @Test
    @DisplayName("UT-41: totalPorData retorna zero quando não há recebíveis na data")
    void deveRetornarZeroSemRecebiveisNaData() {
        service.cadastrar("Venda 1", new BigDecimal("100.00"), DATA);
 
        assertEquals(0, BigDecimal.ZERO.compareTo(service.totalPorData(DATA.plusDays(5))));
    }
}