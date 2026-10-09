package br.com.splitrecebiveis.domain;
 
public class Beneficiario {
 
    private final long id;
    private final String nome;
    private final TipoPessoa tipoPessoa;
    private final String documento;
    private final String banco;
    private final String agencia;
    private final String conta;
 
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
   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: " + nome + "\n");
        sb.append("Documento: " + documento + "\n");
        sb.append("Banco: " + banco + "\n");
        sb.append("Agência: " + agencia + "\n");
        sb.append("Conta: " + conta + "\n");
        return sb.toString();
    }
   
}
 