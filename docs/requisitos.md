# Requisitos do Sistema
 
## 1. Requisitos Funcionais
 
* **RF01 — Cadastro de beneficiários:** o sistema deve permitir cadastrar beneficiários, informando nome, tipo de pessoa, documento e dados bancários.
* **RF02 — Listagem de beneficiários:** o sistema deve permitir consultar os beneficiários cadastrados.
* **RF03 — Configuração de Split:** o sistema deve permitir configurar a divisão de recebíveis entre o estabelecimento e beneficiários.
* **RF04 — Consulta de Splits:** o sistema deve permitir consultar os Splits cadastrados e seus respectivos status.

## 2. Requisitos Não Funcionais
 
* **RNF01 — Tecnologia:** o sistema deve ser desenvolvido em Java, utilizando Maven para gerenciamento do projeto.
* **RNF02 — Organização:** o código deve ser organizado em camadas, separando domínio, serviços, infraestrutura e interface.
* **RNF03 — Testabilidade:** as regras de negócio devem possuir testes automatizados.
* **RNF04 — Usabilidade:** o sistema deve apresentar mensagens claras para orientar o usuário e informar erros de entrada.

## 3. Regras de Negócio
 
* **RN01 — Disponibilidade de recebíveis:** se o valor disponível for insuficiente para executar um Split, a operação deverá ser recusada conforme a regra definida para o sistema.

* **RN02 — Antecedência:** o Split deve respeitar o prazo mínimo de agendamento estabelecido para a operação.
 