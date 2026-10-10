package br.com.splitrecebiveis.service;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
 
import java.util.List;
 
public class BeneficiarioService {
 
    private final BeneficiarioRepositoryMemoria repository;
   
    // Define o primeiro identificador que será atribuído a um beneficiário.
    private long proximoId = 1;
 
    public BeneficiarioService(BeneficiarioRepositoryMemoria repository) {
        this.repository = repository;
    }
 
    //Cadastra beneficiário e devolve para quem chamar (validação já foi feita no construtor do beneficiário).
    public Beneficiario cadastrar(String nome, TipoPessoa tipoPessoa, String documento, String banco,String agencia, String conta) {
        Beneficiario beneficiario = new Beneficiario(proximoId, nome, tipoPessoa, documento, banco, agencia, conta);
 
        repository.salvar(beneficiario);
 
        proximoId++;
 
        return beneficiario;
    }
 
    public List<Beneficiario> listarTodos() {
        return repository.listarTodos();
    }
}