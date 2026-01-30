package br.ifba.edu.inf011.command;

import java.util.Stack;

import br.ifba.edu.inf011.model.DocumentoLogger;

public class CommandManager {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void executar(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        DocumentoLogger.log("Executado: " + command.getClass().getSimpleName());
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            DocumentoLogger.log("Undo: " + command.getClass().getSimpleName());
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
            DocumentoLogger.log("Redo: " + command.getClass().getSimpleName());
        }
    }

    public void consolidar() {
        undoStack.clear();
        redoStack.clear();
        DocumentoLogger.log("Consolidado - histórico limpo");
    }
}
