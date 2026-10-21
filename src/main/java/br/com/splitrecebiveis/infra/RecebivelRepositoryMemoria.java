package br.com.splitrecebiveis.infra;

import java.util.ArrayList;
import java.util.List;

import br.com.splitrecebiveis.domain.Recebivel;

public class RecebivelRepositoryMemoria {

    private final List<Recebivel> recebiveis = new ArrayList<>();

    public void salvar(Recebivel recebivel) {
        recebiveis.add(recebivel);
    }

    // Retorna uma cópia para que quem consulta não altere a lista interna.
    public List<Recebivel> listarTodos() {
        return new ArrayList<>(recebiveis);
    }
}