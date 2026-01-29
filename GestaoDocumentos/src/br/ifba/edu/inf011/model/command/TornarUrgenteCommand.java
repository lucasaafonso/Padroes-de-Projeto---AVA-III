package br.ifba.edu.inf011.model.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.memento.DocumentoMemento;

public class TornarUrgenteCommand implements Command{
    private AbstractDocumentoBase documento;
    private GestorDocumento gestor;
    private DocumentoMemento backup;
    private Documento novoDocumento;

    public TornarUrgenteCommand(AbstractDocumentoBase documento, GestorDocumento gestor) {
        this.documento = documento;
        this.gestor = gestor;
    }

    @Override
    public void execute() {
        backup = documento.salvar();
        novoDocumento = gestor.tornarUrgente(documento);
        DocumentoLogger.log("Documento marcado como urgente");
    }

    @Override
    public void undo() {
        documento.restaurar(backup);
        DocumentoLogger.log("Undo Tornar Urgente");
    }

    public Documento getNovoDocumento() {
        return novoDocumento;
    }
}
