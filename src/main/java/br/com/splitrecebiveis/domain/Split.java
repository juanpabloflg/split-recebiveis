package br.com.splitrecebiveis.domain;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
 
/**
* Divisão de um recebível: valor que será repassado a um beneficiário em uma data programada.
* As regras que dependem de outros objetos (beneficiário aprovado, antecedência e recebíveis)
* ficam no SplitService. Aqui ficam as validações do próprio Split e as mudanças de status.
*/
public class Split {
 
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
    private final long id;
    private final Beneficiario beneficiario;
    private BigDecimal valor;
    private LocalDate dataRepasse;
    private StatusSplit status;
 
    public Split(long id, Beneficiario beneficiario, BigDecimal valor, LocalDate dataRepasse) {
 
        if (id <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
 
        if (beneficiario == null) {
            throw new IllegalArgumentException("O beneficiário é obrigatório.");
        }
 
        validarValor(valor);
        validarData(dataRepasse);
 
        this.id = id;
        this.beneficiario = beneficiario;
        this.valor = valor.setScale(2, RoundingMode.UNNECESSARY);
        this.dataRepasse = dataRepasse;
        this.status = StatusSplit.PENDENTE;
    }
 
    // Validações do valor, usadas na criação e na edição.
    public static void validarValor(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O valor do Split é obrigatório.");
        }
 
        if (valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor do Split deve ser maior que zero.");
        }
 
        // Aceita no máximo centavos. 800 e 800.00 são equivalentes; 10.999 é rejeitado.
        if (valor.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException("O valor deve ter no máximo 2 casas decimais.");
        }
    }
 
    public static void validarData(LocalDate dataRepasse) {
        if (dataRepasse == null) {
            throw new IllegalArgumentException("A data do repasse é obrigatória.");
        }
    }
 
    public long getId() {
        return id;
    }
 
    public Beneficiario getBeneficiario() {
        return beneficiario;
    }
 
    public BigDecimal getValor() {
        return valor;
    }
 
    public LocalDate getDataRepasse() {
        return dataRepasse;
    }
 
    public StatusSplit getStatus() {
        return status;
    }
 
    public boolean isPendente() {
        return status == StatusSplit.PENDENTE;
    }
 
    // RN05: só um Split pendente pode ser editado.
    public void editar(BigDecimal novoValor, LocalDate novaDataRepasse) {
        exigirPendente("editados");
        validarValor(novoValor);
        validarData(novaDataRepasse);
 
        this.valor = novoValor.setScale(2, RoundingMode.UNNECESSARY);
        this.dataRepasse = novaDataRepasse;
    }
 
    // Usado pela execução do Split.
    public void marcarComoExecutado() {
        exigirPendente("executados");
        this.status = StatusSplit.EXECUTADO;
    }
 
    // Usado quando o saldo é insuficiente.
    public void recusar() {
        exigirPendente("recusados");
        this.status = StatusSplit.RECUSADO;
    }
 
    private void exigirPendente(String acao) {
        if (!isPendente()) {
            throw new IllegalStateException("Apenas Splits pendentes podem ser " + acao + ".");
        }
    }
 
    private static String formatarMoeda(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + id + "\n");
        sb.append("Beneficiário: " + beneficiario.getNome() + " (ID " + beneficiario.getId() + ")\n");
        sb.append("Valor: " + formatarMoeda(valor) + "\n");
        sb.append("Data do repasse: " + dataRepasse.format(FORMATO_DATA) + "\n");
        sb.append("Status: " + status + "\n");
        return sb.toString();
    }
}