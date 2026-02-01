package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;

public class AssinarDocumentoCommand implements Command{
    private Documento documento;
    private Documento documentoAntigo;
    private Documento documentoNovo;
    private GestorDocumento gestor;
    private Operador operador;
    private GerenciadorDocumentoModel model;

    public AssinarDocumentoCommand (Documento documento, GestorDocumento gestor, Operador operador, GerenciadorDocumentoModel model) {
        this.documento = documento;
        this.gestor = gestor;
        this.operador = operador;
        this.model = model;
    }

    @Override
    public void execute() {
        Documento alvo = (this.documento != null) ? this.documento : this.model.getDocumentoAtual();
        if (alvo == null) return;
        documentoAntigo = alvo;
        documentoNovo = gestor.assinar(alvo, operador);
        model.atualizarRepositorio(documentoAntigo, documentoNovo);
        model.setDocumentoAtual(documentoNovo);
        DocumentoLogger.log("Assinar Documento");
    }

    @Override
    public void undo() {
        model.atualizarRepositorio(documentoNovo, documentoAntigo);
        model.setDocumentoAtual(documentoAntigo);
        DocumentoLogger.log("Undo Assinar Documento");
    }

    public Documento getNovo() {
        return documentoNovo;
    }
}
