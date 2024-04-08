package sokoban.model;

import java.util.Stack;

public class CommandManager {

    private Stack<Command> undos = new Stack<Command>();
    private Stack<Command> redos = new Stack<Command>();
    public CommandManager() {}
    public boolean executeCommand(Command c) {
        c.execute();
        undos.push(c);
        redos.clear();
        return true;
    }
    public boolean isUndoAvailable() {
        return !undos.empty();
    }
    public boolean isRedoAvailable() {
        return !redos.empty();
    }
    public void undo() {
        if (isUndoAvailable()) {
            Command command = undos.pop();
            command.undo();
            redos.push(command);
        }
    }
    public void redo(){
        if(isRedoAvailable()) {
            Command command = redos.pop();
            command.execute();
            undos.push(command);
        }
    }
    public void clearUndos() {
        undos.clear();
    }
    public void clearRedos() {
        redos.clear();
    }
    public void showMushroom(){

    }
}
