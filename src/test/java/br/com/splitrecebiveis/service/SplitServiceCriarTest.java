package br.com.splitrecebiveis.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
 
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.StatusSplit;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;
 
class SplitServiceCriarTest {
 
    // "Hoje" fixo nos testes, para a regra de antecedência não depender do dia em que o teste roda.
    private static final LocalDate HOJE = LocalDate.of(2026, 11, 20);
    private static final LocalDate AMANHA = HOJE.plusDays(1);
    private static final LocalDate DATA_FUTURA = LocalDate.of(2026, 11, 25);
 
    private BeneficiarioService beneficiarioService;
    private RecebivelService recebivelService;
    private SplitService service;
 
    // Cada teste começa com repositórios novos e vazios.
    @BeforeEach
    void setUp() {
        ZoneId zona = ZoneId.systemDefault();
        Clock clock = Clock.fixed(HOJE.atStartOfDay(zona).toInstant(), zona);
 
        beneficiarioService = new BeneficiarioService(new BeneficiarioRepositoryMemoria());
        recebivelService = new RecebivelService(new RecebivelRepositoryMemoria());
        service = new SplitService(new SplitRepositoryMemoria(), beneficiarioService, recebivelService, clock);
    }
 
    private Beneficiario cadastrarAprovado() {
        Beneficiario beneficiario = beneficiarioService.cadastrar(
                "Maria Silva", TipoPessoa.PF, "12345678901", "001", "1234", "56789-0");
        beneficiarioService.aprovar(beneficiario.getId());
        return beneficiario;
    }
 
    private Beneficiario cadastrarPendente() {
        return beneficiarioService.cadastrar(
                "João Souza", TipoPessoa.PF, "98765432100", "237", "4321", "98765-4");
    }
 
    private void cadastrarRecebivel(String valor, LocalDate data) {
        recebivelService.cadastrar("Venda", new BigDecimal(valor), data);
    }
 
    @Test
    @DisplayName("UT-43: cria Split válido com status PENDENTE e identificador 1")
    void deveCriarSplitValido() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        Split split = service.criar(beneficiario.getId(), new BigDecimal("800.00"), DATA_FUTURA);
 
        assertEquals(1, split.getId());
        assertEquals(StatusSplit.PENDENTE, split.getStatus());
        assertSame(split, service.buscarPorId(1));
    }
 
    @Test
    @DisplayName("UT-44: RN01 - rejeita Split para beneficiário pendente")
    void deveRejeitarBeneficiarioPendente() {
        Beneficiario pendente = cadastrarPendente();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.criar(pendente.getId(), new BigDecimal("800.00"), DATA_FUTURA));
 
        assertEquals("O beneficiário precisa estar aprovado para receber um Split.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-45: rejeita Split para beneficiário inexistente")
    void deveRejeitarBeneficiarioInexistente() {
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.criar(99, new BigDecimal("800.00"), DATA_FUTURA));
 
        assertEquals("Beneficiário não encontrado.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-46: RN02 - rejeita Split com data de hoje")
    void deveRejeitarDataDeHoje() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", HOJE);
 
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.criar(beneficiario.getId(), new BigDecimal("800.00"), HOJE));
 
        assertEquals("O Split deve ser configurado com no mínimo 1 dia de antecedência.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-47: RN02 - rejeita Split com data no passado")
    void deveRejeitarDataNoPassado() {
        Beneficiario beneficiario = cadastrarAprovado();
        LocalDate ontem = HOJE.minusDays(1);
        cadastrarRecebivel("1000.00", ontem);
 
        assertThrows(IllegalArgumentException.class,
                () -> service.criar(beneficiario.getId(), new BigDecimal("800.00"), ontem));
    }
 
    @Test
    @DisplayName("UT-48: RN02 - aceita Split com data de amanhã (borda da antecedência)")
    void deveAceitarDataDeAmanha() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", AMANHA);
 
        Split split = service.criar(beneficiario.getId(), new BigDecimal("800.00"), AMANHA);
 
        assertEquals(AMANHA, split.getDataRepasse());
    }
 
    @Test
    @DisplayName("UT-49: RN03 - rejeita valor acima dos recebíveis da data")
    void deveRejeitarValorAcimaDoRecebivel() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.criar(beneficiario.getId(), new BigDecimal("1500.00"), DATA_FUTURA));
 
        assertEquals("O valor do Split não pode ultrapassar o valor dos recebíveis da data.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-50: RN03 - aceita valor igual ao total dos recebíveis da data (borda)")
    void deveAceitarValorIgualAoRecebivel() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        Split split = service.criar(beneficiario.getId(), new BigDecimal("1000.00"), DATA_FUTURA);
 
        assertEquals(StatusSplit.PENDENTE, split.getStatus());
    }
 
    @Test
    @DisplayName("UT-51: rejeita Split em data sem recebíveis")
    void deveRejeitarDataSemRecebiveis() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.criar(beneficiario.getId(), new BigDecimal("100.00"), DATA_FUTURA.plusDays(1)));
 
        assertEquals("Não há recebíveis cadastrados para a data informada.", erro.getMessage());
    }
 
    @Test
    @DisplayName("UT-52: RN03 - soma os recebíveis da mesma data")
    void deveSomarRecebiveisDaMesmaData() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("600.00", DATA_FUTURA);
        cadastrarRecebivel("400.00", DATA_FUTURA);
 
        Split split = service.criar(beneficiario.getId(), new BigDecimal("1000.00"), DATA_FUTURA);
 
        assertEquals(0, new BigDecimal("1000.00").compareTo(split.getValor()));
    }
 
    @Test
    @DisplayName("UT-53: criação inválida lança exceção, não salva e não consome identificador")
    void naoDeveSalvarNemConsumirIdQuandoCriacaoForInvalida() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
 
        assertThrows(IllegalArgumentException.class,
                () -> service.criar(beneficiario.getId(), new BigDecimal("1500.00"), DATA_FUTURA));
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(1));
 
        Split valido = service.criar(beneficiario.getId(), new BigDecimal("800.00"), DATA_FUTURA);
 
        assertEquals(1, valido.getId());
    }
 
    @Test
    @DisplayName("UT-54: buscarPorId retorna o Split ou lança exceção se não existir")
    void deveBuscarSplitPorId() {
        Beneficiario beneficiario = cadastrarAprovado();
        cadastrarRecebivel("1000.00", DATA_FUTURA);
        Split split = service.criar(beneficiario.getId(), new BigDecimal("800.00"), DATA_FUTURA);
 
        assertSame(split, service.buscarPorId(split.getId()));
 
        IllegalArgumentException erro = assertThrows(IllegalArgumentException.class,
                () -> service.buscarPorId(99));
        assertEquals("Split não encontrado.", erro.getMessage());
    }
}