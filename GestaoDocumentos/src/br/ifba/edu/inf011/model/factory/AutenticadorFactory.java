package br.ifba.edu.inf011.model.factory;

import br.ifba.edu.inf011.model.strategy.AutenticadorCriminalStrategy;
import br.ifba.edu.inf011.model.strategy.AutenticadorPadraoStrategy;
import br.ifba.edu.inf011.model.strategy.AutenticadorPessoalStrategy;
import br.ifba.edu.inf011.model.strategy.AutenticadorPrivacidadeStrategy;
import br.ifba.edu.inf011.model.strategy.AutenticadorStrategy;

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
