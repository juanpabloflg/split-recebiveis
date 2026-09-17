<<<<<<< HEAD
# Sistema de Gestão Financeira Pessoal com Motor de Regras Orçamentárias

## Descrição do Projeto
Trabalho prático desenvolvido para a disciplina integradora (A3) do ecossistema Ânima HUB, com foco na aplicação de princípios de Qualidade de Software, Versionamento e Testes Automatizados.

O projeto consiste em uma aplicação Java para gerenciamento de finanças pessoais, cujo diferencial é um módulo de análise de hábitos de consumo baseado em regras e limites orçamentários pré-configurados. O sistema monitora lançamentos financeiros e gera alertas preditivos quando despesas em categorias específicas se aproximam do teto estabelecido.

## Escopo e Funcionalidades
1. Registro, edição e remoção de lançamentos de receitas e despesas.
2. Agrupamento e classificação de transações por categoria de gasto.
3. Parametrização de limites orçamentários mensais por categoria.
4. Consulta de saldo consolidado e histórico de movimentações.
5. Módulo de Recomendação Orçamentária: avaliação automatizada do fluxo financeiro com emissão de alertas de estouro de limite (Regra de Negócio Não-Trivial).
6. Exportação de demonstrativos financeiros simplificados.

## Requisitos Técnicos e Tecnologias
* **Linguagem:** Java 17
* **Gerenciamento de Build e Dependências:** Apache Maven
* **Testes de Unidade:** JUnit 5
* **Desenvolvimento Guiado por Comportamento (BDD):** Cucumber
* **Métrica de Cobertura de Código:** JaCoCo
* **Controle de Versão:** Git / GitHub

## Organização do Repositório
```text
gestao-financeira/
├── docs/                 # Documentação do projeto, incluindo requisitos e roteiros de teste
├── slides/               # Apresentações de acompanhamento de Sprint e Pitch
├── src/
│   ├── main/java/br/com/financas/
│   │   ├── domain/       # Modelos de domínio e regras de negócio essenciais
│   │   ├── service/      # Serviços e casos de uso da aplicação
│   │   ├── infra/        # Estruturas de persistência e acesso a dados
│   │   └── ui/           # Interface de interação via linha de comando (CLI)
│   └── test/             # Suíte de testes unitários (JUnit) e especificações BDD (Cucumber)
└── pom.xml               # Especificação de dependências e plugins Maven
=======
# sistema-gestao-financeira
Sistema de gestão financeira pessoal desenvolvido para a disciplina de Gestão e Qualidade de Software.
>>>>>>> ddec4564ebba0207f9cae4d523fb0ccbe3f403fd
