package sokoban.model;

public class CommandManager {
    private Command lastCommand;
    public CommandManager() {}
    public boolean executeCommand(Command c) {
        c.execute();
        lastCommand = c;
        return true;
    }
    public boolean isUndoAvailable() {
        return lastCommand != null;
    }
    public void undo() {
        lastCommand.undo();
    }
    public void redo(){

    }
    public void showMushroom(){

    }
}
