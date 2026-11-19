package br.com.splitrecebiveis.infra;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import br.com.splitrecebiveis.domain.Split;
 
public class SplitRepositoryMemoria {
 
    private final List<Split> splits = new ArrayList<>();
 
    public void salvar(Split split) {
        splits.add(split);
    }
 
    // Retorna uma cópia para que quem consulta não altere a lista interna.
    public List<Split> listarTodos() {
        return new ArrayList<>(splits);
    }
 
    public Optional<Split> buscarPorId(long id) {
        return splits.stream()
                .filter(split -> split.getId() == id)
                .findFirst();
    }
 
    // Remove o Split com o identificador informado.
    public void remover(long id) {
        splits.removeIf(split -> split.getId() == id);
    }
}