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
}