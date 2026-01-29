package br.ifba.edu.inf011.model.command;

import java.util.List;

public class MacroCommand implements Command{
    private List<Command> comandos;

    public MacroCommand(List<Command> comandos) {
        this.comandos = comandos;
    }

    @Override
    public void execute() {
        for (Command c : comandos) c.execute();
    }

    @Override
    public void undo() {
        for (int i = comandos.size() - 1; i >= 0; i--)
            comandos.get(i).undo();
    }
}
