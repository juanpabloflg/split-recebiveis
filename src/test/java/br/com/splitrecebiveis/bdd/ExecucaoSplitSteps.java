package br.com.splitrecebiveis.bdd;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.StatusSplit;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.HistoricoRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.service.HistoricoService;
import br.com.splitrecebiveis.service.RecebivelService;
import br.com.splitrecebiveis.service.SplitService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class ExecucaoSplitSteps {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private BeneficiarioService beneficiarioService;
    private RecebivelService    recebivelService;
    private SplitService        splitService;
    private List<Split>         resultado;

    @Dado("que a data de execução simulada é {string}")
    public void queADataDeExecucaoSimuladaE(String data) {
        ZoneId zona = ZoneId.systemDefault();
        Clock clock = Clock.fixed(
                LocalDate.parse(data, FORMATO_DATA).atStartOfDay(zona).toInstant(), zona);

        beneficiarioService = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        recebivelService    = new RecebivelService(new RecebivelRepositoryMemoria());
        HistoricoService historicoService =
                new HistoricoService(new HistoricoRepositoryMemoria(), clock);
        splitService = new SplitService(new SplitRepositoryMemoria(),
                beneficiarioService, recebivelService, clock, historicoService);
    }

    @E("que existe um beneficiário aprovado de nome {string}")
    public void beneficiarioAprovado(String nome) {
        beneficiarioService.cadastrar(
                nome, TipoPessoa.PF, "111.111.111-11", "001", "1234", "11111-1");
        long id = beneficiarioService.listarTodos()
                .get(beneficiarioService.listarTodos().size() - 1).getId();
        beneficiarioService.aprovar(id);
    }

    @E("que existe um recebível de execução de {string} com liquidação em {string}")
    public void recebivelDeExecucao(String valor, String data) {
        recebivelService.cadastrar("Recebível",
                lerValor(valor), LocalDate.parse(data, FORMATO_DATA));
    }

    @E("que existe um Split pendente de {string} para {string} na data {string}")
    public void splitPendente(String valor, String nomeBeneficiario, String data) {
        long id = beneficiarioService.listarTodos().stream()
                .filter(b -> b.getNome().equals(nomeBeneficiario))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Beneficiário não encontrado: " + nomeBeneficiario))
                .getId();
        splitService.criar(id, lerValor(valor), LocalDate.parse(data, FORMATO_DATA));
    }

    @Quando("o operador executa os Splits da data {string}")
    public void executarSplits(String data) {
        resultado = splitService.executarDaData(LocalDate.parse(data, FORMATO_DATA));
    }

    @Então("o Split de {string} deve ter status {string}")
    public void splitComStatus(String valor, String statusEsperado) {
        StatusSplit esperado = StatusSplit.valueOf(statusEsperado);
        boolean encontrado = resultado.stream()
                .filter(s -> s.getValor().compareTo(lerValor(valor)) == 0)
                .anyMatch(s -> s.getStatus() == esperado);
        assertTrue(encontrado,
                "Não encontrou Split de " + valor + " com status " + statusEsperado);
    }

    @E("o motivo da recusa deve conter {string}")
    public void motivoContem(String trecho) {
        boolean encontrado = resultado.stream()
                .filter(s -> s.getStatus() == StatusSplit.RECUSADO)
                .anyMatch(s -> s.getMotivoRecusa() != null
                        && s.getMotivoRecusa().contains(trecho));
        assertTrue(encontrado,
                "Nenhum Split recusado contém '" + trecho + "' no motivo");
    }

    @Então("todos os Splits devem ter status {string}")
    public void todosSplitsComStatus(String statusEsperado) {
        StatusSplit esperado = StatusSplit.valueOf(statusEsperado);
        assertTrue(resultado.stream().allMatch(s -> s.getStatus() == esperado),
                "Nem todos os Splits têm status " + statusEsperado);
    }

    @E("o saldo disponível na data {string} deve ser {string}")
    public void saldoDisponivel(String data, String valorEsperado) {
        BigDecimal saldo = recebivelService.saldoDisponivelPorData(
                LocalDate.parse(data, FORMATO_DATA));
        assertEquals(0, lerValor(valorEsperado).compareTo(saldo),
                "Saldo esperado: " + valorEsperado + ", real: " + saldo);
    }

    private BigDecimal lerValor(String texto) {
        return new BigDecimal(texto.replace(".", "").replace(",", "."));
    }
}