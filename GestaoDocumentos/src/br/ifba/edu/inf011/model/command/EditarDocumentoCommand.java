package br.ifba.edu.inf011.model.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.memento.DocumentoMemento;

public class EditarDocumentoCommand implements Command{
    private AbstractDocumentoBase documento;
    private DocumentoMemento backup;
    private String novoConteudo;

    public EditarDocumentoCommand(AbstractDocumentoBase documento, String novoConteudo) {
        this.documento = documento;
        this.novoConteudo = novoConteudo;
    }

    @Override
    public void execute() {
        backup = documento.salvar();
        documento.setConteudo(novoConteudo);
        DocumentoLogger.log("Editar Documento");
    }

    @Override
    public void undo() {
        documento.restaurar(backup);
    }
}
