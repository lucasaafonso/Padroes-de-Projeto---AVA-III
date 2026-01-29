package br.ifba.edu.inf011.model.memento;

import java.util.Stack;

import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;

public class HistoricoDocumento {
    private Stack<DocumentoMemento> historico = new Stack<>();

    public void salvar(AbstractDocumentoBase documento) {
        historico.push(documento.salvar());
    }

    public void desfazer(AbstractDocumentoBase documento) {
        if (!historico.isEmpty()) {
            documento.restaurar(historico.pop());
        }
    }
}
