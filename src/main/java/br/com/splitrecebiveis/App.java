package br.com.splitrecebiveis;
 
import java.time.Clock;
 
import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.infra.SplitRepositoryMemoria;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.service.RecebivelService;
import br.com.splitrecebiveis.service.SplitService;
import br.com.splitrecebiveis.ui.ConsoleUI;
 
public class App {
 
    public static void main(String[] args) {
 
        BeneficiarioRepositoryMemoria beneficiarioRepository =
                new BeneficiarioRepositoryMemoria();
 
        RecebivelRepositoryMemoria recebivelRepository =
                new RecebivelRepositoryMemoria();
 
        SplitRepositoryMemoria splitRepository =
                new SplitRepositoryMemoria();
 
        BeneficiarioService beneficiarioService =
                new BeneficiarioService(beneficiarioRepository);
 
        RecebivelService recebivelService =
                new RecebivelService(recebivelRepository);
 
        // O relógio real é usado aqui; nos testes ele é substituído por uma data fixa.
        SplitService splitService =
                new SplitService(splitRepository, beneficiarioService, recebivelService, Clock.systemDefaultZone());
 
        ConsoleUI consoleUI = new ConsoleUI(beneficiarioService, recebivelService, splitService);
 
        consoleUI.iniciar();
    }
}