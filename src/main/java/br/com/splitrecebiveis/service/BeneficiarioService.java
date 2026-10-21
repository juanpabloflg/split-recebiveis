package br.com.splitrecebiveis.service;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.StatusBeneficiario;
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
 
    // Devolve apenas os beneficiários que ainda aguardam aprovação.
    public List<Beneficiario> listarPendentes() {
        return repository.listarTodos().stream()
                .filter(beneficiario -> beneficiario.getStatus() == StatusBeneficiario.PENDENTE)
                .toList();
    }
 
    // Busca um beneficiário ou lança exceção se o identificador não existir.
    public Beneficiario buscarPorId(long id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Beneficiário não encontrado."));
    }
 
    // Aprova o beneficiário. O objeto está em memória, então a mudança de status já fica registrada.
    public Beneficiario aprovar(long id) {
        Beneficiario beneficiario = buscarPorId(id);
        beneficiario.aprovar();
        return beneficiario;
    }
}