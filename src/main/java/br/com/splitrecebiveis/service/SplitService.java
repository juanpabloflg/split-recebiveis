package br.com.splitrecebiveis.service;
 
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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

    // Lista todos os Splits, na ordem em que foram criados.
    public List<Split> listarTodos() {
        return repository.listarTodos();
    }
    
    // Edita o valor e a data de um Split pendente.
    // Se qualquer regra falhar, o Split continua exatamente como estava.
    public Split editar(long id, BigDecimal novoValor, LocalDate novaDataRepasse) {
        Split split = buscarPorId(id);

        if (!split.isPendente()) {
            throw new IllegalStateException("Apenas Splits pendentes podem ser editados.");
        }

        validarAgendamento(novoValor, novaDataRepasse);

        split.editar(novoValor, novaDataRepasse);

        return split;
    }

    // Exclui um Split pendente.
    public void excluir(long id) {
        Split split = buscarPorId(id);

        if (!split.isPendente()) {
            throw new IllegalStateException("Apenas Splits pendentes podem ser excluídos.");
        }

        repository.remover(split.getId());
    }
    
    // Data de hoje segundo o relógio do serviço (o mesmo usado nas regras de antecedência).
    public LocalDate hoje() {
        return LocalDate.now(clock);
    }

    // Fila de prioridade: Splits pendentes da data, na ordem em que foram configurados.
    public List<Split> filaDaData(LocalDate data) {
        return repository.listarTodos().stream()
                .filter(Split::isPendente)
                .filter(split -> split.getDataRepasse().equals(data))
                .sorted(Comparator.comparingLong(Split::getId))
                .toList();
    }

    // Executa a fila da data, um Split de cada vez.
    // Com saldo suficiente, o valor é debitado dos recebíveis e o Split fica Executado.
    // Sem saldo, o Split fica Recusado e nenhum valor é repassado.
    public List<Split> executarDaData(LocalDate data) {
        List<Split> fila = filaDaData(data);

        for (Split split : fila) {
            BigDecimal saldo = recebivelService.saldoDisponivelPorData(data);

            if (saldo.compareTo(split.getValor()) >= 0) {
                recebivelService.debitarPorData(data, split.getValor());
                split.marcarComoExecutado();
            } else {
                split.recusar("Saldo insuficiente: disponível " + formatarMoeda(saldo)
                        + " para um Split de " + formatarMoeda(split.getValor()) + ".");
            }
        }

        return fila;
    }

    private static String formatarMoeda(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }
}