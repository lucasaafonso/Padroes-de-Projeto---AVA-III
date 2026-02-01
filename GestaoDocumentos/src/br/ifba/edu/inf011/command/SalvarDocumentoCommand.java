package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class SalvarDocumentoCommand implements Command {
    private GerenciadorDocumentoModel model;
    private Documento documento;
    private String conteudoAntigo;
    private String novoConteudo;

    public SalvarDocumentoCommand(GerenciadorDocumentoModel model) {
        this.model = model;
    }

    public SalvarDocumentoCommand(Documento documento, String novoConteudo) {
        this.documento = documento;
        this.novoConteudo = novoConteudo;
    }

    @Override
    public void execute() {
        if (this.documento == null && this.model != null) {
            this.documento = this.model.getDocumentoAtual();
        }
        if (this.documento == null) return;

        try {
            conteudoAntigo = documento.getConteudo();
        } catch (FWDocumentException e) {
            conteudoAntigo = null;
        }

        if (this.novoConteudo == null) {
            try {
                this.novoConteudo = documento.getConteudo();
            } catch (FWDocumentException e) {
                this.novoConteudo = null;
            }
        }

        if (this.novoConteudo != null) {
            documento.setConteudo(this.novoConteudo);
        }

        DocumentoLogger.log("Salvar Documento");
    }

    @Override
    public void undo() {
        if (this.documento == null) return;
        this.documento.setConteudo(conteudoAntigo);
        DocumentoLogger.log("Undo Salvar Documento");
    }
}