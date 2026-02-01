package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class ProtegerDocumentoCommand implements Command{
    private Documento documento;
    private Documento documentoAntigo;
    private Documento documentoNovo;
    private GestorDocumento gestor;
    private GerenciadorDocumentoModel model;

    public ProtegerDocumentoCommand(Documento documento, GestorDocumento gestor, GerenciadorDocumentoModel model) {
        this.documento = documento;
        this.gestor = gestor;
        this.model = model;
    }

    @Override
    public void execute() {
        Documento alvo = (this.documento != null) ? this.documento : this.model.getDocumentoAtual();
        if (alvo == null) return;
        documentoAntigo = alvo;
        documentoNovo = gestor.proteger(alvo);
        model.atualizarRepositorio(documentoAntigo, documentoNovo);
        model.setDocumentoAtual(documentoNovo);
        DocumentoLogger.log("Documento protegido");
    }

    @Override
    public void undo() {
        model.atualizarRepositorio(documentoNovo, documentoAntigo);
        model.setDocumentoAtual(documentoAntigo);
        DocumentoLogger.log("Undo ProtegerDocumento");
    }

    public Documento getNovo() {
        return documentoNovo;
    }
}
