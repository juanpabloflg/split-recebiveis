# Plano de Teste
 
## Convenções e status
 
| Item | Padrão |
| --- | --- |
| 🏷️ ID do teste | **UT-XX** (unitário), **BDD-XX** (cenário BDD), **RT-XX** (roteiro manual) |
| 📌 Status | 🟡 Planejado • 🔵 Em execução • 🟢 Passou • 🔴 Falhou • ⚫ Bloqueado |
| ⭐ Prioridade | 🔥 Alta • ⚠️ Média • 🟦 Baixa |
| 📎 Evidência | Print / log / vídeo / link do PR ou da Issue |
 
## Identificação e contexto
 
| Campo | Preencher |
| --- | --- |
| 🧩 Nome do projeto | Sistema de Gestão de Recebíveis com Split de Pagamentos |
| 📝 Objetivo do sistema (resumo) | Gerenciar o split de recebíveis: cadastrar e aprovar beneficiários, cadastrar recebíveis e configurar Splits que dividem o valor a receber entre o estabelecimento e seus beneficiários antes da liquidação. |
| 🎯 Público-alvo | Estabelecimentos comerciais que repassam parte do faturamento a fornecedores e prestadores de serviço. |
| 💻 Plataforma/Tipo (console/web/mobile/API) | Console (CLI) em Java |
| 🔗 Repositório | https://github.com/juanpabloflg/split-recebiveis |
| 👥 Time/Grupo | João, Juan, Milena e Rafael |
 
## Objetivo do teste
 
| Item | Descrição |
| --- | --- |
| ✅ Objetivo geral | Garantir que o cadastro, a consulta e a aprovação de beneficiários e o cadastro e a consulta de recebíveis funcionem conforme os requisitos e as regras de negócio (RN08 e RN09), com evidências de testes automatizados (JUnit) e de roteiros manuais. |
| 📊 Metas de cobertura (se aplicável) | Pelo menos 80% nas classes de serviço e regras (RNF04). Mínimo de 5 cenários BDD (RNF05), 5 roteiros manuais e usabilidade com 3 participantes nas próximas Sprints. |
 
## Escopo
 
| Categoria | ✅ Em escopo | 🚫 Fora de escopo |
| --- | --- | --- |
| 🧩 Funcionalidades | RF01 a RF05 (beneficiários e recebíveis); demais RFs nas Sprints 2 e 3 | Autenticação e perfis de usuário |
| 🧠 Regras de negócio | RN08 e RN09 na Sprint 1; RN01 a RN07 nas próximas Sprints | Segunda etapa de aprovação do beneficiário pela operadora |
| 🔌 Integrações | Nenhuma | Integração real com operadora ou maquininha |
| 🗃️ Dados | Persistência em memória | Banco de dados |
| 🧑‍💻 Não-funcionais (usabilidade etc.) | Clareza das mensagens e orientação na CLI (RNF10) | Desempenho e segurança |
 
## Ambiente e ferramentas
 
| Item | Especificação |
| --- | --- |
| 🖥️ SO | Windows (João e Juan) |
| ☕ Linguagem/Runtime | Java 17 (versão definida no `pom.xml`). Executado com JDK 21 (Juan) e Executado com JDK 26 (João) |
| 🧑‍💻 IDE | Visual Studio Code |
| 🧱 Build | Apache Maven |
| ✅ Framework de testes unitários | JUnit 5 (5.10.2), com cobertura medida pelo JaCoCo (0.8.11) |
| 🥒 BDD (se houver) | Cucumber 7.15.0, a partir da Sprint 2 |
| 🤖 CI (se houver) | Não utilizado |
| 🗄️ Banco/Dados (se houver) | Nenhum (repositórios em memória) |
 
## Estratégia de testes (por tipo)
 
| Tipo de teste | 🎯 Objetivo | 📌 Escopo | 🛠️ Ferramenta | 👤 Responsável | 📎 Saída/Evidência |
| --- | --- | --- | --- | --- | --- |
| ✅ Unitário | Validar regras de domínio e serviços isoladamente | Pacotes `domain` e `service` | JUnit 5 e JaCoCo | João (beneficiário, #5) e Juan (recebível, #6) | [mvn-test-passo1.txt](evidencias/mvn-test-passo1.txt), [jacoco-teste-passo1.png](evidencias/jacoco-teste-passo1.png), [mvn-test-passo2.txt](evidencias/mvn-test-passo2.txt), [jacoco-test-passo2.png](evidencias/jacoco-test-passo2.png) |
| 🌐 Sistema/End-to-End | Validar os fluxos pela interface de console | Menu da CLI | Execução manual dos roteiros | João (RT-01 a RT-04) e Juan (RT-05 e RT-06) | [Roteiros de teste](roteiros-de-teste.md) e prints em `evidencias/` |
| 🥒 BDD | Validar fluxos essenciais em linguagem de negócio | Criar Split, beneficiário não aprovado, data inválida, saldo insuficiente e executar Split | Cucumber | A definir na Sprint 2 | A definir |
| 🧑‍💻 Usabilidade | Avaliar clareza e facilidade de uso da CLI | Fluxos principais, com 3 participantes | Roteiro de tarefas | A definir na Sprint 3 | `usabilidade.md` (Sprint 3) |
 
## Rastreabilidade (Requisitos × Testes)
 
| ID Req | Requisito/Funcionalidade | ⭐ Prioridade | 🔗 Fonte (Issue/PR) | 🧪 IDs de testes (UT/BDD/RT) | 📌 Status |
| --- | --- | --- | --- | --- | --- |
| RF01 | Cadastrar beneficiários | 🔥 Alta | #28 (Sprint 0), #5 (PR #67) | UT-01 a UT-06, UT-10 a UT-12, RT-01, RT-03, RT-04 | 🟡 Planejado |
| RF02 | Consultar beneficiários | ⚠️ Média | #33 (Sprint 0), #5 (PR #67) | UT-13, UT-14, RT-01, RT-02 | 🟡 Planejado |
| RF03 | Aprovar beneficiários | 🔥 Alta | #31 e #5 (PR #67) | UT-07, UT-08, UT-15 a UT-19, RT-02 | 🟡 Planejado |
| RF04 | Cadastrar recebíveis | 🔥 Alta | #37 e #6 (PR #68) | UT-20 a UT-27, UT-29 a UT-31, RT-05, RT-06 | 🟡 Planejado |
| RF05 | Consultar recebíveis | ⚠️ Média | #39 e #6 (PR #68) | UT-28, UT-32, UT-33, RT-05 | 🟡 Planejado |
| RF06 a RF14 | Split: criar, listar, editar, excluir, executar, recusar, prioridade, histórico e relatório | 🔥 Alta | A preencher nas Sprints 2 e 3 | BDD-01 a BDD-05 e testes das próximas Sprints | 🟡 Planejado |
 
> 🏷️ Convenção: ✅ **UT-XX** (teste unitário), 🥒 **BDD-XX** (cenário BDD), 📝 **RT-XX** (roteiro manual).
 
## Casos de teste planejados (resumo)
 
| ID | 🧪 Tipo | 🏷️ Título | 🔐 Pré-condição | 📥 Entrada | ✅ Resultado esperado | ⭐ Prioridade | 🤖 Automatizado? |
| --- | --- | --- | --- | --- | --- | --- | --- |
| UT-01 | ✅ Unitário | Criar beneficiário válido | — | Dados válidos | Criado com status PENDENTE | Alta | Sim |
| UT-02 | ✅ Unitário | Rejeitar id ≤ 0 | — | id 0 e -1 | IllegalArgumentException | Média | Sim |
| UT-03 | ✅ Unitário | Rejeitar nome inválido | — | Nulo, vazio ou só espaços | IllegalArgumentException | Alta | Sim |
| UT-04 | ✅ Unitário | Rejeitar tipo de pessoa nulo | — | null | IllegalArgumentException | Média | Sim |
| UT-05 | ✅ Unitário | Rejeitar documento inválido | — | Nulo, vazio ou só espaços | IllegalArgumentException | Alta | Sim |
| UT-06 | ✅ Unitário | Rejeitar dados bancários ausentes | — | Banco, agência ou conta nulos ou vazios | IllegalArgumentException | Alta | Sim |
| UT-07 | ✅ Unitário | Aprovar beneficiário pendente | Beneficiário PENDENTE | `aprovar()` | Status APROVADO | Alta | Sim |
| UT-08 | ✅ Unitário | Aprovar beneficiário já aprovado | Beneficiário APROVADO | `aprovar()` | IllegalStateException | Alta | Sim |
| UT-09 | ✅ Unitário | toString exibe ID e status | Beneficiário criado | `toString()` | Contém "ID: 1" e "Status: PENDENTE" | Baixa | Sim |
| UT-10 | ✅ Unitário | IDs sequenciais de beneficiários | Serviço vazio | 2 cadastros válidos | IDs 1 e 2, ambos PENDENTE | Alta | Sim |
| UT-11 | ✅ Unitário | Cadastro armazena no repositório | Serviço vazio | 1 cadastro válido | `listarTodos` devolve o beneficiário | Alta | Sim |
| UT-12 | ✅ Unitário | Cadastro inválido não salva nem consome ID | Serviço vazio | Nome vazio, depois cadastro válido | Exceção, lista vazia e próximo ID = 1 | Média | Sim |
| UT-13 | ✅ Unitário | Listar sem beneficiários | Serviço vazio | `listarTodos()` | Lista vazia | Média | Sim |
| UT-14 | ✅ Unitário | Listagem devolve cópia | 1 beneficiário | Limpar a lista retornada | Repositório mantém 1 item | Média | Sim |
| UT-15 | ✅ Unitário | Listar apenas pendentes | 2 beneficiários, 1 aprovado | `listarPendentes()` | Somente o pendente | Alta | Sim |
| UT-16 | ✅ Unitário | Aprovar beneficiário existente | 1 beneficiário PENDENTE | `aprovar(id)` | APROVADO e sai dos pendentes | Alta | Sim |
| UT-17 | ✅ Unitário | Aprovar beneficiário inexistente | Serviço vazio | `aprovar(99)` | IllegalArgumentException "Beneficiário não encontrado." | Alta | Sim |
| UT-18 | ✅ Unitário | Aprovar duas vezes | Beneficiário APROVADO | `aprovar(id)` | IllegalStateException | Média | Sim |
| UT-19 | ✅ Unitário | Buscar por ID | 1 beneficiário | ID existente e ID 99 | Retorna o beneficiário e lança exceção | Média | Sim |
| UT-20 | ✅ Unitário | Criar recebível válido | — | Dados válidos | Valor disponível igual ao valor total | Alta | Sim |
| UT-21 | ✅ Unitário | Rejeitar id ≤ 0 | — | id 0 e -1 | IllegalArgumentException | Média | Sim |
| UT-22 | ✅ Unitário | Rejeitar descrição inválida | — | Nula, vazia ou só espaços | IllegalArgumentException | Alta | Sim |
| UT-23 | ✅ Unitário | Rejeitar valor nulo, zero ou negativo | — | null, 0 e -5.00 | IllegalArgumentException | Alta | Sim |
| UT-24 | ✅ Unitário | Aceitar valor mínimo (borda) | — | 0.01 | Recebível criado | Média | Sim |
| UT-25 | ✅ Unitário | Rejeitar mais de 2 casas decimais | — | 10.999 | IllegalArgumentException | Média | Sim |
| UT-26 | ✅ Unitário | Rejeitar data nula | — | dataLiquidacao = null | IllegalArgumentException | Alta | Sim |
| UT-27 | ✅ Unitário | Normalizar para 2 casas decimais | — | 1000 | 1000.00 | Baixa | Sim |
| UT-28 | ✅ Unitário | toString formatado | Recebível criado | `toString()` | "R$ 1.500,50" e "20/10/2026" | Baixa | Sim |
| UT-29 | ✅ Unitário | IDs sequenciais de recebíveis | Serviço vazio | 2 cadastros válidos | IDs 1 e 2 | Alta | Sim |
| UT-30 | ✅ Unitário | Cadastro armazena no repositório | Serviço vazio | 1 cadastro válido | `listarTodos` devolve o recebível | Alta | Sim |
| UT-31 | ✅ Unitário | Cadastro inválido não salva nem consome ID | Serviço vazio | Valor 0, depois cadastro válido | Exceção, lista vazia e próximo ID = 1 | Média | Sim |
| UT-32 | ✅ Unitário | Listar sem recebíveis | Serviço vazio | `listarTodos()` | Lista vazia | Média | Sim |
| UT-33 | ✅ Unitário | Listagem devolve cópia | 1 recebível | Limpar a lista retornada | Repositório mantém 1 item | Média | Sim |
| BDD-01 | 🥒 BDD | (Ainda não implementado) Criar Split válido | Beneficiário aprovado e recebível cadastrado | Valor e data com antecedência | Split criado como Pendente | Alta | Sim |
| BDD-02 | 🥒 BDD | (Ainda não implementado) Beneficiário não aprovado | Beneficiário pendente | Criar Split | Rejeitado (RN01) | Alta | Sim |
| BDD-03 | 🥒 BDD | (Ainda não implementado) Data inválida | Beneficiário aprovado | Data de hoje | Rejeitado (RN02) | Alta | Sim |
| BDD-04 | 🥒 BDD | (Ainda não implementado) Saldo insuficiente | Split pendente maior que o disponível | Executar Split | Status Recusado (RN04) | Alta | Sim |
| BDD-05 | 🥒 BDD | (Ainda não implementado) Executar Split corretamente | Split pendente com saldo | Executar Split | Status Executado | Alta | Sim |
| RT-01 | 📝 Manual | Cadastrar e consultar beneficiário | Sistema recém-aberto | Maria Silva, PF | Cadastrado com status PENDENTE | Alta | Não |
| RT-02 | 📝 Manual | Aprovar beneficiário | RT-01 executado | ID 1 | Status APROVADO | Alta | Não |
| RT-03 | 📝 Manual | Nome vazio | Sistema recém-aberto | Nome vazio | Mensagem de nome obrigatório | Média | Não |
| RT-04 | 📝 Manual | Tipo de pessoa inválido | Sistema recém-aberto | Tipo "XX" | Mensagem de tipo inválido | Média | Não |
| RT-05 | 📝 Manual | Cadastrar e consultar recebível | Sistema recém-aberto | R$ 1.500,50, 20/10/2026 | Cadastrado e listado | Alta | Não |
| RT-06 | 📝 Manual | Valores limite do recebível | Sistema recém-aberto | 0,01, 0 e 10,999 | Aceita o mínimo e rejeita os demais | Média | Não |
 
Detalhes dos roteiros em [roteiros-de-teste.md](roteiros-de-teste.md).
 
## Dados de teste
 
| ID | 🧺 Conjunto | 📝 Descrição | 🧪 Como criar | 📍 Onde armazenar | 💡 Observações |
| --- | --- | --- | --- | --- | --- |
| DT-01 | Beneficiário PF | Maria Silva, PF, CPF 12345678901, banco 001, agência 1234, conta 56789-0 | Opção 1 do menu ou `BeneficiarioService` nos testes | Em memória (zera a cada execução) | Dados fictícios |
| DT-02 | Beneficiário PJ | Empresa XYZ, PJ, CNPJ 12345678000199, banco 237, agência 4321, conta 98765-4 | Igual ao DT-01 | Em memória | Dados fictícios |
| DT-03 | Recebível padrão | Venda cartão 15/10, R$ 1.500,50, liquidação em 20/10/2026 | Opção 4 do menu | Em memória | Dados fictícios |
| DT-04 | Recebível de borda | Venda mínima, R$ 0,01 | Igual ao DT-03 | Em memória | Valor mínimo aceito |