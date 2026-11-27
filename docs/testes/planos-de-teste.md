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
| ✅ Objetivo geral | Garantir que todas as 12 funcionalidades do menu funcionem conforme os requisitos e as regras de negócio (RN01 a RN09), com evidências de testes automatizados (JUnit e Cucumber) e de roteiros manuais. |
| 📊 Metas de cobertura (se aplicável) | Pelo menos 80% nas classes de serviço e regras (RNF04). Mínimo de 5 cenários BDD (RNF05), sendo 4 na Sprint 2 e os demais na Sprint 3. Mínimo de 5 roteiros manuais (17 no total). Usabilidade com 3 participantes na Sprint 3. |
 
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
| 🖥️ SO | Windows (João, Juan, Milena e Rafael) |
| ☕ Linguagem/Runtime | Java 25 LTS (versão definida no `pom.xml`). Os resultados anteriores foram registrados com JDK 25 (Juan), JDK 26 (João), JDK 25 (Milena) e JDK 25 (Rafael) |
| 🧑‍💻 IDE | Visual Studio Code |
| 🧱 Build | Apache Maven |
| ✅ Framework de testes unitários | JUnit 5 (5.10.2), com cobertura medida pelo JaCoCo (0.8.14) |
| 🥒 BDD (se houver) | Cucumber 7.15.0, a partir da Sprint 2 |
| 🤖 CI (se houver) | Não utilizado |
| 🗄️ Banco/Dados (se houver) | Nenhum (repositórios em memória) |
 
## Estratégia de testes (por tipo)
 
| Tipo de teste | 🎯 Objetivo | 📌 Escopo | 🛠️ Ferramenta | 👤 Responsável | 📎 Saída/Evidência |
| --- | --- | --- | --- | --- | --- |
| ✅ Unitário | Validar regras de domínio e serviços isoladamente | Pacotes `domain` e `service` (Beneficiario, Recebivel, Split e serviços) | JUnit 5 e JaCoCo | Sprint 1: João (beneficiário, #5) e Juan (recebível, #6) | [mvn-test-passo1.txt](evidencias/mvn-test-passo1.txt), [jacoco-teste-passo1.png](evidencias/jacoco-teste-passo1.png), [mvn-test-passo2.txt](evidencias/mvn-test-passo2.txt), [jacoco-test-passo2.png](evidencias/jacoco-test-passo2.png) <br>Sprint 2: [mvn-test-pr1.txt](evidencias/mvn-test-pr1.txt)<br>[jacoco-test-pr1.png](evidencias/jacoco-test-pr1.png)<br>[mvn-test-pr1.1.png](evidencias/mvn-test-pr1.1.png)<br>[mvn-test-pr2.png](evidencias/mvn-test-pr2.png)<br>[mvn-test-pr2.txt](evidencias/mvn-test-pr2.txt)<br>[mvn-test-pr3.txt](evidencias/mvn-test-pr3.txt)<br>[mvn-test-pr3.png](evidencias/mvn-test-pr3.png)<br>[jacoco-pr3.png](evidencias/jacoco-pr3.png) |
| 🌐 Sistema/End-to-End | Validar os fluxos pela interface de console | Menu da CLI | Execução manual dos roteiros RT-01 a RT-17 | João (RT-01 a RT-04, RT-07 a RT-10), Juan (RT-05 a RT-06, RT-12 a RT-17) e Rafael (RT-11) | [Roteiros de teste](roteiros-de-teste.md): cada ficha traz os prints em `evidencias/` |
| 🥒 BDD | Validar fluxos essenciais em linguagem de negócio | Sprint 2: criar Split, beneficiário não aprovado, data inválida e valor acima do recebível. Sprint 3: saldo insuficiente e executar Split | Cucumber 7.15.0 (Gherkin em português) | Milena(BDD-01, BDD-02, BDD-03, e BDD-06)<br>Juan(BDD-04 e BDD-05) | Sprint 2: [jacoco-pr4.png](evidencias/jacoco-pr4.png) <br> [mvn-test-pr4-bdd.txt](evidencias/mvn-test-pr4-bdd.txt)<br>[bdd-cucumber-pr4.1.png](evidencias/bdd-cucumber-pr4.1.png)<br>[bdd-cucumber-pr4.2.png](evidencias/bdd-cucumber-pr4.2.png) <br> [bdd-cucumber-pr4.3.png](evidencias/bdd-cucumber-pr4.3.png)<br>[mvn-test-pr4-terminalbdd.png](evidencias/mvn-test-pr4-terminalbdd.png)<br>[mvn-test-pr4.1-terminalbdd.png](evidencias/mvn-test-pr4.1-terminalbdd.png)<br>Sprint 3: [mvn-test-sprint3.txt](evidencias/mvn-test-sprint3.txt)<br>[jacoco-sprint3.png](evidencias/jacoco-sprint3.png)<br>[bdd-sprint3.png](evidencias/bdd-sprint3.png)<br>[bdd-terminal-sprint3.png](evidencias/bdd-terminal-sprint3.png)|
| 🧑‍💻 Usabilidade | Avaliar clareza e facilidade de uso da CLI | Fluxos principais, com 3 participantes | Roteiro de tarefas | Juan | [Usabilidade](teste-usabilidade.md) |
 
## Rastreabilidade (Requisitos × Testes)
 
| ID Req | Requisito/Funcionalidade | ⭐ Prioridade | 🔗 Fonte (Issue/PR) | 🧪 IDs de testes (UT/BDD/RT) | 📌 Status |
| --- | --- | --- | --- | --- | --- |
| RF01 | Cadastrar beneficiários | 🔥 Alta | #28 (Sprint 0), #5 (PR `#67`) | UT-01 a UT-06, UT-10 a UT-12, RT-01, RT-03, RT-04 | 🟢 Passou |
| RF02 | Consultar beneficiários | ⚠️ Média | #33 (Sprint 0), #5 (PR `#67`) | UT-13, UT-14, RT-01, RT-02 | 🟢 Passou |
| RF03 | Aprovar beneficiários | 🔥 Alta | #31 e #5 (PR `#67`) | UT-07, UT-08, UT-15 a UT-19, RT-02 | 🟢 Passou |
| RF04 | Cadastrar recebíveis | 🔥 Alta | #37 e #6 (PR `#68`) | UT-20 a UT-27, UT-29 a UT-31, RT-05, RT-06 | 🟢 Passou |
| RF05 | Consultar recebíveis | ⚠️ Média | #39 e #6 (PR `#68`) | UT-28, UT-32, UT-33, RT-05 | 🟢 Passou |
| RF06 | Criar Split | 🔥 Alta | #41, #42, #43, #44, #7 (PR `#86`) | UT-34 a UT-54, RT-07 a RT-10 | 🟢 Passou |
| RF07 | Listar Splits | ⚠️ Média | #78 (PR `#87`) | UT-55 a UT-57, RT-11 | 🟢 Passou |
| RF08 | Editar Split pendente | 🔥 Alta | #45, #7 (PR `#88`) | UT-58 a UT-60, UT-63 a UT-67, RT-12 | 🟢 Passou |
| RF09 | Excluir Split pendente | 🔥 Alta | #46, #7 (PR `#88`) | UT-68 a UT-70, RT-13 | 🟢 Passou |
| RF10 a RF14 | Executar, recusar, prioridade, histórico e relatório | 🔥 Alta | #8, #11 e #36 (PR `#102`) | BDD-04, BDD-05, UT-71 a UT-96 | 🟢 Passou |
 
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
| UT-55 | ✅ Unitário | Listar sem Splits | Serviço vazio | listarTodos() | Lista vazia | Média | Sim |
| UT-56 | ✅ Unitário | Listar na ordem de criação | 2 Splits | listarTodos() | Ordem de criação | Alta | Sim |
| UT-57 | ✅ Unitário | Listagem devolve cópia | 1 Split | Limpar a lista | Repositório mantém 1 item | Média | Sim |
| UT-58 | ✅ Unitário | Editar Split pendente (domínio) | Split pendente | editar(valor, data) | Valor e data alterados | Alta | Sim |
| UT-59 | ✅ Unitário | RN05 editar Split executado | Split executado | editar() | IllegalStateException | Alta | Sim |
| UT-60 | ✅ Unitário | RN05 editar Split recusado | Split recusado | editar() | IllegalStateException | Média | Sim |
| UT-61 | ✅ Unitário | Executar Split pendente | Split pendente | marcarComoExecutado() | EXECUTADO; segunda vez falha | Média | Sim |
| UT-62 | ✅ Unitário | Recusar Split pendente | Split pendente | recusar() | RECUSADO; segunda vez falha | Média | Sim |
| UT-63 | ✅ Unitário | Editar Split pelo serviço | Split pendente | R$ 500 e outra data | Split alterado | Alta | Sim |
| UT-64 | ✅ Unitário | RN03 na edição | Split pendente | R$ 1.500 | Rejeitado e Split mantido | Alta | Sim |
| UT-65 | ✅ Unitário | RN02 na edição | Split pendente | Data de hoje | Rejeitado e Split mantido | Alta | Sim |
| UT-66 | ✅ Unitário | Editar Split inexistente | — | ID 99 | IllegalArgumentException | Média | Sim |
| UT-67 | ✅ Unitário | RN05 no serviço | Split executado | editar() | "Apenas Splits pendentes podem ser editados." | Alta | Sim |
| UT-68 | ✅ Unitário | Excluir Split pendente | Split pendente | excluir(id) | Split removido | Alta | Sim |
| UT-69 | ✅ Unitário | Excluir Split inexistente | — | ID 99 | "Split não encontrado." | Média | Sim |
| UT-70 | ✅ Unitário | RN06 excluir executado ou recusado | Splits executado e recusado | excluir() | IllegalStateException; lista mantida | Alta | Sim |
| UT-71 | ✅ Unitário | Split.recusar sem motivo mantém motivo nulo | Split pendente | `recusar()` | Status RECUSADO; motivoRecusa null | Alta | Sim |
| UT-72 | ✅ Unitário | Split.recusar com motivo armazena a mensagem (RN04) | Split pendente | `recusar("mensagem")` | Status RECUSADO; motivo igual ao passado | Alta | Sim |
| UT-73 | ✅ Unitário | Recusar Split não-pendente lança exceção | Split executado e recusado | `recusar()` | IllegalStateException | Alta | Sim |
| UT-74 | ✅ Unitário | toString inclui motivo da recusa quando presente | Split recusado com motivo | `toString()` | Contém "Motivo da recusa:" | Baixa | Sim |
| UT-75 | ✅ Unitário | Recebivel.debitar valor válido reduz saldo | Recebível R$ 1.000 | 400,00 | Disponível R$ 600,00 | Alta | Sim |
| UT-76 | ✅ Unitário | Recebivel.debitar valor inválido lança exceção | Recebível criado | null, 0, negativo e acima do disponível | IllegalArgumentException | Alta | Sim |
| UT-77 | ✅ Unitário | RecebivelService — saldo por data soma apenas a data informada | 2 recebíveis em datas diferentes | saldoDisponivelPorData | Soma só da data; zero para data sem recebível | Alta | Sim |
| UT-78 | ✅ Unitário | RecebivelService — debitarPorData distribui entre recebíveis | 2 recebíveis (300+700) na mesma data | debitarPorData(800) | Saldo R$ 200,00 | Alta | Sim |
| UT-79 | ✅ Unitário | RecebivelService — saldo insuficiente lança exceção sem debitar | Recebível R$ 200 | debitarPorData(500) | IllegalArgumentException; saldo intacto | Alta | Sim |
| UT-80 | ✅ Unitário | filaDaData retorna Splits pendentes da data em ordem de ID (RN07) | 3 Splits na mesma data | filaDaData(data) | IDs 1, 2, 3 nessa ordem | Alta | Sim |
| UT-81 | ✅ Unitário | filaDaData ignora outras datas e não-pendentes | Splits em datas diferentes e um executado | filaDaData(data) | Só os pendentes da data | Alta | Sim |
| UT-82 | ✅ Unitário | executarDaData com saldo suficiente marca EXECUTADO e debita (RN04) | Split R$ 800; saldo R$ 1.000 | executarDaData | EXECUTADO; saldo R$ 200 | Alta | Sim |
| UT-83 | ✅ Unitário | executarDaData sem saldo marca RECUSADO com motivo sem debitar (RN04) | Saldo insuficiente | executarDaData | RECUSADO; motivo contém "Saldo insuficiente"; saldo intacto | Alta | Sim |
| UT-84 | ✅ Unitário | executarDaData respeita prioridade com saldo parcial (RN07 + RN04) | Splits 800, 500, 100; saldo 1.000 | executarDaData | EXECUTADO, RECUSADO, EXECUTADO | Alta | Sim |
| UT-85 | ✅ Unitário | executarDaData retorna lista vazia sem Splits pendentes | Serviço vazio | executarDaData | Lista vazia | Média | Sim |
| UT-86 | ✅ Unitário | HistoricoService — listarTodos vazio sem registros | Serviço vazio | listarTodos() | Lista vazia | Média | Sim |
| UT-87 | ✅ Unitário | HistoricoService — registrar cria registro com operação e splitId corretos | — | registrar(CRIADO, split, null) | operacao=CRIADO; splitId=1 | Alta | Sim |
| UT-88 | ✅ Unitário | HistoricoService — detalhe aparece na descrição; ausência não deixa separador solto | — | Com e sem detalhe | Contém o detalhe; não termina com " \| " | Alta | Sim |
| UT-89 | ✅ Unitário | HistoricoService — múltiplos registros na ordem de inserção | 3 registros | listarTodos() | CRIADO, EDITADO, EXCLUIDO | Alta | Sim |
| UT-90 | ✅ Unitário | RegistroHistorico.toString contém data, operação e Split | 1 registro | toString() | Contém "CRIADO", "Split 1", nome | Baixa | Sim |
| UT-91 | ✅ Unitário | RelatorioService — sistema vazio devolve tudo zerado | Serviço vazio | gerarResumo() | Todos os totais zero | Média | Sim |
| UT-92 | ✅ Unitário | RelatorioService — conta beneficiários aprovados e pendentes | 2 beneficiários (1 aprovado) | gerarResumo() | 1 aprovado, 1 pendente | Alta | Sim |
| UT-93 | ✅ Unitário | RelatorioService — totaliza valor e saldo dos recebíveis | 2 recebíveis (600+400) | gerarResumo() | Valor total e saldo R$ 1.000,00 | Alta | Sim |
| UT-94 | ✅ Unitário | RelatorioService — classifica Splits por status com soma de valores | Splits executado, recusado e pendente | gerarResumo() | Contagem e soma corretas por status | Alta | Sim |
| UT-95 | ✅ Unitário | RelatorioService — conta operações do histórico | 1 Split criado | gerarResumo() | operacoesRegistradas = 1 | Média | Sim |
| UT-96 | ✅ Unitário | RelatorioResumo.toString contém todas as seções | Resumo gerado | toString() | Contém Beneficiários, Recebíveis, Splits, Histórico | Baixa | Sim |
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
| RT-11 | 📝 Manual | Listar Splits | Sistema recém-aberto | Opção 7 | Lista vazia e depois o Split criado | Média | Não |
| RT-12 | 📝 Manual | Editar Split | Split pendente | R$ 500; depois R$ 1.500 | Editado; depois rejeitado (RN03) | Alta | Não |
| RT-13 | 📝 Manual | Excluir Split | Split pendente | Confirma N e S | Cancela; depois exclui | Alta | Não |
| RT-14 | 📝 Manual | Executar Splits com saldo suficiente | Aprovado + recebível + 2 Splits | Opção 10, confirmar S | Ambos EXECUTADO; saldo debitado | Alta | Não |
| RT-15 | 📝 Manual | Recusar Split por saldo insuficiente (RN04) | RT-14 + 3º Split maior que saldo restante | Opção 10, confirmar S | 2 executados, 1 recusado com motivo | Alta | Não |
| RT-16 | 📝 Manual | Consultar histórico de Splits | Após RT-15 | Opção 11 | 6 registros em ordem cronológica | Alta | Não |
| RT-17 | 📝 Manual | Gerar relatório resumido | Após RT-15 | Opção 12 | Totais e valores corretos por status | Alta | Não |
| BDD-01 | 🥒 BDD | Criar Split válido | Beneficiário aprovado e recebível na data | Valor e data com antecedência | Split criado como PENDENTE | Alta | Sim |
| BDD-02 | 🥒 BDD | Beneficiário não aprovado | Beneficiário pendente | Criar Split | Rejeitado (RN01) | Alta | Sim |
| BDD-03 | 🥒 BDD | Data inválida | Beneficiário aprovado | Data de hoje | Rejeitado (RN02) | Alta | Sim |
| BDD-04 | 🥒 BDD | Saldo insuficiente | Split pendente maior que o disponível | Executar Split | Status RECUSADO (RN04) | Alta | Sim |
| BDD-05 | 🥒 BDD | Executar Split corretamente | Split pendente com saldo | Executar Split | Status EXECUTADO | Alta | Sim |
| BDD-06 | 🥒 BDD | Valor acima do recebível | Recebível de R$ 1.000 | R$ 1.500 | Rejeitado (RN03) | Média | Sim |
 
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
| DT-09 | Execução com saldo suficiente | Maria Silva aprovada, recebível de R$ 1.000,00 em 30/12/2027, Splits de R$ 400,00 e R$ 300,00 em 30/12/2027 | Opções 1, 3, 4 e 6 do menu | Em memória | Usado em RT-14 e RT-16. Data em 2027 para não conflitar com datas já passadas |
| DT-10 | Execução com saldo insuficiente | DT-09 acrescido de um terceiro Split de R$ 800,00 em 30/12/2027 | Opção 6 do menu após DT-09 | Em memória | Usado em RT-15, RT-16 e RT-17. O terceiro Split é recusado pois o saldo restante é R$ 300,00 |