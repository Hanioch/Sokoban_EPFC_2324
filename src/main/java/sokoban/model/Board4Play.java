package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Board4Play extends Board{
    private Grid4Play grid;

    private BooleanBinding isFull;
    private BooleanProperty isModifiedProperty = new SimpleBooleanProperty(false);

    public BooleanProperty isModifiedProperty() {
        return isModifiedProperty;
    }

    public void setModified(boolean isModified) {
        this.isModifiedProperty.set(isModified);
    }
    public Board4Play(int width, int height) {
        grid = new Grid4Play(width, height);
    }
    public Grid4Play getGrid(){
        return this.grid;
    }
}