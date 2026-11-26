# language: pt
Funcionalidade: Execução de Splits agendados
  Como estabelecimento
  Quero executar os Splits pendentes de uma data
  Para repassar os valores na ordem de configuração respeitando o saldo disponível

  Cenário: BDD-04 Recusar Split por saldo insuficiente (RN04)
    Dado que a data de execução simulada é "01/01/2027"
    E que existe um recebível de execução de "1000,00" com liquidação em "02/01/2027"
    E que existe um beneficiário aprovado de nome "Maria"
    E que existe um Split pendente de "900,00" para "Maria" na data "02/01/2027"
    E que existe um Split pendente de "500,00" para "Maria" na data "02/01/2027"
    Quando o operador executa os Splits da data "02/01/2027"
    Então o Split de "900,00" deve ter status "EXECUTADO"
    E o Split de "500,00" deve ter status "RECUSADO"
    E o motivo da recusa deve conter "Saldo insuficiente"

  Cenário: BDD-05 Executar Splits na ordem de configuração com saldo suficiente (RN07)
    Dado que a data de execução simulada é "01/01/2027"
    E que existe um recebível de execução de "1000,00" com liquidação em "02/01/2027"
    E que existe um beneficiário aprovado de nome "Maria"
    E que existe um Split pendente de "300,00" para "Maria" na data "02/01/2027"
    E que existe um Split pendente de "200,00" para "Maria" na data "02/01/2027"
    E que existe um Split pendente de "100,00" para "Maria" na data "02/01/2027"
    Quando o operador executa os Splits da data "02/01/2027"
    Então todos os Splits devem ter status "EXECUTADO"
    E o saldo disponível na data "02/01/2027" deve ser "400,00"