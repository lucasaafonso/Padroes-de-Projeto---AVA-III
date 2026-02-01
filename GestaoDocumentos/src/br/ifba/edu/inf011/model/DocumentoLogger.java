package br.ifba.edu.inf011.model;

import java.util.ArrayList;
import java.util.List;

import br.ifba.edu.inf011.observer.LogObserver;

public class DocumentoLogger {
    private static final DocumentoLogger instance = new DocumentoLogger();
    private List<LogObserver> observadores = new ArrayList<>();

    private DocumentoLogger() {
    }

    public static DocumentoLogger getInstance() {
        return instance;
    }

    public static void log(String operacao) {
        instance.notificar(operacao);
    }

    public void adicionarObservador(LogObserver observador) {
        observadores.add(observador);
    }

    public void removerObservador(LogObserver observador) {
        observadores.remove(observador);
    }

    private void notificar(String mensagem) {
        for (LogObserver observador : observadores) {
            observador.notificar(mensagem);
        }
    }
}
