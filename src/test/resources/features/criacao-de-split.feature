# language: pt
Funcionalidade: Criação de Split
  Para dividir os recebíveis do estabelecimento com seus beneficiários
  O operador cria Splits que respeitam as regras de negócio do sistema
 
  Contexto:
    Dado que hoje é "20/11/2026"
    E que existe um recebível de "1000,00" com liquidação em "25/11/2026"
 
  @BDD-01
  Cenário: Criar Split válido
    Dado que existe um beneficiário "Maria Silva" aprovado
    Quando o operador cria um Split de "800,00" para "Maria Silva" na data "25/11/2026"
    Então o Split deve ser criado com status "PENDENTE"
 
  @BDD-02
  Cenário: Beneficiário não aprovado
    Dado que existe um beneficiário "João Souza" pendente
    Quando o operador cria um Split de "800,00" para "João Souza" na data "25/11/2026"
    Então o Split deve ser rejeitado com a mensagem "O beneficiário precisa estar aprovado para receber um Split."
 
  @BDD-03
  Cenário: Data inválida
    Dado que existe um beneficiário "Maria Silva" aprovado
    Quando o operador cria um Split de "800,00" para "Maria Silva" na data "20/11/2026"
    Então o Split deve ser rejeitado com a mensagem "O Split deve ser configurado com no mínimo 1 dia de antecedência."
 
  @BDD-06
  Cenário: Valor acima do recebível
    Dado que existe um beneficiário "Maria Silva" aprovado
    Quando o operador cria um Split de "1500,00" para "Maria Silva" na data "25/11/2026"
    Então o Split deve ser rejeitado com a mensagem "O valor do Split não pode ultrapassar o valor dos recebíveis da data."