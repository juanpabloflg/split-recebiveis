package br.com.splitrecebiveis.service;
 
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;
 
public class SplitService {
 
    private final SplitRepositoryMemoria repository;
    private final BeneficiarioService beneficiarioService;
    private final RecebivelService recebivelService;
 
    // Relógio injetado: no sistema é o relógio real; nos testes é uma data fixa.
    private final Clock clock;
 
    // Primeiro identificador que será atribuído a um Split.
    private long proximoId = 1;
 
    public SplitService(SplitRepositoryMemoria repository,
            BeneficiarioService beneficiarioService,
            RecebivelService recebivelService,
            Clock clock) {
        this.repository = repository;
        this.beneficiarioService = beneficiarioService;
        this.recebivelService = recebivelService;
        this.clock = clock;
    }
 
    // Cria um Split pendente aplicando RN01, RN02 e RN03.
    // Se qualquer regra falhar, nada é salvo e o identificador não é consumido.
    public Split criar(long beneficiarioId, BigDecimal valor, LocalDate dataRepasse) {
        Beneficiario beneficiario = beneficiarioService.buscarPorId(beneficiarioId);
 
        // RN01: o beneficiário precisa estar aprovado.
        if (!beneficiario.isAprovado()) {
            throw new IllegalArgumentException("O beneficiário precisa estar aprovado para receber um Split.");
        }
 
        validarAgendamento(valor, dataRepasse);
 
        Split split = new Split(proximoId, beneficiario, valor, dataRepasse);
 
        repository.salvar(split);
 
        proximoId++;
 
        return split;
    }
 
    // Busca um Split ou lança exceção se o identificador não existir.
    public Split buscarPorId(long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Split não encontrado."));
    }
 
    // Regras de valor e data, compartilhadas pela criação e pela edição.
    private void validarAgendamento(BigDecimal valor, LocalDate dataRepasse) {
        Split.validarValor(valor);
        Split.validarData(dataRepasse);
 
        // RN02: antecedência mínima de 1 dia (a data precisa ser posterior a hoje).
        LocalDate hoje = LocalDate.now(clock);
        if (!dataRepasse.isAfter(hoje)) {
            throw new IllegalArgumentException("O Split deve ser configurado com no mínimo 1 dia de antecedência.");
        }
 
        // RN03: o valor não pode ultrapassar os recebíveis com liquidação na data do Split.
        BigDecimal totalDaData = recebivelService.totalPorData(dataRepasse);
 
        if (totalDaData.signum() == 0) {
            throw new IllegalArgumentException("Não há recebíveis cadastrados para a data informada.");
        }
 
        if (valor.compareTo(totalDaData) > 0) {
            throw new IllegalArgumentException("O valor do Split não pode ultrapassar o valor dos recebíveis da data.");
        }
    }
}