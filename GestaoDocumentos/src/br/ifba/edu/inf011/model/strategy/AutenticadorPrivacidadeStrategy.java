package br.ifba.edu.inf011.model.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;

public class AutenticadorPrivacidadeStrategy implements AutenticadorStrategy{
    public String gerarNumero(Documento doc) {
        if (doc.getPrivacidade() == Privacidade.SIGILOSO)
            return "SECURE-" + doc.getNumero().hashCode();
        
        return "PUB-" + doc.hashCode();
    }
}
