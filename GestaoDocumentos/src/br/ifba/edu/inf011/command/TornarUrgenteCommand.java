package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.GestorDocumento;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.documentos.Documento;

public class TornarUrgenteCommand implements Command{
    private AbstractDocumentoBase documento;
    private AbstractDocumentoBase documentoAntigo;
    private Documento documentoNovo;
    private GestorDocumento gestor;
    private GerenciadorDocumentoModel model;

    public TornarUrgenteCommand(AbstractDocumentoBase documento, GestorDocumento gestor, GerenciadorDocumentoModel model) {
        this.documento = documento;
        this.gestor = gestor;
        this.model = model;
    }

    @Override
    public void execute() {
        documentoAntigo = documento;
        documentoNovo = gestor.tornarUrgente(documento);
        model.atualizarRepositorio(documentoAntigo, documentoNovo);
        model.setDocumentoAtual(documentoNovo);
        DocumentoLogger.log("Documento marcado como urgente");
    }

    @Override
    public void undo() {
        model.atualizarRepositorio(documentoNovo, documentoAntigo);
        model.setDocumentoAtual(documentoAntigo);
        DocumentoLogger.log("Undo Tornar Urgente");
    }

    public Documento getNovo() {
        return documentoNovo;
    }
}
