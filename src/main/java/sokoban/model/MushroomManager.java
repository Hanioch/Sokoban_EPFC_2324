package sokoban.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Stack;

public class MushroomManager {
    private BooleanProperty mushroomVisible;
    private MushroomCommand c;

    private Stack<MushroomCommand> undos = new Stack<MushroomCommand>();
    private Stack<MushroomCommand> redos = new Stack<MushroomCommand>();
    public MushroomManager() {
        mushroomVisible = new SimpleBooleanProperty(false);
    }
    public void executeCommand(MushroomCommand c ){
        this.c = c;
        showMushroom(true);
    }
    public BooleanProperty isMushroomShowing(){
        return mushroomVisible;
    }
    public void clicOnMushroom(int x, int y){
        if (!mushroomVisible.get())
            c.clicOnMushroom(x,y);
    }

    private void showMushroom(boolean isFromExecuteCommand){
        c.showMushroom();
        if (isFromExecuteCommand) mushroomVisible.set(false);
        else mushroomVisible.set(!mushroomVisible.get());

    }
    public void showMushroom(){
       showMushroom(false);
    }
    public boolean isUndoAvailable() {
        return !undos.empty();
    }
    public boolean isRedoAvailable() {
        return !redos.empty();
    }
    public boolean undo() {
        if (isUndoAvailable()) {
            MushroomCommand command = undos.pop();
            command.undo();
            redos.push(command);
            return true;
        }
        return false;
    }
    public boolean redo(){
        if(isRedoAvailable()) {
            MushroomCommand command = redos.pop();
            command.execute();
            undos.push(command);
            return true;
        }
        return false;
    }

}
