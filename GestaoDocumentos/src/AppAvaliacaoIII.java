import javax.swing.SwingUtilities;

import br.ifba.edu.inf011.af.CalculoPericialPeritoFactory;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.DocumentoLogger;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.observer.FileLogObserver;
import br.ifba.edu.inf011.ui.MyGerenciadorDocumentoUI;

public class AppAvaliacaoIII {
	public AppAvaliacaoIII() {
	}
	
	public void run(DocumentOperatorFactory factory) throws FWDocumentException {
	    DocumentoLogger.getInstance().adicionarObservador(new FileLogObserver());
	    SwingUtilities.invokeLater(() -> new MyGerenciadorDocumentoUI(new CalculoPericialPeritoFactory()).setVisible(true));
	}

	public static void main(String[] args) throws FWDocumentException {
		new AppAvaliacaoIII().run(null);
	}
}