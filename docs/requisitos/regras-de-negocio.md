# Regras de Negócio

### RN01 — Beneficiário precisa estar aprovado
- **Regra:** um Split só pode ser criado para beneficiário com status Aprovado.
- **Exemplo:** beneficiário pendente → Split rejeitado.
- **Validada:** na criação do Split.

### RN02 — Antecedência mínima
- **Regra:** o Split deve ser configurado com no mínimo 1 dia de antecedência.
- **Exemplo:** hoje é 07/10. Split para 07/10 → rejeitado. Split para 08/10 → permitido.
- **Validada:** na criação do Split.

### RN03 — Valor não pode ultrapassar o recebível
- **Regra:** o valor do Split não pode ser maior que o valor do recebível.
- **Exemplo:** recebível de R$ 1.000 e Split de R$ 1.500 → rejeitado.
- **Validada:** na criação do Split.

### RN04 — Saldo insuficiente na execução
- **Regra:** se, na data do Split, o valor disponível em recebíveis for insuficiente, o Split recebe o status Recusado e nenhum valor é repassado ao beneficiário.
- **Exemplo:** disponível R$ 1.000 e Split de R$ 800 → executado. Disponível R$ 500 e Split de R$ 800 → Recusado.
- **Validada:** na execução do Split.

### RN05 — Apenas Split pendente pode ser editado
- **Regra:** somente Splits com status Pendente podem ser editados.
- **Exemplo:** Pendente → pode editar. Executado ou Recusado → não pode.
- **Validada:** na edição do Split.

### RN06 — Apenas Split pendente pode ser excluído
- **Regra:** somente Splits com status Pendente podem ser excluídos.
- **Exemplo:** Pendente → pode excluir. Executado ou Recusado → não pode.
- **Validada:** na exclusão do Split.

### RN07 — Prioridade entre Splits da mesma data
- **Regra:** Splits agendados para a mesma data entram em uma fila de prioridade, e o primeiro configurado é o primeiro executado.
- **Exemplo:** Splits A (criado primeiro) e B (criado depois) para o mesmo dia → A é executado antes de B.
- **Validada:** na execução do Split.

### RN08 — Beneficiário nasce pendente
- **Regra:** todo beneficiário é cadastrado com status Pendente e só passa a Aprovado pela operação de aprovação. Aprovar um beneficiário que já está aprovado é um erro.
- **Exemplo:** cadastro → Pendente. Aprovação → Aprovado. Segunda aprovação → erro.
- **Validada:** no cadastro e na aprovação.

### RN09 — Dados do recebível
- **Regra:** todo recebível deve ter descrição, valor maior que zero com no máximo 2 casas decimais e data de liquidação. O valor disponível inicial é igual ao valor total.
- **Exemplo:** R$ 0,01 é aceito. R$ 0,00 e R$ 10,999 são rejeitados.
- **Validada:** no cadastro do recebível.

## Observação

- **RN03 e RN04 não são a mesma regra.** A RN03 é verificada na criação (o Split nem chega a ser criado). A RN04 é verificada na execução, na data programada, e o Split fica com o status Recusado.