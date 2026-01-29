package br.ifba.edu.inf011.model.command;

public interface Command {
    void execute();
    void undo();
}
