package br.com.splitrecebiveis.ui;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.Recebivel;
import br.com.splitrecebiveis.domain.RegistroHistorico;
import br.com.splitrecebiveis.domain.Split;
import br.com.splitrecebiveis.domain.StatusSplit;
import br.com.splitrecebiveis.domain.TipoPessoa;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.service.HistoricoService;
import br.com.splitrecebiveis.service.RecebivelService;
import br.com.splitrecebiveis.service.SplitService;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
 
public class ConsoleUI {
 
    // STRICT rejeita datas que não existem, como 31/02/2026. Em STRICT o ano deve ser "uuuu".
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
 
    private final Scanner sc = new Scanner(System.in);
 
    private final BeneficiarioService beneficiarioService;
    private final RecebivelService recebivelService;
    private final SplitService splitService;
    private final HistoricoService historicoService;
 
    public ConsoleUI(BeneficiarioService beneficiarioService, RecebivelService recebivelService,
            SplitService splitService, HistoricoService historicoService) {
        this.beneficiarioService = beneficiarioService;
        this.recebivelService = recebivelService;
        this.splitService = splitService;
        this.historicoService = historicoService;
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
            System.out.println("6 - Criar Split");
            System.out.println("7 - Listar Splits");
            System.out.println("8 - Editar Split");
            System.out.println("9 - Excluir Split");
            System.out.println("10 - Executar Splits agendados");
            System.out.println("11 - Consultar histórico de Splits");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
 
            opcao = sc.nextLine().trim();
 
            switch (opcao) {
                case "1" -> cadastrarBeneficiario();
                case "2" -> listarBeneficiarios();
                case "3" -> aprovarBeneficiario();
                case "4" -> cadastrarRecebivel();
                case "5" -> listarRecebiveis();
                case "6" -> criarSplit();
                case "7" -> listarSplits();
                case "8" -> editarSplit();
                case "9" -> excluirSplit();
                case "10" -> executarSplits();
                case "11" -> consultarHistorico();
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
 
        if (beneficiarios.isEmpty()) {
            System.out.println("Nenhum beneficiário cadastrado.");
        } else {
            System.out.println("\nBeneficiários Cadastrados");
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
 
        if (recebiveis.isEmpty()) {
            System.out.println("Nenhum recebível cadastrado.");
        } else {
            System.out.println("\nRecebíveis Cadastrados");
            for (Recebivel recebivel : recebiveis) {
                System.out.println(recebivel);
            }
        }
 
        aguardarVoltar();
    }
 
    // Mostra os beneficiários aprovados e cria um Split para o escolhido pelo usuário.
    private void criarSplit() {
 
        System.out.println("\nCriação de Split");
 
        List<Beneficiario> aprovados = beneficiarioService.listarAprovados();
 
        if (aprovados.isEmpty()) {
            System.out.println("Nenhum beneficiário aprovado. Cadastre e aprove um beneficiário antes de criar um Split.");
            aguardarVoltar();
            return;
        }
 
        System.out.println("Beneficiários aprovados:\n");
        for (Beneficiario beneficiario : aprovados) {
            System.out.println(beneficiario);
        }
 
        try {
            System.out.print("ID do beneficiário: ");
            long beneficiarioId = Long.parseLong(sc.nextLine().trim());
 
            System.out.print("Valor do Split em R$ (ex.: 800,00): ");
            BigDecimal valor = lerValor(sc.nextLine());
 
            System.out.print("Data do repasse (dd/MM/aaaa): ");
            LocalDate dataRepasse = LocalDate.parse(sc.nextLine().trim(), FORMATO_DATA);
 
            Split split = splitService.criar(beneficiarioId, valor, dataRepasse);
 
            System.out.println("\nSplit criado com sucesso!");
            System.out.println(split);
 
        } catch (NumberFormatException e) {
            // Precisa vir antes de IllegalArgumentException, pois é uma subclasse dela.
            System.out.println("ID ou valor inválido. Use apenas números, por exemplo: 800,00.");
 
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/aaaa, por exemplo: 25/12/2026.");
 
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível criar o Split: " + e.getMessage());
        }
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
     
    // Consulta e exibe os Splits armazenados, com o status de cada um.
    private void listarSplits() {
 
        List<Split> splits = splitService.listarTodos();
 
        if (splits.isEmpty()) {
            System.out.println("Nenhum Split cadastrado.");
        } else {
            System.out.println("\nSplits Cadastrados");
            for (Split split : splits) {
                System.out.println(split);
            }
        }
 
        aguardarVoltar();
    }
    
    // Edita o valor e a data de um Split pendente. Enter mantém o valor atual.
    private void editarSplit() {

        System.out.println("\nEdição de Split");

        List<Split> splits = splitService.listarTodos();

        if (splits.isEmpty()) {
            System.out.println("Nenhum Split cadastrado.");
            aguardarVoltar();
            return;
        }

        for (Split split : splits) {
            System.out.println(split);
        }

        try {
            System.out.print("ID do Split a editar (ou 0 para cancelar): ");
            String entradaId = sc.nextLine().trim();

            if (entradaId.equals("0")) {
                System.out.println("Edição cancelada.");
                return;
            }

            Split atual = splitService.buscarPorId(Long.parseLong(entradaId));

            System.out.print("Novo valor em R$ (Enter para manter): ");
            String entradaValor = sc.nextLine().trim();
            BigDecimal novoValor = entradaValor.isEmpty() ? atual.getValor() : lerValor(entradaValor);

            System.out.print("Nova data do repasse, dd/MM/aaaa (Enter para manter): ");
            String entradaData = sc.nextLine().trim();
            LocalDate novaData = entradaData.isEmpty()
                    ? atual.getDataRepasse()
                    : LocalDate.parse(entradaData, FORMATO_DATA);

            Split editado = splitService.editar(atual.getId(), novoValor, novaData);

            System.out.println("\nSplit editado com sucesso!");
            System.out.println(editado);

        } catch (NumberFormatException e) {
            // Precisa vir antes de IllegalArgumentException, pois é uma subclasse dela.
            System.out.println("ID ou valor inválido. Use apenas números, por exemplo: 800,00.");

        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/aaaa, por exemplo: 25/12/2026.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível editar o Split: " + e.getMessage());
        }
    }

    // Exclui um Split pendente, depois de pedir confirmação.
    private void excluirSplit() {

        System.out.println("\nExclusão de Split");

        List<Split> splits = splitService.listarTodos();

        if (splits.isEmpty()) {
            System.out.println("Nenhum Split cadastrado.");
            aguardarVoltar();
            return;
        }

        for (Split split : splits) {
            System.out.println(split);
        }

        try {
            System.out.print("ID do Split a excluir (ou 0 para cancelar): ");
            String entradaId = sc.nextLine().trim();

            if (entradaId.equals("0")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            long id = Long.parseLong(entradaId);

            // Confere se o Split existe antes de pedir a confirmação.
            splitService.buscarPorId(id);

            System.out.print("Confirma a exclusão do Split " + id + "? (S/N): ");
            String confirmacao = sc.nextLine().trim().toUpperCase();

            if (!confirmacao.equals("S")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            splitService.excluir(id);

            System.out.println("\nSplit excluído com sucesso!");

        } catch (NumberFormatException e) {
            // Precisa vir antes de IllegalArgumentException, pois é uma subclasse dela.
            System.out.println("ID inválido. Digite apenas números.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível excluir o Split: " + e.getMessage());
        }
    }
    
    // Mostra a fila de prioridade de uma data e executa os Splits pendentes dela.
    private void executarSplits() {

        System.out.println("\nExecução de Splits");

        try {
            System.out.print("Data da execução, dd/MM/aaaa (Enter para hoje): ");
            String entrada = sc.nextLine().trim();
            LocalDate data = entrada.isEmpty() ? splitService.hoje() : LocalDate.parse(entrada, FORMATO_DATA);

            List<Split> fila = splitService.filaDaData(data);

            if (fila.isEmpty()) {
                System.out.println("Nenhum Split pendente para " + data.format(FORMATO_DATA) + ".");
                aguardarVoltar();
                return;
            }

            System.out.println("\nFila de execução em " + data.format(FORMATO_DATA) + " (ordem de configuração):");

            int posicao = 1;
            for (Split split : fila) {
                System.out.println(posicao + "º - Split " + split.getId() + " | "
                        + split.getBeneficiario().getNome() + " | " + formatarMoeda(split.getValor()));
                posicao++;
            }

            System.out.println("Saldo disponível nos recebíveis da data: "
                    + formatarMoeda(recebivelService.saldoDisponivelPorData(data)));

            System.out.print("\nConfirma a execução? (S/N): ");
            String confirmacao = sc.nextLine().trim().toUpperCase();

            if (!confirmacao.equals("S")) {
                System.out.println("Execução cancelada.");
                return;
            }

            List<Split> resultado = splitService.executarDaData(data);

            System.out.println("\nResultado da execução:\n");

            long executados = 0;
            for (Split split : resultado) {
                System.out.println(split);
                if (split.getStatus() == StatusSplit.EXECUTADO) {
                    executados++;
                }
            }

            System.out.println(executados + " Split(s) executado(s) e "
                    + (resultado.size() - executados) + " recusado(s).");

            aguardarVoltar();

        } catch (DateTimeParseException e) {
            System.out.println("Data inválida. Use o formato dd/MM/aaaa, por exemplo: 25/12/2026.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Não foi possível executar os Splits: " + e.getMessage());
        }
    }

    // Formata um valor em reais, por exemplo "R$ 1.500,50".
    private String formatarMoeda(BigDecimal valor) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %,.2f", valor);
    }

    // Exibe o histórico das operações feitas nos Splits, na ordem em que aconteceram.
    private void consultarHistorico() {
 
        List<RegistroHistorico> registros = historicoService.listarTodos();
 
        System.out.println("\nHistórico de Splits");
 
        if (registros.isEmpty()) {
            System.out.println("Nenhuma operação registrada.");
        } else {
            for (RegistroHistorico registro : registros) {
                System.out.println(registro);
            }
            System.out.println("\nTotal: " + registros.size() + " operação(ões) registrada(s).");
        }
 
        aguardarVoltar();
    }
}