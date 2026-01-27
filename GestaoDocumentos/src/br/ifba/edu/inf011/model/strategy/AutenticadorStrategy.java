package br.ifba.edu.inf011.model.strategy;

import br.ifba.edu.inf011.model.documentos.Documento;

public interface AutenticadorStrategy {
    String gerarNumero(Documento documento);
}
