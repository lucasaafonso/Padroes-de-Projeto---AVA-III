package br.ifba.edu.inf011.observer;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileLogObserver implements LogObserver {
    private static final String ARQUIVO = "log_operacoes.txt";

    @Override
    public void notificar(String mensagem) {
        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {
            fw.write(LocalDateTime.now() + " - " + mensagem + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
