package br.com.splitrecebiveis.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Valor que o estabelecimento tem a receber em uma data de liquidação.
 * É a partir dele que, nas próximas Sprints, os Splits serão executados.
 */
public class Recebivel {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final long id;
    private final String descricao;
    private final BigDecimal valorTotal;

    // Começa igual ao valor total. Na Sprint 2/3 diminuirá conforme os Splits forem executados.
    private BigDecimal valorDisponivel;

    private final LocalDate dataLiquidacao;

    public Recebivel(long id, String descricao, BigDecimal valorTotal, LocalDate dataLiquidacao) {

        if (id <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória.");
        }

        if (valorTotal == null) {
            throw new IllegalArgumentException("O valor do recebível é obrigatório.");
        }

        if (valorTotal.signum() <= 0) {
            throw new IllegalArgumentException("O valor do recebível deve ser maior que zero.");
        }

        // Aceita no máximo centavos. 1000 e 1000.00 são equivalentes; 10.999 é rejeitado.
        if (valorTotal.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException("O valor deve ter no máximo 2 casas decimais.");
        }

        if (dataLiquidacao == null) {
            throw new IllegalArgumentException("A data de liquidação é obrigatória.");
        }

        this.id = id;
        this.descricao = descricao;
        this.valorTotal = valorTotal.setScale(2, RoundingMode.UNNECESSARY);
        this.valorDisponivel = this.valorTotal;
        this.dataLiquidacao = dataLiquidacao;
    }

    public long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getValorDisponivel() {
        return valorDisponivel;
    }

    public LocalDate getDataLiquidacao() {
        return dataLiquidacao;
    }

    private static String formatarMoeda(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: " + id + "\n");
        sb.append("Descrição: " + descricao + "\n");
        sb.append("Valor total: " + formatarMoeda(valorTotal) + "\n");
        sb.append("Valor disponível: " + formatarMoeda(valorDisponivel) + "\n");
        sb.append("Data de liquidação: " + dataLiquidacao.format(FORMATO_DATA) + "\n");
        return sb.toString();
    }
}