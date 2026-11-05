# Regras de Negócio
 
### RN01 — Beneficiário precisa estar aprovado
- **Regra:** um Split só pode ser criado para beneficiário com status Aprovado.
- **Exemplo:** beneficiário pendente → Split rejeitado.
- **Validada:** na criação do Split.
 
### RN02 — Antecedência mínima
- **Regra:** a data do repasse precisa ser posterior à data de hoje (mínimo de 1 dia corrido de antecedência). Decisão do grupo na Sprint 2: não são considerados dias úteis.
- **Exemplo:** hoje é 07/10. Split para 07/10 → rejeitado. Split para 08/10 → permitido.
- **Validada:** na criação e na edição do Split.
 
### RN03 — Valor não pode ultrapassar o recebível
- **Regra:** o valor do Split não pode ser maior que o total dos recebíveis com liquidação na data do repasse. Se não houver recebíveis nessa data, o Split é rejeitado.
- **Exemplo:** recebíveis de R$ 1.000 na data e Split de R$ 1.500 → rejeitado. Dois recebíveis na mesma data, de R$ 600 e R$ 400, permitem um Split de até R$ 1.000.
- **Validada:** na criação e na edição do Split.
 
### RN04 — Saldo insuficiente na execução
- **Regra:** se, na data do Split, o valor disponível em recebíveis for insuficiente, o Split recebe o status Recusado e nenhum valor é repassado ao beneficiário. O valor disponível da data é a soma do valor disponível dos recebíveis com liquidação nessa data.
- **Exemplo:** disponível R$ 1.000 e Split de R$ 800 → executado. Disponível R$ 500 e Split de R$ 800 → Recusado.
- **Validada:** na execução do Split (Sprint 3).
 
### RN05 — Apenas Split pendente pode ser editado
- **Regra:** somente Splits com status Pendente podem ser editados. Na edição, o novo valor e a nova data passam de novo pelas regras RN02 e RN03.
- **Exemplo:** Pendente → pode editar. Executado ou Recusado → não pode.
- **Validada:** na edição do Split.
 
### RN06 — Apenas Split pendente pode ser excluído
- **Regra:** somente Splits com status Pendente podem ser excluídos.
- **Exemplo:** Pendente → pode excluir. Executado ou Recusado → não pode.
- **Validada:** na exclusão do Split.
 
### RN07 — Prioridade entre Splits da mesma data
- **Regra:** Splits agendados para a mesma data entram em uma fila de prioridade, e o primeiro configurado é o primeiro executado.
- **Exemplo:** Splits A (criado primeiro) e B (criado depois) para o mesmo dia → A é executado antes de B.
- **Validada:** na execução do Split (Sprint 3).
 
### RN08 — Beneficiário nasce pendente
- **Regra:** todo beneficiário é cadastrado com status Pendente e só passa a Aprovado pela operação de aprovação. Aprovar um beneficiário que já está aprovado é um erro.
- **Exemplo:** cadastro → Pendente. Aprovação → Aprovado. Segunda aprovação → erro.
- **Validada:** no cadastro e na aprovação.
 
### RN09 — Dados do recebível
- **Regra:** todo recebível deve ter descrição, valor maior que zero com no máximo 2 casas decimais e data de liquidação. O valor disponível inicial é igual ao valor total.
- **Exemplo:** R$ 0,01 é aceito. R$ 0,00 e R$ 10,999 são rejeitados.
- **Validada:** no cadastro do recebível.
 
## Observações
 
- **RN03 e RN04 não são a mesma regra.** A RN03 é verificada na criação e na edição (o Split nem chega a ser criado ou alterado). A RN04 é verificada na execução, na data programada, e o Split fica com o status Recusado.
 
## Decisões da Sprint 2
 
- **Antecedência (RN02):** a data do repasse deve ser posterior a hoje. Dias úteis não são considerados.
- **Valor (RN03):** o limite é o total dos recebíveis com liquidação na data do Split. Sem recebível na data, o Split é rejeitado.
- **Ordem das validações na criação:** primeiro o beneficiário aprovado (RN01), depois a data (RN02) e por último o valor (RN03).
- **Status do Split:** todo Split nasce Pendente. As mudanças para Executado e Recusado entram na Sprint 3 com a execução. Por isso, na Sprint 2, as regras RN05 e RN06 são validadas por testes unitários, pois ainda não há como levar um Split a esses estados pelo console.
- **Relógio:** o serviço de Split recebe um relógio (Clock), para que os testes não dependam do dia em que rodam.