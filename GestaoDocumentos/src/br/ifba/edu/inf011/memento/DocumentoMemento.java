package br.ifba.edu.inf011.memento;

import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;

public class DocumentoMemento {
    private final String numero;
    private final String conteudo;
    private final Privacidade privacidade;
    private final Operador proprietario;

    public DocumentoMemento(String numero, String conteudo, Privacidade privacidade, Operador proprietario) {
        this.numero = numero;
        this.conteudo = conteudo;
        this.privacidade = privacidade;
        this.proprietario = proprietario;
    }

    public String getNumero() {
        return numero;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Privacidade getPrivacidade() {
        return privacidade;
    }

    public Operador getProprietario() {
        return proprietario;
    }
}
