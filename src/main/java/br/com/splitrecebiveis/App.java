package br.com.splitrecebiveis;
 
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.ui.ConsoleUI;
 
public class App {
 
    public static void main(String[] args) {
 
        BeneficiarioRepositoryMemoria repository =
                new BeneficiarioRepositoryMemoria();
 
        BeneficiarioService service =
                new BeneficiarioService(repository);
 
        ConsoleUI consoleUI = new ConsoleUI(service);
 
        consoleUI.iniciar();
    }
}