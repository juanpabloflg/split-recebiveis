package br.com.splitrecebiveis.domain;
 
import java.math.BigDecimal;
import java.util.Locale;
 
/**
* Resumo das operações do sistema: beneficiários, recebíveis, Splits por status e histórico.
*/
public record RelatorioResumo(
        int totalBeneficiarios,
        int beneficiariosAprovados,
        int beneficiariosPendentes,
        int totalRecebiveis,
        BigDecimal valorTotalRecebiveis,
        BigDecimal saldoDisponivelRecebiveis,
        int totalSplits,
        int splitsPendentes,
        int splitsExecutados,
        int splitsRecusados,
        BigDecimal valorPendente,
        BigDecimal valorExecutado,
        BigDecimal valorRecusado,
        int operacoesRegistradas) {
 
    private static String moeda(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Beneficiários\n");
        sb.append("  Total: " + totalBeneficiarios + "\n");
        sb.append("  Aprovados: " + beneficiariosAprovados + "\n");
        sb.append("  Pendentes: " + beneficiariosPendentes + "\n");
        sb.append("\nRecebíveis\n");
        sb.append("  Quantidade: " + totalRecebiveis + "\n");
        sb.append("  Valor total: " + moeda(valorTotalRecebiveis) + "\n");
        sb.append("  Saldo disponível: " + moeda(saldoDisponivelRecebiveis) + "\n");
        sb.append("\nSplits\n");
        sb.append("  Total: " + totalSplits + "\n");
        sb.append("  Pendentes: " + splitsPendentes + " (" + moeda(valorPendente) + ")\n");
        sb.append("  Executados: " + splitsExecutados + " (" + moeda(valorExecutado) + ")\n");
        sb.append("  Recusados: " + splitsRecusados + " (" + moeda(valorRecusado) + ")\n");
        sb.append("\nHistórico\n");
        sb.append("  Operações registradas: " + operacoesRegistradas + "\n");
        return sb.toString();
    }
}