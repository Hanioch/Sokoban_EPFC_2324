package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;

public class MushroomManager {
    private BooleanBinding mushroomVisible;
    private MushroomCommand c;
    public MushroomManager() {
        mushroomVisible = Bindings.createBooleanBinding(()-> false);
    }
    public void executeCommand(MushroomCommand c ){
        this.c = c;
        showMushroom(true);
    }
    public BooleanBinding isMushroomShowing(){
        return mushroomVisible;
    }
    public boolean clicOnMushroom(){
        //faire les move aleatoire je crois
        return false;
    }

    private void showMushroom(boolean isFromExecuteCommand){
        c.showMushroom();
        if (isFromExecuteCommand) mushroomVisible = Bindings.createBooleanBinding(()->false);
        else mushroomVisible = mushroomVisible.not();
        mushroomVisible.invalidate();

    }
    public void showMushroom(){
       showMushroom(false);
    }

}
