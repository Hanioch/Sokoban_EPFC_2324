package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyObjectProperty;

public class Board {
    private final Grid grid = new Grid();

    public Board() {

    }
    public Grid getGrid() {
        return grid;
    }
}
