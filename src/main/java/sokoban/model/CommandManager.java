package sokoban.model;

import java.util.Stack;

public class CommandManager {

    private Stack<Command> undos = new Stack<Command>();
    private Stack<Command> redos = new Stack<Command>();
    private Stack<MushroomCommand> undosMushroom = new Stack<MushroomCommand>();
    private Stack<MushroomCommand> redosMushroom = new Stack<MushroomCommand>();
    private boolean isMushroomActivated= false;
    public CommandManager() {}
    public boolean executeCommand(Command c) {
        boolean succesful = c.execute();
        if (succesful) {
            undos.push(c);
            redos.clear();
        }
        return succesful;
    }
    public boolean isUndoAvailable() {
        return !undos.empty();
    }
    public boolean isRedoAvailable() {
        return !redos.empty();
    } public boolean isUndoMushroomAvailable() {
        return !undosMushroom.empty();
    }
    public boolean isRedoMushroomAvailable() {
        return !redosMushroom.empty();
    }
    public boolean undo() {
        if (isUndoAvailable()) {
            Command command = undos.pop();

            if (command.isMushroomClicked()) undoMushroom();
            else command.undo();
            redos.push(command);

            return true;
        }

        if (isUndoMushroomAvailable()) undoMushroom();

        return false;
    }

    private void undoMushroom(){
        MushroomCommand command = undosMushroom.pop();
        command.undo();
        redosMushroom.push(command);
    }
    public boolean redo(){
        if(isRedoAvailable()) {
            Command command = redos.pop();

            if (command.isMushroomClicked()) redoMushroom();
            else command.execute();
            undos.push(command);

            return true;
        }

        if (isRedoMushroomAvailable())
            redoMushroom();

        return false;
    }

    private void redoMushroom(){
        MushroomCommand command = redosMushroom.pop();
        command.redo();
        undosMushroom.push(command);
    }

    public void clickedOnMushroom(Command c, MushroomCommand mc){
        undos.push(c);
        undosMushroom.push(mc);
    }

}
