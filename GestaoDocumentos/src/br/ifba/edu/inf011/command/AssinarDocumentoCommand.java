package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.memento.DocumentoMemento;
import br.ifba.edu.inf011.model.operador.Operador;

public class AssinarDocumentoCommand implements Command{
    private AbstractDocumentoBase documento;
    private DocumentoMemento backup;
    private GestorDocumento gestor;
    private Operador operador;
    private Documento novoDocumento;

    public AssinarDocumentoCommand (AbstractDocumentoBase documento, GestorDocumento gestor, Operador operador) {
        this.documento = documento;
        this.gestor = gestor;
        this.operador = operador;
    }

    @Override
    public void execute() {
        backup = documento.salvar();
        novoDocumento = gestor.assinar(documento, operador);
        DocumentoLogger.log("Assinar Documento");
    }

    @Override
    public void undo() {
        documento.restaurar(backup);
        DocumentoLogger.log("Undo Assinar Documnto");
    }

    public Documento getNovoDocumento() {
        return novoDocumento;
    }
}
