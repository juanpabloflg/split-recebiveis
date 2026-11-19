package br.com.splitrecebiveis.service;
 
import java.math.BigDecimal;

import java.util.List;
 
import br.com.splitrecebiveis.domain.Beneficiario;

import br.com.splitrecebiveis.domain.Recebivel;

import br.com.splitrecebiveis.domain.RelatorioResumo;

import br.com.splitrecebiveis.domain.Split;

import br.com.splitrecebiveis.domain.StatusSplit;
 
public class RelatorioService {
 
    private final BeneficiarioService beneficiarioService;

    private final RecebivelService recebivelService;

    private final SplitService splitService;

    private final HistoricoService historicoService;
 
    public RelatorioService(BeneficiarioService beneficiarioService, RecebivelService recebivelService,

            SplitService splitService, HistoricoService historicoService) {

        this.beneficiarioService = beneficiarioService;

        this.recebivelService = recebivelService;

        this.splitService = splitService;

        this.historicoService = historicoService;

    }
 
    // Monta o resumo com os números atuais do sistema.

    public RelatorioResumo gerarResumo() {

        List<Beneficiario> beneficiarios = beneficiarioService.listarTodos();

        List<Recebivel> recebiveis = recebivelService.listarTodos();

        List<Split> splits = splitService.listarTodos();
 
        int aprovados = (int) beneficiarios.stream().filter(Beneficiario::isAprovado).count();
 
        BigDecimal valorTotalRecebiveis = recebiveis.stream()

                .map(Recebivel::getValorTotal)

                .reduce(BigDecimal.ZERO, BigDecimal::add);
 
        BigDecimal saldoDisponivel = recebiveis.stream()

                .map(Recebivel::getValorDisponivel)

                .reduce(BigDecimal.ZERO, BigDecimal::add);
 
        return new RelatorioResumo(

                beneficiarios.size(),

                aprovados,

                beneficiarios.size() - aprovados,

                recebiveis.size(),

                valorTotalRecebiveis,

                saldoDisponivel,

                splits.size(),

                contar(splits, StatusSplit.PENDENTE),

                contar(splits, StatusSplit.EXECUTADO),

                contar(splits, StatusSplit.RECUSADO),

                somar(splits, StatusSplit.PENDENTE),

                somar(splits, StatusSplit.EXECUTADO),

                somar(splits, StatusSplit.RECUSADO),

                historicoService.listarTodos().size());

    }
 
    private int contar(List<Split> splits, StatusSplit status) {

        return (int) splits.stream().filter(split -> split.getStatus() == status).count();

    }
 
    private BigDecimal somar(List<Split> splits, StatusSplit status) {

        return splits.stream()

                .filter(split -> split.getStatus() == status)

                .map(Split::getValor)

                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

}
 