package br.ifba.edu.inf011.strategy;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;

public class AutenticadorCriminalStrategy implements AutenticadorStrategy{
    public String gerarNumero(Documento doc) {
        return "CRI-" + LocalDate.now().getYear() + "-" + doc.hashCode();
    }
}
