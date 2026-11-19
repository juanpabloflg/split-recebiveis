# Sistema de Gestão de Recebíveis com Split de Pagamentos
 
## Descrição do Projeto
 
Trabalho prático desenvolvido para a disciplina integradora (A3) do ecossistema Ânima HUB, com foco na aplicação de princípios de Qualidade de Software, Versionamento e Testes Automatizados.
 
O projeto é uma aplicação Java de console para gerenciamento de recebíveis por meio do mecanismo de Split, que divide o valor a receber entre um estabelecimento e seus beneficiários antes da liquidação.
 
O sistema permite cadastrar e aprovar beneficiários, cadastrar recebíveis, configurar e acompanhar Splits, executá-los respeitando a prioridade e o saldo disponível, e consultar o histórico e um relatório resumido das operações.
 
## Problema e Público-alvo
 
**Problema:** dividir o valor recebido entre o estabelecimento e terceiros antes da liquidação, com controle de aprovação dos beneficiários, prazos e saldo disponível, evitando repasses manuais, sem rastreio e sujeitos a erro.
 
**Público-alvo:** estabelecimentos comerciais que repassam parte do faturamento a fornecedores e prestadores de serviço.
 
## Funcionalidades
 
O menu do sistema oferece 12 funcionalidades:
 
| Opção | Funcionalidade | Descrição |
| --- | --- | --- |
| 1 | Cadastrar beneficiário | Registra pessoa física ou jurídica com dados bancários. Nasce com status Pendente. |
| 2 | Listar beneficiários | Consulta os beneficiários cadastrados e seus status. |
| 3 | Aprovar beneficiário | Aprova um beneficiário pendente, habilitando-o a receber Splits. |
| 4 | Cadastrar recebível | Registra descrição, valor e data de liquidação. |
| 5 | Listar recebíveis | Consulta os recebíveis, com valor total e valor disponível. |
| 6 | Criar Split | Cria um Split pendente para um beneficiário aprovado, com valor e data do repasse. |
| 7 | Listar Splits | Consulta os Splits e o status de cada um. |
| 8 | Editar Split | Altera o valor e a data de um Split pendente. |
| 9 | Excluir Split | Exclui um Split pendente, com confirmação. |
| 10 | Executar Splits agendados | Executa os Splits pendentes de uma data, na ordem de configuração, debitando os recebíveis. Sem saldo, o Split é recusado, com o motivo. |
| 11 | Consultar histórico de Splits | Exibe criação, edição, exclusão, execução e recusa, com data e hora. |
| 12 | Gerar relatório resumido | Resume beneficiários, recebíveis, Splits por status e operações registradas. |
 
**Principais regras de negócio:** beneficiário aprovado para receber Split, antecedência mínima de 1 dia, valor limitado aos recebíveis da data, edição e exclusão só de Split pendente, prioridade por ordem de configuração e recusa por saldo insuficiente. O detalhamento está em [Regras de negócio](docs/requisitos/regras-de-negocio.md).
 
### Status atual (Sprint 2)
 
- Código com todas as funcionalidades do menu implementado.
- Testes unitários, roteiros manuais e cenários BDD documentados até a criação, listagem, edição e exclusão de Split.
- Testes e documentação da execução, do histórico e do relatório: Sprint 3.
 
## Tecnologias Utilizadas
 
- **Linguagem:** Java 25
- **Gerenciamento de Build e Dependências:** Apache Maven
- **Testes de Unidade:** JUnit 5
- **Desenvolvimento Guiado por Comportamento (BDD):** Cucumber (Gherkin em português)
- **Métrica de Cobertura de Código:** JaCoCo
- **Controle de Versão:** Git / GitHub (Issues, Projects, Pull Requests)
 
## Como Executar
 
Pré-requisitos: JDK 25 e Maven instalados. Na pasta do projeto (onde está o `pom.xml`):
 
```
mvn compile
java -cp target/classes br.com.splitrecebiveis.App
```
 
## Como Rodar os Testes
 
```
mvn clean test
```
 
- **Cobertura (JaCoCo):** relatório em `target/site/jacoco/index.html`.
- **Cenários BDD (Cucumber):** relatório em `target/cucumber-report.html`. Para rodar só o BDD: `mvn test -Dtest=RunCucumberTest`.
- **Evidências:** saídas do Maven, relatórios e prints dos roteiros em `docs/testes/evidencias/`.
 
## Estrutura de Pastas
 
```
split-recebiveis/
├── docs/
│   ├── hub/              # Documentos relacionados a entregas do HUB
│   ├── requisitos/       # Requisitos funcionais, não funcionais e regras de negócio
│   └── testes/           # Plano de teste e roteiros de teste
│       └── evidencias/   # Prints e saídas dos testes
├── slides/               # Apresentações de acompanhamento de Sprint e Pitch
├── src/
│   ├── main/java/br/com/splitrecebiveis/
│   │   ├── domain/       # Entidades, enums e regras essenciais do domínio
│   │   ├── service/      # Serviços e regras de negócio da aplicação
│   │   ├── infra/        # Persistência em memória e acesso aos dados
│   │   └── ui/           # Interface de interação via linha de comando (CLI)
│   └── test/
│       ├── java/         # Testes unitários (JUnit) e passos do BDD (Cucumber)
│       └── resources/features/   # Cenários BDD em Gherkin
└── pom.xml               # Dependências e plugins Maven
```
 
## Documentação
 
**Requisitos**
- [Requisitos funcionais](docs/requisitos/requisitos-funcionais.md)
- [Requisitos não funcionais](docs/requisitos/requisitos-nao-funcionais.md)
- [Regras de negócio](docs/requisitos/regras-de-negocio.md)
 
**Testes**
- [Plano de teste](docs/testes/planos-de-teste.md)
- [Roteiros de teste](docs/testes/roteiros-de-teste.md)
- [Evidências dos testes](docs/testes/evidencias/)

**HUB**
- [Ânima HUB](docs/hub/)
 
 
## Integrantes
 
- João Pedro Alencar Pereira Costa - RA 42320831
- Juan Pablo de Freitas Lemos Galvão - RA 42320070
- Milena Júlia Mafra - RA 42320102
- Rafael Augusto Proença - RA 42321742
 
 
## Papéis
 
| Integrante | Papel na Sprint 0 | Papel na Sprint 1 | Papel na Sprint 2 | Papel na Sprint 3 |
| --- | --- | --- | --- | --- |
| João | Consulta de beneficiários (#33) | Aprovação de beneficiários e testes unitários (#5, #31, #69 e outras) | Criação de Split, validações de negócio e consulta de histórico (#41, #42, #43 e outras) | |
| Juan | Setup do projeto (repositório, Maven e pacotes), escopo e README inicial (#13, #14, #49 e outras) | Recebíveis (cadastro, consulta e validação), testes de recebível e de regras de beneficiário, e evidências (#37, #39, #6 e outras) | Edição, exclusão, execução e controle de prioridade dos Splits (#45, #46, #47 e outras) | |
| Milena | Documentação de requisitos e regras de negócio (#21, #23, #50) | Documentação do projeto e organização da Sprint (#20, #25 e outras) | Automação de testes BDD e atualização da documentação (#9, #79 e outras) | |
| Rafael | Arquitetura inicial e cadastro de beneficiários (#24, #28) | Documentação e planejamento dos testes (#10, #16 e outras) | Consulta de Splits, relatórios e testes unitários (#78, #4, #7 e outras) | |
 
A lista completa de entregas de cada integrante está no [Projects do projeto](https://github.com/users/juanpabloflg/projects/1/views/1), filtradas por responsável e por Sprint.