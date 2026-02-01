package br.ifba.edu.inf011.ui;

import javax.swing.JOptionPane;

import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.factory.AutenticadorFactory;
import br.ifba.edu.inf011.strategy.AutenticadorStrategy;

public class MyGerenciadorDocumentoUI extends AbstractGerenciadorDocumentosUI{

	 public MyGerenciadorDocumentoUI(DocumentOperatorFactory factory) {
		super(factory);
	}

	protected JPanelOperacoes montarMenuOperacoes() {
		JPanelOperacoes comandos = new JPanelOperacoes();
		comandos.addOperacao("➕ Criar Publico", e -> this.criarDocumentoPublico());
		comandos.addOperacao("➕ Criar Privado", e -> this.criarDocumentoPrivado());
		comandos.addOperacao("💾 Salvar", e-> this.salvarConteudo());
		comandos.addOperacao("🔑 Proteger", e->this.protegerDocumento());
		comandos.addOperacao("✍️ Assinar", e->this.assinarDocumento());
		comandos.addOperacao("⏰ Urgente", e->this.tornarUrgente());
		comandos.addOperacao("✍️💾 Alterar e Assinar", e->this.alterarAssinar());
		comandos.addOperacao("⭐ Priorizar", e->this.priorizar());
		comandos.addOperacao("↩️ Desfazer", e->this.undo());
		comandos.addOperacao("↪️ Refazer", e->this.redo());
		comandos.addOperacao("✅ Consolidar", e->this.consolidar());
		return comandos;
	 }
	
	protected void criarDocumentoPublico() {
		this.criarDocumento(Privacidade.PUBLICO);
	}
	
	protected void criarDocumentoPrivado() {
		this.criarDocumento(Privacidade.SIGILOSO);
	}
	
	protected void salvarConteudo() {
        try {
            this.controller.salvarDocumento(this.atual, this.areaEdicao.getConteudo());
			this.atual = this.controller.getDocumentoAtual();
        	this.refreshUI();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(this, "Erro ao Salvar: " + e.getMessage());
        }
    }	
	
	protected void protegerDocumento() {
		try {
			this.controller.protegerDocumento(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro ao proteger: " + e.getMessage());
		}
	}

	protected void assinarDocumento() {
		try {
			this.controller.assinarDocumento(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro ao assinar: " + e.getMessage());
		}		
	}

	protected void tornarUrgente() {
		try {
			this.controller.tornarUrgente(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro ao tornar urgente: " + e.getMessage());
		}		
	}	

	private void criarDocumento(Privacidade privacidade) {
        try {
            int tipoIndex = this.barraSuperior.getTipoSelecionadoIndice();
			AutenticadorStrategy autenticadorStrategy = AutenticadorFactory.criarAutenticador(tipoIndex);
            this.atual = this.controller.criarDocumento(autenticadorStrategy, privacidade);
            this.barraDocs.addDoc("[" + atual.getNumero() + "]");
            this.refreshUI();
        } catch (FWDocumentException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }	

	protected void alterarAssinar() {
		try {
			this.controller.acaoRapidaAssinarSalvar(atual, this.areaEdicao.getConteudo());
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			
			JOptionPane.showMessageDialog(this, "Erro ao alterar e assinar: " + e.getMessage());
		}
	}

	protected void priorizar() {
		try {
			this.controller.acaoRapidaPriorizar(atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			
			JOptionPane.showMessageDialog(this, "Erro ao priorizar: " + e.getMessage());
		}
	}

	protected void undo() {
		this.controller.desfazer();
		this.atual = this.controller.getDocumentoAtual();
    	this.refreshUI();
	}

	protected void redo() {
		this.controller.refazer();
		this.atual = this.controller.getDocumentoAtual();
		this.refreshUI();
	}

	protected void consolidar() {
		this.controller.consolidar();
		this.atual = this.controller.getDocumentoAtual();
		this.refreshUI();
	}
}