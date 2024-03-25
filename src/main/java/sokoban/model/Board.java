package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyListProperty;

public class Board {
    private int maxFilledCells;

    private final Grid grid;

    private final BooleanBinding isFull;

    public Board() {
        grid = new Grid();
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    public ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }

    public void play(int line, int col, Element newElem) {
        if (!isFull() || (newElem instanceof Ground))
            grid.play(line, col, newElem);
        if (newElem instanceof Player) {
            if (Player.playerIsSet() || !isFull()) {
                grid.play(line, col, newElem);
            }
        }
    }

    public boolean isFull() {
        return isFull.get();
    }
    public boolean isEmpty(int line, int col) {
        return grid.isEmpty(line,col);
    }

    public int maxFilledCells() {
        return this.maxFilledCells;
    }

    public LongBinding filledCellsCountProperty(){
        return grid.filledCellsCountProperty();
    }
    public Grid getGrid(){
        return this.grid;
    }
}
