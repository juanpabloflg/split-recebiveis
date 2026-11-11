package br.com.splitrecebiveis.infra;
 
import java.util.ArrayList;
import java.util.List;
 
import br.com.splitrecebiveis.domain.RegistroHistorico;
 
public class HistoricoRepositoryMemoria {
 
    private final List<RegistroHistorico> registros = new ArrayList<>();
 
    public void salvar(RegistroHistorico registro) {
        registros.add(registro);
    }
 
    // Retorna uma cópia, na ordem em que as operações aconteceram.
    public List<RegistroHistorico> listarTodos() {
        return new ArrayList<>(registros);
    }
}