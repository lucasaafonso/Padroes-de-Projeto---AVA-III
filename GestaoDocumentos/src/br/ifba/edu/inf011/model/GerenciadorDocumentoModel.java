package br.ifba.edu.inf011.model;

import java.util.ArrayList;
import java.util.List;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.command.AssinarDocumentoCommand;
import br.ifba.edu.inf011.model.command.Command;
import br.ifba.edu.inf011.model.command.CommandManager;
import br.ifba.edu.inf011.model.command.EditarDocumentoCommand;
import br.ifba.edu.inf011.model.command.MacroCommand;
import br.ifba.edu.inf011.model.command.ProtegerDocumentoCommand;
import br.ifba.edu.inf011.model.command.TornarUrgenteCommand;
import br.ifba.edu.inf011.model.documentos.AbstractDocumentoBase;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.model.strategy.AutenticadorStrategy;

public class GerenciadorDocumentoModel {
    private CommandManager commandManager = new CommandManager();
	private List<Documento> repositorio;
    private DocumentOperatorFactory factory;
    private Autenticador autenticador;
    private GestorDocumento gestor;
    private Documento atual;

    public GerenciadorDocumentoModel(DocumentOperatorFactory factory) {
        this.repositorio = new ArrayList<>();
        this.factory = factory;
        this.autenticador = new Autenticador();
        this.gestor = new GestorDocumento();
        this.atual = null;
    }

    public Documento criarDocumento(AutenticadorStrategy autenticadorStrategy, Privacidade privacidade) throws FWDocumentException {
        Operador operador = factory.getOperador();
        Documento documento = factory.getDocumento();
        
        operador.inicializar("jdc", "João das Couves");
        documento.inicializar(operador, privacidade);
        
        this.autenticador.setAutenticadorStrategy(autenticadorStrategy);
        this.autenticador.autenticar(documento);

        this.repositorio.add(documento);
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

        AssinarDocumentoCommand command = new AssinarDocumentoCommand((AbstractDocumentoBase)documento, gestor, operador);
        commandManager.executar(command);
        Documento assinado = command.getNovoDocumento();
        this.atualizarRepositorio(documento, assinado);
        this.atual = assinado;
        return atual;
    }    
    
    public Documento protegerDocumento(Documento documento) throws FWDocumentException {
        if (documento == null) 
            return null;

        ProtegerDocumentoCommand command = new ProtegerDocumentoCommand((AbstractDocumentoBase)documento, gestor);
        commandManager.executar(command);
        Documento protegido = command.getNovoDocumento();
        this.atualizarRepositorio(documento, protegido);
        this.atual = protegido;
        return atual;        
    }    
    
    public Documento tornarUrgente(Documento documento) throws FWDocumentException {
        if (documento == null) 
            return null;

        TornarUrgenteCommand command = new TornarUrgenteCommand((AbstractDocumentoBase)documento, gestor);
        commandManager.executar(command);
        Documento urgente = command.getNovoDocumento();
        this.atualizarRepositorio(documento, urgente);
        this.atual = urgente;
        return atual;         
    }

    public Documento acaoRapidaAlterarEAssinar(Documento documento, String conteudo) throws FWDocumentException{
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");

        Command macro = new MacroCommand(List.of(
                new EditarDocumentoCommand((AbstractDocumentoBase) documento, conteudo),
                new AssinarDocumentoCommand((AbstractDocumentoBase) documento, gestor, operador)
        ));
        
        commandManager.executar(macro);
        this.atual = documento;
        return atual;
    }

    public Documento acaoRapidaPriorizar(Documento documento) throws FWDocumentException{
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");

        Command macro = new MacroCommand(List.of(
                new TornarUrgenteCommand((AbstractDocumentoBase) documento, gestor),
                new AssinarDocumentoCommand((AbstractDocumentoBase) documento, gestor, operador)
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
	
	public void setDocumentoAtual(Documento doc) {
		this.atual = doc;
	}      
}