package sokoban.model;

import java.util.Stack;

public class CommandManager {

    private Stack<Command> undos = new Stack<Command>();
    private Stack<Command> redos = new Stack<Command>();
    private Command lastCommand;
    private Command lastCommandUndone;
    public CommandManager() {}
    public boolean executeCommand(Command c) {
        c.execute();
        lastCommand = c;
        lastCommandUndone = null;
        return true;
    }
    public boolean isUndoAvailable() {
        return lastCommand != null;
    }
    public boolean isRedoAvailable() {
        return lastCommandUndone != null;
    }
    public void undo() {
        if (isUndoAvailable()) {
            lastCommand.undo();
            lastCommandUndone = lastCommand;
            lastCommand = null;
        }
    }
    public void redo(){
        if(isRedoAvailable()) {
            lastCommandUndone.execute();
            lastCommand = lastCommandUndone;
            lastCommandUndone = null;
        }
    }
    public void showMushroom(){

    }
}
