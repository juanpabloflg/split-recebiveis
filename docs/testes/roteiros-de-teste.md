# Roteiros de Teste
 
## Convenções e status
 
| Item | Padrão |
| --- | --- |
| 🏷️ ID do teste | **RT-XX** (ex.: RT-01, RT-02...) |
| 📌 Status | 🟡 Planejado • 🔵 Em execução • 🟢 Passou • 🔴 Falhou • ⚫ Bloqueado |
| ⭐ Prioridade | 🔥 Alta • ⚠️ Média • 🟦 Baixa |
| 📎 Evidência | Print, log, vídeo ou link do PR/Issue (arquivos em `evidencias/`) |
 
> Cada roteiro é executado com o sistema recém-aberto, pois os dados ficam apenas em memória.
> RT-01 a RT-04 foram executados por João (PR `#67`). RT-05 e RT-06 por Juan (PR `#68`). RT-07 a RT-10 por João (PR `#86`). RT-11 por Rafael (PR `#87`). RT-12 e RT-13 por Juan (PR `#88`). RT-13 a RT17 por Juan (PR `#102`).
 
## Resumo
 
| ID | Funcionalidade | Prioridade | Status |
| --- | --- | --- | --- |
| RT-01 | Cadastrar e consultar beneficiário | 🔥 Alta | 🟢 Passou |
| RT-02 | Aprovar beneficiário | 🔥 Alta | 🟢 Passou |
| RT-03 | Cadastro de beneficiário com nome vazio | ⚠️ Média | 🟢 Passou |
| RT-04 | Cadastro de beneficiário com tipo de pessoa inválido | ⚠️ Média | 🟢 Passou |
| RT-05 | Cadastrar e consultar recebível | 🔥 Alta | 🟢 Passou |
| RT-06 | Cadastro de recebível com valores limite | ⚠️ Média | 🟢 Passou |
| RT-07 | Criar Split válido | 🔥 Alta | 🟢 Passou |
| RT-08 | Split para beneficiário pendente (RN01) | 🔥 Alta | 🟢 Passou |
| RT-09 | Split sem antecedência mínima (RN02) | 🔥 Alta | 🟢 Passou |
| RT-10 | Valor acima do recebível e data sem recebível (RN03) | ⚠️ Média | 🟢 Passou |
| RT-11 | Listar Splits | ⚠️ Média | 🟢 Passou |
| RT-12 | Editar Split | 🔥 Alta | 🟢 Passou |
| RT-13 | Excluir Split | 🔥 Alta | 🟢 Passou |
| RT-14 | Executar Splits com saldo suficiente | 🔥 Alta | 🟢 Passou |
| RT-15 | Recusar Split por saldo insuficiente (RN04) | 🔥 Alta | 🟢 Passou |
| RT-16 | Consultar histórico de Splits | 🔥 Alta | 🟢 Passou |
| RT-17 | Gerar relatório resumido | 🔥 Alta | 🟢 Passou |
 
## Cobertura mínima exigida pelo enunciado

| Categoria | Mínimo | Roteiros |
| --- | --- | --- |
| Fluxos principais | 2 | RT-01, RT-02, RT-05, RT-07, RT-11, RT-12, RT-13, RT-14, RT-16, RT-17 |
| Validações e erros | 2 | RT-03, RT-04, RT-08, RT-10, RT-15 |
| Bordas (limites e entradas inválidas) | 1 | RT-06, RT-09 |

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
| 👤 Executado por / data | João Pedro, 20/10/2026 |
| 📌 Status | 🟢 Passou |
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
| 👤 Executado por / data | João Pedro, 20/10/2026 |
| 📌 Status | 🟢 Passou |
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
| 👤 Executado por / data | João Pedro, 20/10/2026 |
| 📌 Status | 🟢 Passou |
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
| 👤 Executado por / data | João Pedro, 20/10/2026 |
| 📌 Status | 🟢 Passou |
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
| 📌 Status | 🟢 Passou |
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
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-06.1.png](evidencias/RT-06.1.png)<br>[RT-06.2.png](evidencias/RT-06.2.png) |

## RT-07 — Criar Split válido
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-07 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Criar Split (RF06) |
| 🎯 Objetivo | Verificar a criação de um Split para beneficiário aprovado |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | DT-05: Maria Silva aprovada; recebível R$ 1.000,00 em 30/12/2026; Split de R$ 800,00 em 30/12/2026 |
| 🪜 Passos | 1) Cadastrar Maria Silva (opção 1) e aprovar (opção 3, ID 1)<br>2) Cadastrar o recebível (opção 4)<br>3) Digitar `6`, ID `1`, valor `800,00`, data `30/12/2026` |
| ✅ Resultado esperado | "Split criado com sucesso!" com ID 1, beneficiário Maria Silva, valor R$ 800,00, data 30/12/2026 e status PENDENTE |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | João Pedro, 04/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-07.1.png](evidencias/RT-07.1.png)<br>[RT-07.2.png](evidencias/RT-07.2.png)<br>[RT-07.3.png](evidencias/RT-07.3.png)<br>[RT-07.4.png](evidencias/RT-07.4.png) |
 
## RT-08 — Split para beneficiário pendente (RN01)
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-08 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Criar Split (RF06, RN01) |
| 🎯 Objetivo | Verificar que beneficiário pendente não recebe Split |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Maria Silva (aprovada), João Souza (pendente, ID 2), recebível R$ 1.000,00 em 30/12/2026 |
| 🪜 Passos | 1) Cadastrar Maria e João; aprovar só a Maria<br>2) Cadastrar o recebível<br>3) Digitar `6`, ID `2`, valor `800,00`, data `30/12/2026` |
| ✅ Resultado esperado | "Não foi possível criar o Split: O beneficiário precisa estar aprovado para receber um Split." |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | João Pedro, 04/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-08.1.png](evidencias/RT-08.1.png)<br>[RT-08.2.png](evidencias/RT-08.2.png)<br>[RT-08.3.png](evidencias/RT-08.3.png)<br>[RT-08.4.png](evidencias/RT-08.4.png) |
 
## RT-09 — Split sem antecedência mínima (RN02)
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-09 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Criar Split (RF06, RN02) |
| 🎯 Objetivo | Verificar a antecedência mínima de 1 dia |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Maria Silva aprovada; recebível R$ 1.000,00 e Split R$ 800,00, ambos com a data de hoje |
| 🪜 Passos | 1) Cadastrar e aprovar Maria Silva<br>2) Cadastrar o recebível com a data de hoje<br>3) Digitar `6`, ID `1`, valor `800,00` e a data de hoje |
| ✅ Resultado esperado | "Não foi possível criar o Split: O Split deve ser configurado com no mínimo 1 dia de antecedência." |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | João Pedro, 04/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-09.1.png](evidencias/RT-09.1.png)<br>[RT-09.2.png](evidencias/RT-09.2.png)<br>[RT-09.3.png](evidencias/RT-09.3.png) |
 
## RT-10 — Valor acima do recebível e data sem recebível (RN03)
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-10 |
| ⭐ Prioridade | ⚠️ Média |
| 🧩 Funcionalidade | Criar Split (RF06, RN03) |
| 🎯 Objetivo | Verificar o limite do valor e a exigência de recebível na data |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Maria Silva aprovada; recebível R$ 1.000,00 em 30/12/2026; valores `1500,00` e `100,00` |
| 🪜 Passos | 1) Cadastrar e aprovar Maria; cadastrar o recebível<br>2) Digitar `6`, ID `1`, valor `1500,00`, data `30/12/2026`<br>3) Digitar `6`, ID `1`, valor `100,00`, data `31/12/2026` |
| ✅ Resultado esperado | (2) "Não foi possível criar o Split: O valor do Split não pode ultrapassar o valor dos recebíveis da data." (3) "Não foi possível criar o Split: Não há recebíveis cadastrados para a data informada." |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | João Pedro, 04/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-10.1.png](evidencias/RT-10.1.png)<br>[RT-10.2.png](evidencias/RT-10.2.png)<br>[RT-10.3.png](evidencias/RT-10.3.png) |

## RT-11 — Listar Splits
 
| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-11 |
| ⭐ Prioridade | ⚠️ Média |
| 🧩 Funcionalidade | Listar Splits (RF07) |
| 🎯 Objetivo | Verificar a listagem de Splits vazia e com um Split criado |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | DT-05: Maria Silva aprovada; recebível R$ 1.000,00 em 30/12/2026; Split de R$ 800,00 em 30/12/2026 |
| 🪜 Passos | 1) Digitar `7` no menu<br>2) Digitar `x` e conferir que a mensagem se repete; depois digitar `0`<br>3) Executar o RT-07 (criar o Split de R$ 800,00)<br>4) Digitar `7`<br>5) Digitar `0` para voltar ao menu |
| ✅ Resultado esperado | (1) "Nenhum Split cadastrado." e a mensagem "Para voltar ao menu, digite 0:", que se repete com `x`. (4) O Split ID 1 listado com beneficiário Maria Silva (ID 1), valor R$ 800,00, data 30/12/2026 e status PENDENTE, aguardando o `0`. |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Rafael, 05/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-11.1.png](evidencias/RT-11.1.png)<br>[RT-11.2.png](evidencias/RT-11.2.png) |

## RT-12 — Editar Split

| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-12 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Editar Split pendente (RF08, RN05, RN03) |
| 🎯 Objetivo | Verificar a edição de valor com sucesso e a rejeição de valor acima do recebível |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | DT-05; novo valor `500,00` e depois `1500,00` |
| 🪜 Passos | 1) Executar o RT-07 (criar o Split de R$ 800,00)<br>2) Digitar `8`, ID `1`, novo valor `500,00` e Enter na data<br>3) Digitar `8`, ID `1`, novo valor `1500,00` e Enter na data<br>4) Digitar `7` e conferir o valor |
| ✅ Resultado esperado | (2) "Split editado com sucesso!" com valor R$ 500,00, data 30/12/2026 e status PENDENTE. (3) "Não foi possível editar o Split: O valor do Split não pode ultrapassar o valor dos recebíveis da data." (4) A listagem mostra o Split com R$ 500,00. |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Juan, 06/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-12.1.png](evidencias/RT-12.1.png)<br>[RT-12.2.png](evidencias/RT-12.2.png) |

## RT-13 — Excluir Split

| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-13 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Excluir Split pendente (RF09, RN06) |
| 🎯 Objetivo | Verificar a confirmação e a exclusão de um Split pendente |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | DT-05; confirmações `N` e `S` |
| 🪜 Passos | 1) Executar o RT-07 (criar o Split de R$ 800,00)<br>2) Digitar `9`, ID `1` e `N`<br>3) Digitar `9`, ID `1` e `S`<br>4) Digitar `7` |
| ✅ Resultado esperado | (2) "Exclusão cancelada." e o Split continua na lista. (3) "Split excluído com sucesso!". (4) "Nenhum Split cadastrado." |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Juan, 06/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-13.1.png](evidencias/RT-13.1.png)<br>[RT-13.2.png](evidencias/RT-13.2.png) |

---

## RT-14 — Executar Splits com saldo suficiente

| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-14 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Executar Splits agendados (RF10, RN07) |
| 🎯 Objetivo | Verificar a execução de todos os Splits quando há saldo suficiente |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Maria Silva aprovada; recebível R$ 1.000,00 em 30/12/2027; Splits de R$ 400,00 e R$ 300,00 em 30/12/2027 |
| 🪜 Passos | 1) Cadastrar e aprovar Maria Silva (opções 1 e 3)<br>2) Cadastrar o recebível de R$ 1.000,00 em 30/12/2027 (opção 4)<br>3) Criar Split de R$ 400,00 em 30/12/2027 (opção 6)<br>4) Criar Split de R$ 300,00 em 30/12/2027 (opção 6)<br>5) Digitar `10`, informar `30/12/2027`<br>6) Verificar fila exibida e saldo disponível<br>7) Responder `S` para confirmar |
| ✅ Resultado esperado | Fila exibe 1º R$ 400,00 e 2º R$ 300,00 com saldo R$ 1.000,00. Ambos com status EXECUTADO. Mensagem "2 Split(s) executado(s) e 0 recusado(s)." Opção 5 mostra saldo disponível R$ 300,00. |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Juan, 25/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-14.1.png](evidencias/RT-14.1.png)<br>[RT-14.2.png](evidencias/RT-14.2.png) |

---

## RT-15 — Recusar Split por saldo insuficiente (RN04)

| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-15 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Executar Splits agendados com recusa por saldo (RF10, RN04) |
| 🎯 Objetivo | Verificar que um Split é recusado com motivo quando o saldo é insuficiente, sem debitar nada |
| 🔐 Pré-condição | Sistema recém-aberto |
| 🧪 Dados de teste | Maria Silva aprovada; recebível R$ 1.000,00 em 30/12/2027; Splits de R$ 400,00, R$ 300,00 e R$ 800,00 em 30/12/2027 |
| 🪜 Passos | 1) Realizar os passos 1 a 4 do RT-14<br>2) Criar Split de R$ 800,00 em 30/12/2027 (opção 6)<br>3) Digitar `10`, data `30/12/2027` e confirmar `S` |
| ✅ Resultado esperado | Split R$ 400,00: EXECUTADO. Split R$ 300,00: EXECUTADO. Split R$ 800,00: RECUSADO com motivo "Saldo insuficiente: disponível R$ 300,00 para um Split de R$ 800,00." Mensagem "2 Split(s) executado(s) e 1 recusado(s)." Saldo disponível: R$ 300,00 (inalterado pelo recusado). |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Juan, 25/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-15.png](evidencias/RT-15.png) |

---

## RT-16 — Consultar histórico de Splits

| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-16 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Consultar histórico de Splits (RF11) |
| 🎯 Objetivo | Verificar que o histórico registra criação, execução e recusa na ordem cronológica |
| 🔐 Pré-condição | RT-15 executado na mesma sessão |
| 🧪 Dados de teste | Estado resultante do RT-15 |
| 🪜 Passos | 1) Digitar `11` no menu |
| ✅ Resultado esperado | 3 linhas CRIADO (uma por Split), 2 linhas EXECUTADO (Splits R$ 400 e R$ 300) e 1 linha RECUSADO (Split R$ 800, com o motivo). Total: "6 operação(ões) registrada(s)." Registros em ordem cronológica. |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Juan, 25/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-16.png](evidencias/RT-16.png) |

---

## RT-17 — Gerar relatório resumido

| Campo | Detalhe |
| --- | --- |
| 🏷️ ID | RT-17 |
| ⭐ Prioridade | 🔥 Alta |
| 🧩 Funcionalidade | Gerar relatório resumido (RF12) |
| 🎯 Objetivo | Verificar que o relatório exibe os totais e valores por status corretamente |
| 🔐 Pré-condição | RT-15 executado na mesma sessão |
| 🧪 Dados de teste | Estado resultante do RT-15 |
| 🪜 Passos | 1) Digitar `12` no menu |
| ✅ Resultado esperado | Beneficiários: Total 1, Aprovados 1, Pendentes 0. Recebíveis: Quantidade 1, Valor total R$ 1.000,00, Saldo disponível R$ 300,00. Splits: Total 3, Pendentes 0 (R$ 0,00), Executados 2 (R$ 700,00), Recusados 1 (R$ 800,00). Histórico: 6 operações registradas. |
| 🧾 Resultado obtido | Conforme o esperado, anexado em evidência |
| 👤 Executado por / data | Juan, 25/11/2026 |
| 📌 Status | 🟢 Passou |
| 📎 Evidência | [RT-17.png](evidencias/RT-17.png) |