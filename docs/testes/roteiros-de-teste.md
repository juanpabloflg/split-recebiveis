# Roteiros de Teste
 
## Convenções e status
 
| Item | Padrão |
| --- | --- |
| 🏷️ ID do teste | **RT-XX** (ex.: RT-01, RT-02...) |
| 📌 Status | 🟡 Planejado • 🔵 Em execução • 🟢 Passou • 🔴 Falhou • ⚫ Bloqueado |
| ⭐ Prioridade | 🔥 Alta • ⚠️ Média • 🟦 Baixa |
| 📎 Evidência | Print, log, vídeo ou link do PR/Issue (arquivos em `evidencias/`) |
 
> Cada roteiro é executado com o sistema recém-aberto, pois os dados ficam apenas em memória.
> Roteiros RT-01 a RT-04 foram executados por João (PR #67). Roteiros RT-05 e RT-06 foram executados por Juan (PR #68).
 
## Resumo
 
| ID | Funcionalidade | Prioridade | Status |
| --- | --- | --- | --- |
| RT-01 | Cadastrar e consultar beneficiário | 🔥 Alta | 🟡 Planejado |
| RT-02 | Aprovar beneficiário | 🔥 Alta | 🟡 Planejado |
| RT-03 | Cadastro de beneficiário com nome vazio | ⚠️ Média | 🟡 Planejado |
| RT-04 | Cadastro de beneficiário com tipo de pessoa inválido | ⚠️ Média | 🟡 Planejado |
| RT-05 | Cadastrar e consultar recebível | 🔥 Alta | 🟡 Planejado |
| RT-06 | Cadastro de recebível com valores limite | ⚠️ Média | 🟡 Planejado |
 
## Cobertura mínima exigida pelo enunciado
 
| Categoria | Mínimo | Roteiros desta Sprint |
| --- | --- | --- |
| Fluxos principais | 2 | RT-01, RT-02, RT-05 |
| Validações e erros | 2 | RT-03, RT-04 |
| Bordas (limites e entradas inválidas) | 1 | RT-06 |
 
---
 
## RT-01 — Cadastrar e consultar beneficiário
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-01 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Cadastrar e consultar beneficiário (RF01, RF02) |
| 🎯 Objetivo | Verificar o fluxo principal de cadastro de um beneficiário pessoa física |
| 🔐 Pré-condição | Sistema recém-aberto, sem beneficiários |
| 🧪 Dados de teste | DT-01: Maria Silva, PF, CPF 12345678901, banco 001, agência 1234, conta 56789-0 |
| 🪜 Passos | 1) Digitar `1` no menu<br>2) Preencher os campos com os dados de teste<br>3) Ler a mensagem exibida<br>4) Digitar `2` para listar<br>5) Digitar `x` e conferir que a mensagem se repete<br>6) Digitar `0` para voltar ao menu |
| ✅ Resultado esperado | Mensagem "Beneficiário cadastrado com sucesso!" com ID 1 e Status PENDENTE. A listagem mostra Maria Silva e só volta ao menu quando for digitado `0`. |
| 🧾 Resultado obtido | Apareceu "Beneficiário cadastrado com sucesso!" com ID 1 e Status PENDENTE. A listagem exibiu Maria Silva e só voltou ao menu após digitar '0'. |
| 👤 Executado por / data | João, 20/10/2026 |
| 📌 Status | 🟡 Planejado |
| 📎 Evidência | [RT-01.png](evidencias/RT-01.png) |
 
## RT-02 — Aprovar beneficiário
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-02 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Aprovar beneficiário (RF03) |
| 🎯 Objetivo | Verificar a aprovação de um beneficiário pendente e as validações do ID |
| 🔐 Pré-condição | RT-01 executado na mesma sessão (beneficiário ID 1 pendente) |
| 🧪 Dados de teste | IDs `1`, `99` e `abc` |
| 🪜 Passos | 1) Digitar `3` no menu e conferir que o beneficiário 1 aparece como pendente<br>2) Digitar `abc`<br>3) Digitar `3` de novo e depois `99`<br>4) Digitar `3` de novo e depois `1`<br>5) Digitar `2` e conferir o status<br>6) Digitar `0` para voltar<br>7) Digitar `3` e depois `0` |
| ✅ Resultado esperado | (2) "ID inválido. Digite apenas números." (3) "Não foi possível aprovar: Beneficiário não encontrado." (4) "Beneficiário aprovado com sucesso!" com Status APROVADO. (5) A listagem mostra APROVADO. (7) "Nenhum beneficiário pendente de aprovação." |
| 🧾 Resultado obtido | "Exibiu ID inválido. Digite apenas números." (3) "Exibiu não foi possível aprovar: Beneficiário não encontrado." (4) "Exibiu Beneficiário aprovado!" com Status APROVADO. (5) A listagem mostra APROVADO. (7) "Nenhum beneficiário pendente de aprovação."  |
| 👤 Executado por / data | João, 20/10/2026 |
| 📌 Status | 🟡 Planejado |
| 📎 Evidência | [RT-02.1.png](evidencias/RT-02.1.png)<br>[RT-02.2.png](evidencias/RT-02.2.png)<br>[RT-02.3.png](evidencias/RT-02.3.png) | 
 
## RT-03 — Cadastro de beneficiário com nome vazio
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-03 |
| ⭐ Prioridade | ⚠️ Média |
| 🧩 Funcionalidade | Cadastrar beneficiário (RF01) |
| 🎯 Objetivo | Verificar a validação de nome obrigatório |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Nome vazio (só Enter), PF, 12345678901, 001, 1234, 56789-0 |
| 🪜 Passos | 1) Digitar `1` no menu<br>2) No campo Nome, apenas pressionar Enter<br>3) Preencher os demais campos<br>4) Digitar `2` e depois `0` |
| ✅ Resultado esperado | Mensagem "Não foi possível cadastrar: O nome é obrigatório." A listagem mostra "Nenhum beneficiário cadastrado." |
| 🧾 Resultado obtido | "O terminal exibiu: Não foi possível cadastrar: O nome é obrigatório." A listagem mostra "Nenhum beneficiário cadastrado." |
| 👤 Executado por / data | João, 20/10/2026 |
| 📌 Status | 🟡 Planejado |
| 📎 Evidência | [RT-03.png](evidencias/RT-03.png) |
 
## RT-04 — Cadastro de beneficiário com tipo de pessoa inválido
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-04 |
| ⭐ Prioridade | ⚠️ Média |
| 🧩 Funcionalidade | Cadastrar beneficiário (RF01) |
| 🎯 Objetivo | Verificar a validação do tipo de pessoa |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Nome `João`, tipo `XX` |
| 🪜 Passos | 1) Digitar `1` no menu<br>2) Nome: `João`<br>3) Tipo de pessoa: `XX` |
| ✅ Resultado esperado | Mensagem "Tipo de pessoa inválido. Digite PF ou PJ." e retorno ao menu, sem cadastrar. |
| 🧾 Resultado obtido | "O terminal exibiu: Tipo de pessoa inválido. Digite PF ou PJ." e retorno ao menu, sem cadastrar. |
| 👤 Executado por / data | João, 20/10/2026 |
| 📌 Status | 🟡 Planejado |
| 📎 Evidência | [RT-04.png](evidencias/RT-04.png) |
 
## RT-05 — Cadastrar e consultar recebível
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-05 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Cadastrar e consultar recebível (RF04, RF05) |
| 🎯 Objetivo | Verificar o fluxo principal de recebíveis |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | DT-03: descrição `Venda cartão 15/10`, valor `1.500,50`, data `20/10/2026` |
| 🪜 Passos | 1) Digitar `4` no menu<br>2) Preencher Descrição, Valor e Data<br>3) Ler a mensagem exibida<br>4) Digitar `5` para listar<br>5) Digitar `0` para voltar ao menu |
| ✅ Resultado esperado | Mensagem "Recebível cadastrado com sucesso!" com ID 1, valor total e valor disponível de R$ 1.500,50 e data 20/10/2026. A listagem mostra o recebível e só volta ao menu com `0`. |
| 🧾 Resultado obtido | "O terminal exibiu: Recebível cadastrado com sucesso!" com ID 1, valor total e valor disponível de R$ 1.500,50 e data 20/10/2026. A listagem mostra o recebível e só volta ao menu com `0`. |
| 👤 Executado por / data | Juan, 20/10/2026 |
| 📌 Status | 🟡 Planejado |
| 📎 Evidência | [RT-05.png](evidencias/RT-05.png) |
 
## RT-06 — Cadastro de recebível com valores limite
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-06 |
| ⭐ Prioridade | ⚠️ Média |
| 🧩 Funcionalidade | Cadastrar recebível (RF04) |
| 🎯 Objetivo | Verificar o valor mínimo aceito e as validações de valor |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | DT-04: valores `0,01`, `0` e `10,999`, todos com a data `20/10/2026` |
| 🪜 Passos | 1) Digitar `4`; descrição `Venda mínima`; valor `0,01`<br>2) Digitar `4`; descrição `Teste`; valor `0`<br>3) Digitar `4`; descrição `Teste`; valor `10,999`<br>4) Digitar `5` e depois `0` |
| ✅ Resultado esperado | (1) Cadastrado com R$ 0,01. (2) "Não foi possível cadastrar: O valor do recebível deve ser maior que zero." (3) "Não foi possível cadastrar: O valor deve ter no máximo 2 casas decimais." (4) A listagem mostra só o recebível de R$ 0,01. |
| 🧾 Resultado obtido | (1) Cadastrado com R$ 0,01. (2) "O terminal exibiu: Não foi possível cadastrar: O valor do recebível deve ser maior que zero." (3) "Não foi possível cadastrar: O valor deve ter no máximo 2 casas decimais." (4) A listagem mostra só o recebível de R$ 0,01. |
| 👤 Executado por / data | Juan, 20/10/2026 |
| 📌 Status | 🟡 Planejado |
| 📎 Evidência | [RT-06.1.png](evidencias/RT-06.1.png)<br>[RT-06.2.png](evidencias/RT-06.2.png) |