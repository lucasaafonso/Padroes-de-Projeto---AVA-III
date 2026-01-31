package br.ifba.edu.inf011.command;

import java.util.List;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.Autenticador;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.strategy.AutenticadorStrategy;

public class CriarDocumentoCommand implements Command{
    private DocumentOperatorFactory factory;
    private Autenticador autenticador;
    private AutenticadorStrategy strategy;
    private Privacidade privacidade;
    private Documento criado;
    private Operador operador;
    private List<Documento> repositorio;

    public CriarDocumentoCommand(DocumentOperatorFactory factory, Autenticador autenticador, AutenticadorStrategy strategy, Privacidade privacidade, List<Documento> repositorio) {
        this.factory = factory;
        this.autenticador = autenticador;
        this.strategy = strategy;
        this.privacidade = privacidade;
        this.repositorio = repositorio;
    }

     @Override
    public void execute() {
        try {
            operador = factory.getOperador();
        } catch (FWDocumentException e) {
            e.printStackTrace();
        }
        try {
            criado = factory.getDocumento();
        } catch (FWDocumentException e) {
            e.printStackTrace();
        }

        if (criado != null) {
            operador.inicializar("jdc", "João das Couves");
            try {
            criado.inicializar(operador, privacidade);
            } catch (FWDocumentException e) {
            e.printStackTrace();
            }
            autenticador.setAutenticadorStrategy(strategy);
            autenticador.autenticar(criado);
            this.repositorio.add(criado);
        }
    }

    @Override
    public void undo() {
        repositorio.remove(criado);
    }

    public Documento getDocumentoCriado() {
        return criado;
    }
}
