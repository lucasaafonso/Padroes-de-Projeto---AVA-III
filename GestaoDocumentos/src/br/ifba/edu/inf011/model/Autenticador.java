package br.ifba.edu.inf011.model;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.strategy.AutenticadorStrategy;

public class Autenticador {
	private AutenticadorStrategy autenticadorStrategy;

	public void setAutenticadorStrategy(AutenticadorStrategy autenticadorStrategy) {
		this.autenticadorStrategy = autenticadorStrategy;
	}

	public void autenticar(Documento documento) {
		String numero = autenticadorStrategy.gerarNumero(documento);
		documento.setNumero(numero);
	}
}