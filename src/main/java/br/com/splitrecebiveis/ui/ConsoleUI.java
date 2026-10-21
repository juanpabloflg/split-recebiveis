package br.com.splitrecebiveis.ui;

import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Recebivel;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.service.RecebivelService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    // STRICT rejeita datas que não existem, como 31/02/2026. Em STRICT o ano deve ser "uuuu".
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final Scanner sc = new Scanner(System.in);

    private final BeneficiarioService beneficiarioService;
    private final RecebivelService recebivelService;

    public ConsoleUI(BeneficiarioService beneficiarioService, RecebivelService recebivelService) {
        this.beneficiarioService = beneficiarioService;
        this.recebivelService = recebivelService;
    }


    public void iniciar() {
        String opcao;

        do {
            System.out.println("\nGestão de Recebíveis com Split");
            System.out.println("1 - Cadastrar beneficiário");
            System.out.println("2 - Listar beneficiários");
            System.out.println("3 - Aprovar beneficiário");
            System.out.println("4 - Cadastrar recebível");
            System.out.println("5 - Listar recebíveis");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextLine().trim();

            switch (opcao) {
                case "1" -> cadastrarBeneficiario();
                case "2" -> listarBeneficiarios();
                case "3" -> aprovarBeneficiario();
                case "4" -> cadastrarRecebivel();
                case "5" -> listarRecebiveis();
                case "0" -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }

        } while (!opcao.equals("0"));
    }

    private void cadastrarBeneficiario() {

        try {
            System.out.println("\nCadastro de Beneficiário");

            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("Tipo de pessoa (PF/PJ): ");

            TipoPessoa tipoPessoa;

            // Tenta converter a entrada para uma constante do enum.
            try {
                tipoPessoa = TipoPessoa.valueOf(
                    sc.nextLine().trim().toUpperCase()
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                    "Tipo de pessoa inválido. Digite PF ou PJ."
                );
                return;
            }

            // Exibe "CPF:" se o tipo de pessoa for PF, caso contrário, exibe "CNPJ:".
            System.out.print(tipoPessoa == TipoPessoa.PF ? "CPF: " : "CNPJ: ");
            String documento = sc.nextLine();

    
            System.out.print("Banco: ");
            String banco = sc.nextLine();

            System.out.print("Agência: ");
            String agencia = sc.nextLine();

            System.out.print("Conta: ");
            String conta = sc.nextLine();

            Beneficiario beneficiario = beneficiarioService.cadastrar(nome, tipoPessoa, documento, banco, agencia, conta);

            System.out.println("\nBeneficiário cadastrado com sucesso!");
            System.out.println(beneficiario);

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Não foi possível cadastrar: " + e.getMessage()
            );
        }
    }

    // Consulta e exibe os beneficiários armazenados.
    private void listarBeneficiarios() {

        // Solicita ao serviço a lista de beneficiários.
        List<Beneficiario> beneficiarios = beneficiarioService.listarTodos();

        System.out.println("\nBeneficiários Cadastrados");

        if (beneficiarios.isEmpty()) {
            System.out.println("Nenhum beneficiário cadastrado.");
        } else {
            for (Beneficiario beneficiario : beneficiarios) {
                System.out.println(beneficiario);
            }
        }

        // Mantém o resultado na tela até o usuário decidir voltar.
        aguardarVoltar();
    }

    // Mostra os beneficiários pendentes e aprova o escolhido pelo usuário.
    private void aprovarBeneficiario() {

        System.out.println("\nAprovação de Beneficiário");

        List<Beneficiario> pendentes = beneficiarioService.listarPendentes();

        if (pendentes.isEmpty()) {
            System.out.println("Nenhum beneficiário pendente de aprovação.");
            aguardarVoltar();
            return;
        }

        System.out.println("Beneficiários pendentes:\n");
        for (Beneficiario beneficiario : pendentes) {
            System.out.println(beneficiario);
        }

        System.out.print("Digite o ID do beneficiário a aprovar (ou 0 para cancelar): ");
        String entrada = sc.nextLine().trim();

        if (entrada.equals("0")) {
            System.out.println("Aprovação cancelada.");
            return;
        }

        try {
            long id = Long.parseLong(entrada);
            Beneficiario aprovado = beneficiarioService.aprovar(id);

            System.out.println("\nBeneficiário aprovado com sucesso!");
            System.out.println(aprovado);

        } catch (NumberFormatException e) {
            // Precisa vir antes de IllegalArgumentException, pois é uma subclasse dela.
            System.out.println("ID inválido. Digite apenas números.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível aprovar: " + e.getMessage());
        }
    }

    // Lê os dados de um recebível e pede o cadastro ao serviço.
    private void cadastrarRecebivel() {

        try {
            System.out.println("\nCadastro de Recebível");

            System.out.print("Descrição: ");
            String descricao = sc.nextLine();

            System.out.print("Valor em R$ (ex.: 1500,50): ");
            BigDecimal valor = lerValor(sc.nextLine());

            System.out.print("Data de liquidação (dd/MM/aaaa): ");
            LocalDate dataLiquidacao = LocalDate.parse(sc.nextLine().trim(), FORMATO_DATA);

            Recebivel recebivel = recebivelService.cadastrar(descricao, valor, dataLiquidacao);

            System.out.println("\nRecebível cadastrado com sucesso!");
            System.out.println(recebivel);

        } catch (NumberFormatException e) {
            // Precisa vir antes de IllegalArgumentException, pois é uma subclasse dela.
            System.out.println("Valor inválido. Use apenas números, por exemplo: 1500,50.");

        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/aaaa, por exemplo: 25/12/2026.");

        } catch (IllegalArgumentException e) {
            System.out.println("Não foi possível cadastrar: " + e.getMessage());
        }
    }

    // Consulta e exibe os recebíveis armazenados.
    private void listarRecebiveis() {

        List<Recebivel> recebiveis = recebivelService.listarTodos();

        System.out.println("\nRecebíveis Cadastrados");

        if (recebiveis.isEmpty()) {
            System.out.println("Nenhum recebível cadastrado.");
        } else {
            for (Recebivel recebivel : recebiveis) {
                System.out.println(recebivel);
            }
        }

        aguardarVoltar();
    }

    // Aceita "1500", "1500.50", "1500,50" e "1.500,50".
    private BigDecimal lerValor(String texto) {
        String normalizado = texto.trim();

        // Com vírgula, o ponto é separador de milhar e deve ser removido.
        if (normalizado.contains(",")) {
            normalizado = normalizado.replace(".", "").replace(",", ".");
        }

        return new BigDecimal(normalizado);
    }

    // Segura a tela de consulta até o usuário digitar 0 para voltar ao menu.
    private void aguardarVoltar() {
        String entrada;

        do {
            System.out.print("\nPara voltar ao menu, digite 0: ");
            entrada = sc.nextLine().trim();
        } while (!entrada.equals("0"));
    }
}