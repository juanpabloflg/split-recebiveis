package br.com.splitrecebiveis;

import br.com.splitrecebiveis.infra.BeneficiarioRepositoryMemoria;
import br.com.splitrecebiveis.infra.RecebivelRepositoryMemoria;
import br.com.splitrecebiveis.service.BeneficiarioService;
import br.com.splitrecebiveis.service.RecebivelService;
import br.com.splitrecebiveis.ui.ConsoleUI;

public class App {

    public static void main(String[] args) {

        BeneficiarioRepositoryMemoria beneficiarioRepository =
                new BeneficiarioRepositoryMemoria();

        RecebivelRepositoryMemoria recebivelRepository =
                new RecebivelRepositoryMemoria();

        BeneficiarioService beneficiarioService =
                new BeneficiarioService(beneficiarioRepository);

        RecebivelService recebivelService =
                new RecebivelService(recebivelRepository);

        ConsoleUI consoleUI = new ConsoleUI(beneficiarioService, recebivelService);

        consoleUI.iniciar();
    }
}