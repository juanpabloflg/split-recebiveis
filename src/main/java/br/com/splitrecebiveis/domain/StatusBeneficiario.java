package br.com.splitrecebiveis.domain;
 
/**
* Situação de um beneficiário no sistema.
* Todo beneficiário nasce PENDENTE e só passa a APROVADO pela operação de aprovação.
*/
public enum StatusBeneficiario {
 
    PENDENTE,
    APROVADO;
}