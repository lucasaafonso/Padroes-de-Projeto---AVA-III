package br.ifba.edu.inf011.model.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.memento.DocumentoMemento;

public class ProtegerDocumentoCommand implements Command{
    private AbstractDocumentoBase documento;
    private GestorDocumento gestor;
    private DocumentoMemento backup;

    public ProtegerDocumentoCommand(AbstractDocumentoBase documento, GestorDocumento gestor) {
        this.documento = documento;
        this.gestor = gestor;
    }

    @Override
    public void execute() {
        backup = documento.salvar();
        gestor.proteger(documento);
        DocumentoLogger.log("Documento protegido");
    }

    @Override
    public void undo() {
        documento.restaurar(backup);
        DocumentoLogger.log("Undo ProtegerDocumento");
    }

}
