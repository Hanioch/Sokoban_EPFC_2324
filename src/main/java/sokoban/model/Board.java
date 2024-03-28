package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Board {
    private int maxFilledCells;

    private Grid grid;

    private  BooleanBinding isFull;
    private  BooleanProperty isModifiedProperty = new SimpleBooleanProperty(false);

    public BooleanProperty isModifiedProperty() {
        return isModifiedProperty;
    }

    public void setModified(boolean isModified) {
        this.isModifiedProperty.set(isModified);
    }
    public Board() {
        grid = new Grid(15,10);
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    public ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }

    public void play(int line, int col, Element newElem) {

        if (!isFull() || (newElem instanceof Ground) || (newElem instanceof Player && grid.playerIsSet() && grid.playerIsAlone())
                || (newElem instanceof Target && !grid.getCell(line, col).isEmpty())) {
            grid.play(line, col, newElem);
            setModified(true);
        }
    }
    public void resetGrid(int width, int height) {
        grid.filledCellsCount.invalidate();
        this.grid = new Grid(width, height);
        this.isModifiedProperty.set(false);
        this.maxFilledCells = this.grid.getArea() / 2;
        grid.setHeight(height);
        grid.setWidth(width);

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
