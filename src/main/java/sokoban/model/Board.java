package sokoban.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Board {
    private final Grid grid ;
    private final BooleanProperty isModified = new SimpleBooleanProperty(false);
    public Board() {
        this(Grid.GRID_WIDTH, Grid.GRID_HEIGHT);
    }
    public Board(int width, int height) {
        this.grid = new Grid(width, height);
    }
    public Grid getGrid() {
        return grid;
    }
    public boolean isModified() {
        return isModified.get();
    }

    public BooleanProperty isModifiedProperty() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified.set(modified);
    }
}
