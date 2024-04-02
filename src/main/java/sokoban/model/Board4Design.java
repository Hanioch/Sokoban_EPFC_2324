package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Board4Design extends Board {
    private Grid4Design grid;
    private int maxFilledCells;

    private BooleanBinding isFull;
    private BooleanProperty isModifiedProperty = new SimpleBooleanProperty(false);

    public BooleanProperty isModifiedProperty() {
        return isModifiedProperty;
    }

    public void setModified(boolean isModified) {
        this.isModifiedProperty.set(isModified);
    }
    public Board4Design() {
        grid = new Grid4Design(15,10);
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    public ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }

    public void play(int line, int col, Element newElem) {

        String type = newElem.getClass().getSimpleName();

        // peut jouer si le Grid n'est pas rempli || si la cellule visée est déjà remplie || si on place un Ground
        // || si on place un Player et qu'il est déjà placé ailleurs sur une case où il n'est pas avec une Target

        if(!isFull() || !grid.getCell(line, col).isEmpty() || type.equals("Ground")
                || (type.equals("Player") && grid.playerIsSet() && grid.playerIsAlone())) {
            grid.play(line, col, newElem);
            setModified(true);
        }
        grid.invalidateBinding();
    }
    public void setGrid(Grid4Design newGrid4Design) {
        this.grid = newGrid4Design;
        this.maxFilledCells = this.grid.getArea() / 2;
        this.isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }
    public void resetGrid(int width, int height) {
        grid.filledCellsCount.invalidate();
        this.grid = new Grid4Design(width, height);
        this.isModifiedProperty.set(false);
        this.maxFilledCells = this.grid.getArea() / 2;
        this.isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
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
    public BooleanBinding isCharMissed(){
        return grid.isCharacterMissedProperty();
    }
    public BooleanBinding isBoxMissed(){
        return grid.isBoxMissedProperty();
    }
    public BooleanBinding isTargetMissed(){
        return grid.isTargetMissedProperty();
    }
    public BooleanBinding isSameNumberOfBoxAndTarget(){
        return grid.isSameNumberOfBoxAndTargetProperty();
    }
    public BooleanBinding isAnError(){
        return grid.IsAnErrorProperty();
    }
    public Grid4Design getGrid(){
        return this.grid;
    }
}

