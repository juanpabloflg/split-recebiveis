package br.com.splitrecebiveis;
 
import br.com.splitrecebiveis.domain.Beneficiario;
import br.com.splitrecebiveis.domain.TipoPessoa;
 
public class App {
 
    public static void main(String[] args) {
 
        try {
            // Cria um beneficiário diretamente, sem usar o serviço
            // ou qualquer funcionalidade da pessoa 2.
            Beneficiario beneficiario = new Beneficiario(
                    1,
                    "João Silva",
                    TipoPessoa.PF,
                    "12345678900",
                    "Banco do Brasil",
                    "1234",
                    "56789"
            );
 
            // Exibe os dados do objeto criado.
            System.out.println("Beneficiário criado com sucesso!");
            System.out.println(beneficiario);
 
        } catch (IllegalArgumentException e) {
            // Exibe erros de validação do construtor.
            System.out.println("Erro: " + e.getMessage());
        }
    }
}