package br.ifba.edu.inf011.factory;

import br.ifba.edu.inf011.strategy.AutenticadorCriminalStrategy;
import br.ifba.edu.inf011.strategy.AutenticadorPadraoStrategy;
import br.ifba.edu.inf011.strategy.AutenticadorPessoalStrategy;
import br.ifba.edu.inf011.strategy.AutenticadorPrivacidadeStrategy;
import br.ifba.edu.inf011.strategy.AutenticadorStrategy;

public class AutenticadorFactory {
    public static AutenticadorStrategy criarAutenticador(int tipo) {
        switch (tipo) {
            case 0: return new AutenticadorCriminalStrategy();
            case 1: return new AutenticadorPessoalStrategy();
            case 2: return new AutenticadorPrivacidadeStrategy();
            default: return new AutenticadorPadraoStrategy();
        }
    }
}
