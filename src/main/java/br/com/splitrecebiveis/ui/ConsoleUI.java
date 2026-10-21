package br.com.splitrecebiveis.ui;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.service.BeneficiarioService;
 
import java.util.List;
import java.util.Scanner;
 
public class ConsoleUI {
 
    private final Scanner sc = new Scanner(System.in);
 
    private final BeneficiarioService beneficiarioService;
 
    public ConsoleUI(BeneficiarioService beneficiarioService) {
        this.beneficiarioService = beneficiarioService;
    }
 
 
    public void iniciar() {
        String opcao;
 
        do {
            System.out.println("\nGestão de Recebíveis com Split");
            System.out.println("1 - Cadastrar beneficiário");
            System.out.println("2 - Listar beneficiários");
            System.out.println("3 - Aprovar beneficiário");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
 
            opcao = sc.nextLine().trim();
 
            switch (opcao) {
                case "1" -> cadastrarBeneficiario();
                case "2" -> listarBeneficiarios();
                case "3" -> aprovarBeneficiario();
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
 
    // Segura a tela de consulta até o usuário digitar 0 para voltar ao menu.
    private void aguardarVoltar() {
        String entrada;
 
        do {
            System.out.print("\nPara voltar ao menu, digite 0: ");
            entrada = sc.nextLine().trim();
        } while (!entrada.equals("0"));
    }
}