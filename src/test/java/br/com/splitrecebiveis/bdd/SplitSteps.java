package br.com.splitrecebiveis.bdd;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
 
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.service.RecebivelService;
import br.com.splitrecebiveis.service.SplitService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
 
/**
* Passos (step definitions) dos cenários de criação de Split.
* O Cucumber cria uma nova instância desta classe para cada cenário,
* então cada cenário começa com serviços e repositórios novos.
*/
public class SplitSteps {
 
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
    private BeneficiarioService beneficiarioService;
    private RecebivelService recebivelService;
    private SplitService splitService;
 
    private final Map<String, Beneficiario> beneficiarios = new HashMap<>();
    private Split splitCriado;
    private Exception erro;
 
    @Dado("que hoje é {string}")
    public void queHojeE(String data) {
        // Relógio fixo: a regra de antecedência passa a depender desta data, e não do dia em que o teste roda.
        ZoneId zona = ZoneId.systemDefault();
        Clock clock = Clock.fixed(LocalDate.parse(data, FORMATO_DATA).atStartOfDay(zona).toInstant(), zona);
 
        beneficiarioService = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        recebivelService = new RecebivelService(new RecebivelRepositoryMemoria());
        splitService = new SplitService(new SplitRepositoryMemoria(), beneficiarioService, recebivelService, clock);
    }
 
    @Dado("que existe um recebível de {string} com liquidação em {string}")
    public void queExisteUmRecebivel(String valor, String data) {
        recebivelService.cadastrar("Venda", lerValor(valor), LocalDate.parse(data, FORMATO_DATA));
    }
 
    @Dado("que existe um beneficiário {string} aprovado")
    public void queExisteUmBeneficiarioAprovado(String nome) {
        Beneficiario beneficiario = cadastrarBeneficiario(nome);
        beneficiarioService.aprovar(beneficiario.getId());
    }
 
    @Dado("que existe um beneficiário {string} pendente")
    public void queExisteUmBeneficiarioPendente(String nome) {
        cadastrarBeneficiario(nome);
    }
 
    @Quando("o operador cria um Split de {string} para {string} na data {string}")
    public void oOperadorCriaUmSplit(String valor, String nome, String data) {
        try {
            splitCriado = splitService.criar(
                    beneficiarios.get(nome).getId(),
                    lerValor(valor),
                    LocalDate.parse(data, FORMATO_DATA));
        } catch (IllegalArgumentException | IllegalStateException e) {
            // O erro é guardado para o passo "Então" conferir a mensagem.
            erro = e;
        }
    }
 
    @Entao("o Split deve ser criado com status {string}")
    public void oSplitDeveSerCriado(String status) {
        assertNull(erro, "Não era esperado erro, mas ocorreu: " + (erro == null ? "" : erro.getMessage()));
        assertNotNull(splitCriado, "O Split deveria ter sido criado.");
        assertEquals(status, splitCriado.getStatus().name());
    }
 
    @Entao("o Split deve ser rejeitado com a mensagem {string}")
    public void oSplitDeveSerRejeitado(String mensagem) {
        assertNotNull(erro, "Era esperado um erro, mas o Split foi criado.");
        assertEquals(mensagem, erro.getMessage());
        assertNull(splitCriado, "Nenhum Split deveria ter sido criado.");
    }
 
    private Beneficiario cadastrarBeneficiario(String nome) {
        Beneficiario beneficiario = beneficiarioService.cadastrar(
                nome, TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
        beneficiarios.put(nome, beneficiario);
        return beneficiario;
    }
 
    // Aceita valores como "800,00" e "1000,00".
    private BigDecimal lerValor(String texto) {
        return new BigDecimal(texto.replace(".", "").replace(",", "."));
    }
}

