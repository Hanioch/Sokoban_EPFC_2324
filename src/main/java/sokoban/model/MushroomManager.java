package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MushroomManager {
    private BooleanProperty mushroomVisible;
    private MushroomCommand c;
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

}
