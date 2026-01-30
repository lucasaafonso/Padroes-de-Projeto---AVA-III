package br.ifba.edu.inf011.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;

public class AutenticadorPadraoStrategy implements AutenticadorStrategy {
    public String gerarNumero(Documento doc) {
        return "DOC-" + System.currentTimeMillis();
    }
}
