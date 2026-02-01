package br.ifba.edu.inf011.model;

import java.util.ArrayList;
import java.util.List;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.command.AssinarDocumentoCommand;
import br.ifba.edu.inf011.command.Command;
import br.ifba.edu.inf011.command.CommandManager;
import br.ifba.edu.inf011.command.CriarDocumentoCommand;
import br.ifba.edu.inf011.command.EditarDocumentoCommand;
import br.ifba.edu.inf011.command.MacroCommand;
import br.ifba.edu.inf011.command.ProtegerDocumentoCommand;
import br.ifba.edu.inf011.command.TornarUrgenteCommand;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.strategy.AutenticadorStrategy;

public class GerenciadorDocumentoModel {
    private CommandManager commandManager;
	private List<Documento> repositorio;
    private DocumentOperatorFactory factory;
    private Autenticador autenticador;
    private GestorDocumento gestor;
    private Documento atual;

    public GerenciadorDocumentoModel(DocumentOperatorFactory factory) {
        this.commandManager = new CommandManager();
        this.repositorio = new ArrayList<>();
        this.factory = factory;
        this.autenticador = new Autenticador();
        this.gestor = new GestorDocumento();
        this.atual = null;
    }

    public Documento criarDocumento(AutenticadorStrategy autenticadorStrategy, Privacidade privacidade) throws FWDocumentException {
        CriarDocumentoCommand command = new CriarDocumentoCommand(factory, autenticador, autenticadorStrategy, privacidade, repositorio);
        commandManager.executar(command);
        Documento documento = command.getDocumentoCriado();
        this.atual = documento;
        return documento;
    }

    public void salvarDocumento(Documento documento, String conteudo) throws Exception {
        EditarDocumentoCommand command = new EditarDocumentoCommand((AbstractDocumentoBase) documento, conteudo);
        commandManager.executar(command);
        this.atual = documento;
    }
    
    public Documento assinarDocumento(Documento documento) throws FWDocumentException {
        if (documento == null) 
            return null;

        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");

        AssinarDocumentoCommand command = new AssinarDocumentoCommand((AbstractDocumentoBase)documento, gestor, operador, this);
        commandManager.executar(command);
        Documento novo = command.getNovo();
        this.atual = novo;
        return atual;
    }
    
    public Documento protegerDocumento(Documento documento) throws FWDocumentException {
        if (documento == null) 
            return null;

        ProtegerDocumentoCommand command = new ProtegerDocumentoCommand((AbstractDocumentoBase)documento, gestor, this);
        commandManager.executar(command);
        Documento novo = command.getNovo();
        this.atual = novo;
        return atual;        
    }    

    public Documento tornarUrgente(Documento documento) throws FWDocumentException {
        if (documento == null) 
            return null;

        TornarUrgenteCommand command = new TornarUrgenteCommand((AbstractDocumentoBase)documento, gestor, this);
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");
        
        commandManager.executar(command);
        this.atual = documento;
        return atual;
    }

    public Documento acaoRapidaPriorizar(Documento documento) throws FWDocumentException{
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");

        Command macro = new MacroCommand(List.of(
                new TornarUrgenteCommand((AbstractDocumentoBase) documento, gestor, this),
                new AssinarDocumentoCommand((AbstractDocumentoBase) documento, gestor, operador, this)
        ));

        commandManager.executar(macro);
        this.atual = documento;
        return atual;
    }

    public void desfazer() {
        commandManager.undo();
    }

    public void refazer() {
        commandManager.redo();
    }

    public void consolidar() {
        commandManager.consolidar();
    }
    
    public void atualizarRepositorio(Documento antigo, Documento novo) {
        int index = repositorio.indexOf(antigo);
        if (index != -1) {
            repositorio.set(index, novo);
        }
    } 

    public List<Documento> getRepositorio() {
        return repositorio;
    }
    
	public Documento getDocumentoAtual() {
		return this.atual;
	}
	
	public void setDocumentoAtual(Documento documento) {
		this.atual = documento;
	}      
}