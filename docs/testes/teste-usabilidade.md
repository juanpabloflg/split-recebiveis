# 🧑‍💻✅ Teste de Usabilidade - Sistema de Gestão de Recebíveis com Split de Pagamentos

### 🎯 Objetivo do teste

| Item        | Preencher ✍️                                                                                                                                                          |
| ----------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 🎯 Objetivo | Avaliar se os usuários conseguem realizar as principais operações do Sistema de Gestão de Recebíveis com Split de Pagamentos (cadastro e aprovação de beneficiários, cadastro de recebíveis e gerenciamento de Splits) de forma clara, intuitiva e sem ajuda excessiva.                                          |
| ✅ Hipóteses | <p>(H1) Os usuários conseguem concluir as principais tarefas do sistema em aproximadamente 2 minutos cada.<br>(H2) Os usuários compreendem as mensagens de validação, confirmação e erro apresentadas pelo sistema durante as operações.<br>(H3) O menu principal é intuitivo e permite que os usuários encontrem e executem as funcionalidades sem dificuldades significativas.</p> |
| 📌 Escopo   | MenuConsole: cadastrar beneficiário, aprovar beneficiário, cadastrar recebível, criar Split e executar Splits agendados, consultando posteriormente o resultado no histórico de operações.                                                    |

***

### 👥 Participantes (perfil)

| ID | 👤 Perfil            | 🎓 Nível de experiência | 📍 Contexto (onde/como) | Observações 📝                          |
| -- | -------------------- | ----------------------- | ----------------------- | --------------------------------------- |
| P1 | Mateus / Estudante | Médio                   | Remoto              | Usa computador frequentemente              |
| P2 | Pedro / Profissional administrativo            | Médio                   | Presencial                  | Utiliza sistemas e computador com frequência       |
| P3 | Sophia / Estudante    | Médio                   | Presencial              | Utiliza computadores com frequência |

***

### ⚙️ Ambiente e preparação

| Item              | Descrição 🧰                           |
| ----------------- | -------------------------------------- |
| 💻 Dispositivo    | Computador e Notebook                               |
| 🌐 Ambiente       | Sala silenciosa                        |
| 🧪 Versão testada | `main`                  |
| 🎥 Gravação       | Não (somente anotações + prints)       |
| 📎 Evidências     | Prints do formulário + anotações no final |

***

### 🧩 Tarefas (roteiro do teste)

| 🏷️ Tarefa | 📝 Descrição            | 🎯 Sucesso (critério)        | ⏱️ Tempo alvo | ⭐ Prioridade |
| ---------- | ----------------------- | ---------------------------- | ------------- | ------------ |
| T1         | Cadastrar um beneficiário com seus dados pessoais e bancários.   | Beneficiário cadastrado com sucesso e exibido na listagem com status PENDENTE.	 | 02:00         | 🔥 Alta      |
| T2         | Aprovar um beneficiário previamente cadastrado.    | Beneficiário localizado e status atualizado para APROVADO. | 01:30         | 🔥 Alta      |
| T3         | Cadastrar um recebível informando descrição, valor e data de liquidação. | Recebível cadastrado com sucesso e exibido na listagem.  | 02:00         | 🔥 Alta      |
| T4         | Criar um Split para um beneficiário aprovado, informando valor e data do repasse.    | Split criado com sucesso e exibido na listagem com status PENDENTE. | 02:00         | 🔥 Alta      |
| T5         | Executar os Splits agendados para uma determinada data e consultar o resultado no histórico. | Execução realizada e resultado registrado no histórico, com o status correspondente de cada Split.  | 02:00         | 🔥 Alta      |


***

### 📊 Resultados por participante e tarefa

**Legenda:** ✅ Concluiu • ⚠️ Concluiu com ajuda • ❌ Não concluiu

| 👤 Participante | 🏷️ Tarefa | 📌 Status | ⏱️ Tempo (min/seg) | ❗ Erros | 🧩 Dificuldades observadas            | 📝 Comentários do participante                                | 📎 Evidência |
| --------------- | ---------- | --------- | ------------------ | ------: | ------------------------------------- | ------------------------------------------------------------- | ------------ |
| P1 (Mateus)             | T1         | ⚠️         | 02:30              |       2 | Cadastro de dados correspondentes a PF                               | “Faltou detalhamento de formatação esperada dos dados.”                                                 | [Print](evidencias/usabilidade/P1.T1.png)      |
| P1 (Mateus)              | T2         | ✅         | 00:20              |       0 | Nenhuma                               | “Muito boa, bem tranquilo de fazer”                                                | [Print](evidencias/usabilidade/P1.T2.png)      |
| P1 (Mateus)              | T3         | ✅         | 01:10              |       0 | Nenhuma                               | “Tranquila, apenas tive uma dúvida com o que seria descrição”                                            | [Print](evidencias/usabilidade/P1.T3.png)      |
| P1 (Mateus)             | T4         | ✅         | 01:50              |       0 | Nenhuma                  | “Achei fácil.” | [Print](evidencias/usabilidade/P1.T4.png)      |
| P1 (Mateus)              | T5         | ✅         | 02:05              |       0 | Nenhuma                               | “Tranquilo.”                                                  | [Print](evidencias/usabilidade/P1.T5.png)      |
| P2 (Pedro)            | T1         | ✅         | 01:55              |       0 | Nenhuma     | “Bem intuitiva.”       | [Print](evidencias/usabilidade/P2.T1.png)      |
| P2 (Pedro)             | T2         | ✅        | 00:20              |       0 | Nenhuma | “Funcionalidade mais prática do sistema.”                      | [Print](evidencias/usabilidade/P2.T2.png)      |
| P2 (Pedro)             | T3         | ✅         | 02:20             |       0 | Nenhuma   | “Bem detalhada nas instruções.”                                       | [Print](evidencias/usabilidade/P2.T3.png)      |
| P2 (Pedro)               | T4         | ✅        | 03:05              |       0 | Nenhuma           | “Foi ok, tive algumas dúvidas sobre o funcionamento mas consegui fazer.”                     | [Print](evidencias/usabilidade/P2.T4.png)      |
| P2 (Pedro)              | T5         | ⚠️         | 03:10              |       2 |  Dificuldade para entender qual data preencher             | “Tive dúvidas com a execução e precisei de explicação para continuar.” | [Print](evidencias/usabilidade/P2.T5.png)      |
| P3 (Sophia)             | T1         | ✅         | 01:25              |       0 | Nenhuma                               | “Experiência agradável, sem grandes dificuldades.”                                                  | [Print](evidencias/usabilidade/P3.T1.png)      |
| P3 (Sophia)              | T2         | ✅         | 00:32              |       0 | Nenhuma     | “Achei bem fácil.”       | [Print](evidencias/usabilidade/P3.T2.png)      |
| P3 (Sophia)             | T3         | ✅         | 02:15              |       0 | Nenhuma | “Foi ok, tive uma dúvida durante a execução mas consegui executar.”                      | [Print](evidencias/usabilidade/P3.T3.png)      |
| P3 (Sophia)             | T4         | ⚠️         | 03:40             |       3 | Dúvida sobre qual data inserir na criação do Split    | “Média, precisei de ajuda para preencher os dados de criação do Split.”                                       | [Print](evidencias/usabilidade/P3.T4.png)      |
| P3 (Sophia)             | T5         | ✅         | 01:40              |       0 | Nenhuma          | “Ok, sem dificuldades!”                     | [Print](evidencias/usabilidade/P3.T5.png)      |


***

### ⭐ Questionário rápido (pós-teste)

| 👤 Participante | 😃 Facilidade (0–10) | 🧭 Clareza (0–10) | ⚡ Velocidade (0–10) | 💬 O que mais gostou? | 🛠️ O que melhoraria?                           |
| --------------- | -------------------: | ----------------: | ------------------: | --------------------- | ----------------------------------------------- |
| P1 (Mateus)            |                    10 |                 10 |                   9 | “Ser um sistema completo e fácil de usar.”  | “Criar uma interface para melhorar a experiência do usuario”                         |
| P2 (Pedro)             |                    10 |                 9 |                   10 | “A praticidade dos cadastros.”     | “Uma interface gráfica, com dashboards mostrando os beneficiários e os splits que foram efetuados.” |
| P3 (Sophia)             |                    8 |                 6 |                   8 | “Gostei da ideia do projeto e principalmente da facilidade de utilizá-lo, apesar de algumas dúvidas.”      | “Acredito que ter informações que deixam algumas opções de preenchimento mais claras do que lançar e do que é cada campo.”        |

***

### 🧠 Achados e melhorias (priorização)

| 🧩 Achado | 📍 Onde ocorreu (tarefa) | 🎯 Impacto | 📈 Frequência (P1/P2/P3) | 🔥 Prioridade | ✅ Ação recomendada | 🔗 Issue (link) |
| --- | --- | --- | --- | --- | --- | --- |
| Dúvida sobre qual data informar ao criar o Split e ao executar os Splits (5 dos 7 erros) | T4, T5 | Alto | 2/3 (P2 e P3) | 🔥 Alta | Orientar o preenchimento: informar que a data do repasse deve ser posterior a hoje e ter recebível nessa data, e mostrar as datas com recebíveis ou Splits pendentes antes de pedir a data | [#103](https://github.com/users/juanpabloflg/projects/1/views/1?pane=issue&itemId=320530099&issue=juanpabloflg%7Csplit-recebiveis%7C103) |
| Falta de formato esperado e de explicação dos campos de cadastro (CPF ou CNPJ, banco, descrição do recebível) | T1, T3 | Médio | 2/3 (P1 e P3) | ⚠️ Média | Incluir exemplos nos prompts, como "CPF (somente números)" e "Descrição (ex.: Venda cartão 15/10)" | [#103](https://github.com/users/juanpabloflg/projects/1/views/1?pane=issue&itemId=320530099&issue=juanpabloflg%7Csplit-recebiveis%7C103) |
| Ordem do fluxo não explicada na tela (aprovar o beneficiário, cadastrar o recebível, criar o Split e só então executar) | T4, T5 | Médio | 2/3 (P2 e P3) | ⚠️ Média | Acrescentar uma linha de ajuda no início das funções de Criar Split e Executar Splits | (criar issue futuramente) |
| Sugestão de interface gráfica com painel (dashboard) de beneficiários e Splits | Questionário | Baixo | 2/3 (P1 e P2) | 🟦 Baixa | Registrar como evolução futura (interface web), fora do escopo desta entrega | (criar issue futuramente) |

***

### ✅ Conclusão e decisão

| Item | Resultado 📝 |
| --- | --- |
| ✅ Principais pontos positivos | Todas as 15 tarefas foram concluídas (12 sem ajuda e 3 com ajuda), nenhuma foi abandonada. A navegação pelo menu não gerou dificuldade relatada, e a aprovação de beneficiário foi a tarefa mais rápida (média de 00:24). As notas médias foram 9,3 de facilidade, 8,3 de clareza e 9,0 de velocidade. Os participantes elogiaram a praticidade dos cadastros e a ideia do projeto. |
| ⚠️ Principais dificuldades | A data a informar nas telas de Criar Split e Executar Splits (5 dos 7 erros registrados), a falta de formato esperado e de explicação dos campos no cadastro e a ordem do fluxo, que não está explicada na tela. A participante P3 deu nota 6 de clareza por causa dessas dúvidas. |
| 🛠️ Top 3 melhorias | <p>1) Orientar o preenchimento da data nas telas de Criar Split e Executar Splits (🔥)<br>2) Incluir exemplos e formato esperado nos campos de cadastro (⚠️)<br>3) Acrescentar texto de ajuda sobre a ordem do fluxo (⚠️)</p> |
| 🟢 Go/No-Go para entrega | **Go**, condicionado a implementar as melhorias 1 e 2, que são ajustes de texto nas telas e têm baixo esforço, antes da entrega final (implementadas na issue `#103`). A interface gráfica fica como evolução futura. |
| 📎 Links de evidência | Pasta `evidencias/usabilidade/` (prints P1.T1 a P3.T5) |