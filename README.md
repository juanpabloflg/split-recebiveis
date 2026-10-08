# Sistema de Gestão de Recebíveis com Split de Pagamentos

## Descrição do Projeto

Trabalho prático desenvolvido para a disciplina integradora (A3) do ecossistema Ânima HUB, com foco na aplicação de princípios de Qualidade de Software, Versionamento e Testes Automatizados.

O projeto consiste em uma aplicação Java para gerenciamento de recebíveis por meio do mecanismo de Split, permitindo configurar e acompanhar o repasse de valores entre um estabelecimento e seus respectivos beneficiários.

O sistema busca representar o processo de divisão de recebíveis antes da liquidação, permitindo o cadastro de beneficiários, configuração de Splits, controle de status e aplicação de regras de negócio relacionadas aos repasses financeiros.

## Escopo e Funcionalidades

1. Cadastro e consulta de beneficiários, permitindo o registro de pessoas físicas ou jurídicas que poderão receber parte dos valores.
2. Controle do processo de aprovação dos beneficiários antes de sua utilização em um Split.
3. Cadastro e configuração de Splits, definindo beneficiário, valor e data programada para o repasse.
4. Consulta e acompanhamento do status dos Splits agendados.
5. Controle de prioridade entre Splits configurados para a mesma data, respeitando a ordem de configuração.
6. Validação da disponibilidade de recebíveis para execução do Split, com alteração do status para "Recusado" quando o valor disponível for insuficiente.
7. Edição e exclusão de Splits enquanto estiverem com status "Pendente".
8. Consulta do histórico das operações realizadas no sistema.

## Requisitos Técnicos e Tecnologias

- **Linguagem:** Java 17
- **Gerenciamento de Build e Dependências:** Apache Maven
- **Testes de Unidade:** JUnit 5
- **Desenvolvimento Guiado por Comportamento (BDD):** Cucumber
- **Métrica de Cobertura de Código:** JaCoCo
- **Controle de Versão:** Git / GitHub

## Organização do Repositório

split-recebiveis/
├── docs/                 # Documentação do projeto, incluindo requisitos e roteiros de teste
├── slides/               # Apresentações de acompanhamento de Sprint e Pitch
├── src/
│   ├── main/java/br/com/splitrecebiveis/
│   │   ├── domain/       # Entidades, enums e regras essenciais do domínio
│   │   ├── service/      # Serviços e regras de negócio da aplicação
│   │   ├── infra/        # Persistência em memória e acesso aos dados
│   │   └── ui/           # Interface de interação via linha de comando (CLI)
│   └── test/             # Suíte de testes unitários (JUnit) e especificações BDD (Cucumber)
└── pom.xml               # Especificação de dependências e plugins Maven
