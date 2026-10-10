package br.com.splitrecebiveis.infra;
 
import java.util.ArrayList;
import java.util.List;
 
import br.com.splitrecebiveis.domain.Beneficiario;
 
public class BeneficiarioRepositoryMemoria {
 
 
    private final List<Beneficiario> beneficiarios = new ArrayList<>();
 
    public void salvar(Beneficiario beneficiario) {
        beneficiarios.add(beneficiario);
    }
 
    /*Retorna uma cópia da lista de beneficiários cadastrados.
    A cópia é enviada para não enviar a lista interna diretamente. */
    public List<Beneficiario> listarTodos() {
        return new ArrayList<>(beneficiarios);
    }
}