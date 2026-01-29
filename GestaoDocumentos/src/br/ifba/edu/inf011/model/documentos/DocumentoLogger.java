package br.ifba.edu.inf011.model.documentos;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class DocumentoLogger {
    private static final String ARQUIVO = "log_operacoes.txt";

    public static void log(String operacao) {
        try (FileWriter fw = new FileWriter(ARQUIVO, true)) {
            fw.write(LocalDateTime.now() + " - " + operacao + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
