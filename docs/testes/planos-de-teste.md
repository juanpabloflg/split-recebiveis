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
| ✅ Objetivo geral | Garantir que o cadastro, a consulta e a aprovação de beneficiários, o cadastro e a consulta de recebíveis e a criação, a listagem, a edição e a exclusão de Splits funcionem conforme os requisitos e as regras de negócio (RN01 a RN03, RN05, RN06, RN08 e RN09), com evidências de testes automatizados (JUnit e Cucumber) e de roteiros manuais. |
| 📊 Metas de cobertura (se aplicável) | Pelo menos 80% nas classes de serviço e regras (RNF04). Mínimo de 5 cenários BDD (RNF05), sendo 4 na Sprint 2 e os demais na Sprint 3. Mínimo de 5 roteiros manuais (13 até a Sprint 2). Usabilidade com 3 participantes na Sprint 3. |
 
## Escopo
 
| Categoria | ✅ Em escopo | 🚫 Fora de escopo |
| --- | --- | --- |
| 🧩 Funcionalidades | RF01 a RF09 (beneficiários, recebíveis e Splits: criar, listar, editar e excluir), nas Sprints 1 e 2. RF10 a RF14 (execução, recusa, prioridade, histórico e relatório) na Sprint 3 | Autenticação e perfis de usuário |
| 🧠 Regras de negócio | RN01 a RN03, RN05, RN06, RN08 e RN09 (Sprints 1 e 2). RN04 e RN07 na Sprint 3 | Segunda etapa de aprovação do beneficiário pela operadora; dias úteis na antecedência (a RN02 usa 1 dia corrido) |
| 🔌 Integrações | Nenhuma | Integração real com operadora ou maquininha |
| 🗃️ Dados | Persistência em memória | Banco de dados |
| 🧑‍💻 Não-funcionais (usabilidade etc.) | Clareza das mensagens e orientação na CLI (RNF10); cenários BDD (RNF05) | Desempenho e segurança |
 
## Ambiente e ferramentas
 
| Item | Especificação |
| --- | --- |
| 🖥️ SO | Windows (João e Juan) |
| ☕ Linguagem/Runtime | Java 17 (versão definida no `pom.xml`). Executado com JDK 21 (Juan) e JDK 26 (João) |
| 🧑‍💻 IDE | Visual Studio Code |
| 🧱 Build | Apache Maven |
| ✅ Framework de testes unitários | JUnit 5 (5.10.2), com cobertura medida pelo JaCoCo (0.8.11) |
| 🥒 BDD (se houver) | Cucumber 7.15.0, a partir da Sprint 2 |
| 🤖 CI (se houver) | Não utilizado |
| 🗄️ Banco/Dados (se houver) | Nenhum (repositórios em memória) |
 
## Estratégia de testes (por tipo)
 
| Tipo de teste | 🎯 Objetivo | 📌 Escopo | 🛠️ Ferramenta | 👤 Responsável | 📎 Saída/Evidência |
| --- | --- | --- | --- | --- | --- |
| ✅ Unitário | Validar regras de domínio e serviços isoladamente | Pacotes `domain` e `service` (Beneficiario, Recebivel, Split e serviços) | JUnit 5 e JaCoCo | Sprint 1: João (beneficiário, #5) e Juan (recebível, #6) | [mvn-test-passo1.txt](evidencias/mvn-test-passo1.txt), [jacoco-teste-passo1.png](evidencias/jacoco-teste-passo1.png), [mvn-test-passo2.txt](evidencias/mvn-test-passo2.txt), [jacoco-test-passo2.png](evidencias/jacoco-test-passo2.png) <br>Sprint 2: [mvn-test-pr1.txt](evidencias/mvn-test-pr1.txt)<br>[jacoco-test-pr1.png](evidencias/jacoco-test-pr1.png) <br>[mvn-test-pr1.1.png](evidencias/mvn-test-pr1.1.png) |
| 🌐 Sistema/End-to-End | Validar os fluxos pela interface de console | Menu da CLI | Execução manual dos roteiros RT-01 a RT-13 | João (RT-01 a RT-04, RT-07 a RT-10) e Juan (RT-05 a RT-06) | [Roteiros de teste](roteiros-de-teste.md): cada ficha traz os prints em `evidencias/` |
| 🥒 BDD | Validar fluxos essenciais em linguagem de negócio | Sprint 2: criar Split, beneficiário não aprovado, data inválida e valor acima do recebível. Sprint 3: saldo insuficiente e executar Split | Cucumber 7.15.0 (Gherkin em português) | Juan | [mvn-test-pr4-bdd.png](evidencias/mvn-test-pr4-bdd.png) e [cucumber-report-pr4.png](evidencias/cucumber-report-pr4.png) (até o PR 4 ser feito, deixe "A fazer") |
| 🧑‍💻 Usabilidade | Avaliar clareza e facilidade de uso da CLI | Fluxos principais, com 3 participantes | Roteiro de tarefas | A definir na Sprint 3 | `usabilidade.md` (Sprint 3) |
 
## Rastreabilidade (Requisitos × Testes)
 
| ID Req | Requisito/Funcionalidade | ⭐ Prioridade | 🔗 Fonte (Issue/PR) | 🧪 IDs de testes (UT/BDD/RT) | 📌 Status |
| --- | --- | --- | --- | --- | --- |
| RF01 | Cadastrar beneficiários | 🔥 Alta | #28 (Sprint 0), #5 (PR #67) | UT-01 a UT-06, UT-10 a UT-12, RT-01, RT-03, RT-04 | 🟡 Planejado |
| RF02 | Consultar beneficiários | ⚠️ Média | #33 (Sprint 0), #5 (PR #67) | UT-13, UT-14, RT-01, RT-02 | 🟡 Planejado |
| RF03 | Aprovar beneficiários | 🔥 Alta | #31 e #5 (PR #67) | UT-07, UT-08, UT-15 a UT-19, RT-02 | 🟡 Planejado |
| RF04 | Cadastrar recebíveis | 🔥 Alta | #37 e #6 (PR #68) | UT-20 a UT-27, UT-29 a UT-31, RT-05, RT-06 | 🟡 Planejado |
| RF05 | Consultar recebíveis | ⚠️ Média | #39 e #6 (PR #68) | UT-28, UT-32, UT-33, RT-05 | 🟡 Planejado |
| RF06 | Criar Split | 🔥 Alta | #41, #42, #43, #44, #7 (PR #__) | UT-34 a UT-54, RT-07 a RT-10 | 🟡 Planejado |
| RF07 a RF14 | Listar, editar, excluir, executar, recusar, prioridade, histórico e relatório | 🔥 Alta | Próximos PRs | A preencher | 🟡 Planejado |
 
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
| UT-33 | ✅ Unitário | Listagem devolve cópia | 1 recebível | Limpar a lista retornada | Repositório mantém 1 item | Média | 
Sim |
| UT-34 | ✅ Unitário | Criar Split válido | — | Dados válidos | Criado com status PENDENTE | Alta | Sim |
| UT-35 | ✅ Unitário | Rejeitar id ≤ 0 | — | id 0 e -1 | IllegalArgumentException | Média | Sim |
| UT-36 | ✅ Unitário | Rejeitar beneficiário nulo | — | null | IllegalArgumentException | Média | Sim |
| UT-37 | ✅ Unitário | Rejeitar valor nulo, zero ou negativo | — | null, 0 e -5.00 | IllegalArgumentException | Alta | Sim |
| UT-38 | ✅ Unitário | Rejeitar mais de 2 casas decimais | — | 10.999 | IllegalArgumentException | Média | Sim |
| UT-39 | ✅ Unitário | Rejeitar data nula | — | null | IllegalArgumentException | Alta | Sim |
| UT-40 | ✅ Unitário | Somar recebíveis da data | Recebíveis em 2 datas | totalPorData(data) | Soma só a data informada | Alta | Sim |
| UT-41 | ✅ Unitário | Total zero sem recebíveis | 1 recebível em outra data | totalPorData(data) | Zero | Média | Sim |
| UT-42 | ✅ Unitário | Listar apenas aprovados | 2 beneficiários, 1 aprovado | listarAprovados() | Somente o aprovado | Média | Sim |
| UT-43 | ✅ Unitário | Criar Split válido pelo serviço | Aprovado e recebível na data | R$ 800 | PENDENTE, ID 1 | Alta | Sim |
| UT-44 | ✅ Unitário | RN01 beneficiário pendente | Beneficiário pendente | Criar Split | Rejeitado | Alta | Sim |
| UT-45 | ✅ Unitário | Beneficiário inexistente | — | ID 99 | "Beneficiário não encontrado." | Média | Sim |
| UT-46 | ✅ Unitário | RN02 data de hoje | Aprovado | Data = hoje | Rejeitado | Alta | Sim |
| UT-47 | ✅ Unitário | RN02 data no passado | Aprovado | Data = ontem | Rejeitado | Média | Sim |
| UT-48 | ✅ Unitário | RN02 data de amanhã (borda) | Recebível amanhã | Data = amanhã | Criado | Alta | Sim |
| UT-49 | ✅ Unitário | RN03 valor acima do recebível | Recebível R$ 1.000 | R$ 1.500 | Rejeitado | Alta | Sim |
| UT-50 | ✅ Unitário | RN03 valor igual (borda) | Recebível R$ 1.000 | R$ 1.000 | Criado | Média | Sim |
| UT-51 | ✅ Unitário | Data sem recebíveis | Recebível em outra data | Qualquer valor | "Não há recebíveis…" | Alta | Sim |
| UT-52 | ✅ Unitário | Soma de recebíveis da data | 2 recebíveis (600 + 400) | R$ 1.000 | Criado | Média | Sim |
| UT-53 | ✅ Unitário | Criação inválida não consome ID | Aprovado | Inválido, depois válido | Próximo ID = 1 | Média | Sim |
| UT-54 | ✅ Unitário | Buscar Split por ID | 1 Split | ID existente e 99 | Retorna / lança exceção | Média | Sim |
| RT-01 | 📝 Manual | Cadastrar e consultar beneficiário | Sistema recém-aberto | Maria Silva, PF | Cadastrado com status PENDENTE | Alta | Não |
| RT-02 | 📝 Manual | Aprovar beneficiário | RT-01 executado | ID 1 | Status APROVADO | Alta | Não |
| RT-03 | 📝 Manual | Nome vazio | Sistema recém-aberto | Nome vazio | Mensagem de nome obrigatório | Média | Não |
| RT-04 | 📝 Manual | Tipo de pessoa inválido | Sistema recém-aberto | Tipo "XX" | Mensagem de tipo inválido | Média | Não |
| RT-05 | 📝 Manual | Cadastrar e consultar recebível | Sistema recém-aberto | R$ 1.500,50, 20/10/2026 | Cadastrado e listado | Alta | Não |
| RT-06 | 📝 Manual | Valores limite do recebível | Sistema recém-aberto | 0,01, 0 e 10,999 | Aceita o mínimo e rejeita os demais | Média | Não |
| RT-07 | 📝 Manual | Criar Split válido | Aprovado e recebível | R$ 800 em 30/12/2026 | Split PENDENTE | Alta | Não |
| RT-08 | 📝 Manual | Beneficiário pendente (RN01) | Maria aprovada e João pendente | ID 2 | Mensagem de RN01 | Alta | Não |
| RT-09 | 📝 Manual | Data de hoje (RN02) | Recebível hoje | Data de hoje | Mensagem de RN02 | Alta | Não |
| RT-10 | 📝 Manual | Valor e data sem recebível (RN03) | Recebível R$ 1.000 | R$ 1.500; data sem recebível | Mensagens de RN03 | Média | Não |
 
Detalhes dos roteiros em [roteiros-de-teste.md](roteiros-de-teste.md).
 
## Dados de teste
 
| ID | 🧺 Conjunto | 📝 Descrição | 🧪 Como criar | 📍 Onde armazenar | 💡 Observações |
| --- | --- | --- | --- | --- | --- |
| DT-01 | Beneficiário PF | Maria Silva, PF, CPF 12345678901, banco 001, agência 1234, conta 56789-0 | Opção 1 do menu ou `BeneficiarioService` nos testes | Em memória (zera a cada execução) | Dados fictícios |
| DT-02 | Beneficiário PJ | Empresa XYZ, PJ, CNPJ 12345678000199, banco 237, agência 4321, conta 98765-4 | Igual ao DT-01 | Em memória | Dados fictícios |
| DT-03 | Recebível padrão | Venda cartão 15/10, R$ 1.500,50, liquidação em 20/10/2026 | Opção 4 do menu | Em memória | Dados fictícios |
| DT-04 | Recebível de borda | Venda mínima, R$ 0,01 | Igual ao DT-03 | Em memória | Valor mínimo aceito |
| DT-05 | Split padrão | Maria Silva aprovada, recebível Venda cartão de R$ 1.000,00 em 30/12/2026 e Split de R$ 800,00 em 30/12/2026 | Opções 1, 3, 4 e 6 do menu | Em memória | Usado em RT-07, RT-11, RT-12 e RT-13 |
| DT-06 | Beneficiário pendente | João Souza, PF, cadastrado e não aprovado (ID 2) | Opção 1 do menu, sem aprovar | Em memória | Usado em RT-08 |
| DT-07 | Recebível e Split de hoje | Recebível de R$ 1.000,00 e Split de R$ 800,00, ambos com a data de hoje | Opções 4 e 6 do menu | Em memória | Usado em RT-09. Digite a data do dia da execução |
| DT-08 | Valores limite do Split | Valor R$ 1.500,00 (acima do recebível) e data 31/12/2026 (sem recebível) | Opção 6 do menu | Em memória | Usado em RT-10 |