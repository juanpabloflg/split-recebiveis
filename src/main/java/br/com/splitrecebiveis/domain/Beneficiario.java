package br.com.splitrecebiveis.domain;
 
public class Beneficiario {
 
    private final long id;
    private final String nome;
    private final TipoPessoa tipoPessoa;
    private final String documento;
    private final String banco;
    private final String agencia;
    private final String conta;
 
    // Único campo que muda depois da criação: PENDENTE -> APROVADO.
    private StatusBeneficiario status;
 
    public Beneficiario(long id, String nome, TipoPessoa tipoPessoa, String documento, String banco, String agencia,
            String conta) {
        if(id <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
 
        if(nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
 
        if(tipoPessoa == null) {
            throw new IllegalArgumentException("O tipo de pessoa é obrigatório.");
        }   
        if(documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("O documento é obrigatório");
        }
 
        if(banco == null || banco.isBlank()
        || agencia == null || agencia.isBlank()
        || conta == null || conta.isBlank()) {
            throw new IllegalArgumentException("Banco, agência e conta são obrigatórios.");
        }
        this.id = id;
        this.nome = nome;
        this.tipoPessoa = tipoPessoa;
        this.documento = documento;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
 
        // RN08: todo beneficiário é cadastrado como pendente.
        this.status = StatusBeneficiario.PENDENTE;
    }
 
    public long getId() {
        return id;
    }
 
    public String getNome() {
        return nome;
    }
 
    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }
 
    public String getDocumento() {
        return documento;
    }
 
    public String getBanco() {
        return banco;
    }
 
    public String getAgencia() {
        return agencia;
    }
 
    public String getConta() {
        return conta;
    }
 
    public StatusBeneficiario getStatus() {
        return status;
    }
 
    public boolean isAprovado() {
        return status == StatusBeneficiario.APROVADO;
    }
 
    // Aprova o beneficiário. Aprovar duas vezes é considerado erro de uso.
    public void aprovar() {
        if (isAprovado()) {
            throw new IllegalStateException("O beneficiário já está aprovado.");
        }
        this.status = StatusBeneficiario.APROVADO;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + id + "\n");
        sb.append("Nome: " + nome + "\n");
        sb.append("Tipo: " + tipoPessoa + "\n");
        sb.append("Documento: " + documento + "\n");
        sb.append("Banco: " + banco + "\n");
        sb.append("Agência: " + agencia + "\n");
        sb.append("Conta: " + conta + "\n");
        sb.append("Status: " + status + "\n");
        return sb.toString(); 
    }
}