package sokoban.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Board {
    private final Grid grid = new Grid();
    private final BooleanProperty isModified = new SimpleBooleanProperty(false);
    public Board() {

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
