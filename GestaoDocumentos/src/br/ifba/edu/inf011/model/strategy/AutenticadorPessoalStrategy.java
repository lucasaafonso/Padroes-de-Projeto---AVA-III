package br.ifba.edu.inf011.model.strategy;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;

public class AutenticadorPessoalStrategy implements AutenticadorStrategy{
    public String gerarNumero(Documento doc) {
        return "PES-" + LocalDate.now().getDayOfYear() + "-" + doc.getProprietario().hashCode();
    }
}
