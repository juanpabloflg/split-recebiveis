package br.com.splitrecebiveis.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.splitrecebiveis.domain.Recebivel;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;

public class RecebivelService {

    private final RecebivelRepositoryMemoria repository;

    // Primeiro identificador que será atribuído a um recebível.
    private long proximoId = 1;

    public RecebivelService(RecebivelRepositoryMemoria repository) {
        this.repository = repository;
    }

    // Cadastra o recebível. A validação é feita no construtor de Recebivel;
    // se ela falhar, nada é salvo e o identificador não é consumido.
    public Recebivel cadastrar(String descricao, BigDecimal valorTotal, LocalDate dataLiquidacao) {
        Recebivel recebivel = new Recebivel(proximoId, descricao, valorTotal, dataLiquidacao);

        repository.salvar(recebivel);

        proximoId++;

        return recebivel;
    }

    public List<Recebivel> listarTodos() {
        return repository.listarTodos();
    }

    // Soma o valor total dos recebíveis com liquidação na data informada.
    // Retorna zero quando não há recebíveis nessa data.
    public BigDecimal totalPorData(LocalDate data) {
        return repository.listarTodos().stream()
                .filter(recebivel -> recebivel.getDataLiquidacao().equals(data))
                .map(Recebivel::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    // Soma o valor disponível dos recebíveis com liquidação na data informada.
    // Retorna zero quando não há recebíveis nessa data.
    public BigDecimal saldoDisponivelPorData(LocalDate data) {
        return repository.listarTodos().stream()
                .filter(recebivel -> recebivel.getDataLiquidacao().equals(data))
                .map(Recebivel::getValorDisponivel)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Debita o valor dos recebíveis da data, na ordem em que foram cadastrados.
    // Um valor maior que o saldo da data é rejeitado e nada é debitado.
    public void debitarPorData(LocalDate data, BigDecimal valor) {
        if (saldoDisponivelPorData(data).compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente nos recebíveis da data.");
        }

        BigDecimal restante = valor;

        for (Recebivel recebivel : repository.listarTodos()) {
            if (restante.signum() == 0) {
                break;
            }

            if (!recebivel.getDataLiquidacao().equals(data)) {
                continue;
            }

            BigDecimal debito = recebivel.getValorDisponivel().min(restante);

            if (debito.signum() > 0) {
                recebivel.debitar(debito);
                restante = restante.subtract(debito);
            }
        }
    }
}