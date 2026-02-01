package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;

public class EditarDocumentoCommand implements Command{
    private AbstractDocumentoBase documento;
    private String conteudoAntigo;
    private String novoConteudo;

    public EditarDocumentoCommand(AbstractDocumentoBase documento, String novoConteudo) {
        this.documento = documento;
        this.novoConteudo = novoConteudo;
    }

    @Override
    public void execute() {
        try {
            conteudoAntigo = documento.getConteudo();
        } catch (FWDocumentException e) {
            conteudoAntigo = null;
        }
        documento.setConteudo(novoConteudo);
        DocumentoLogger.log("Editar Documento");
    }

    @Override
    public void undo() {
        documento.setConteudo(conteudoAntigo);
        DocumentoLogger.log("Undo Editar Documento");
    }
}
