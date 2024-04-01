package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Board4Design extends Board {
    private int maxFilledCells;

    private Grid4Design grid4Design;

    private  BooleanBinding isFull;
    private  BooleanProperty isModifiedProperty = new SimpleBooleanProperty(false);

    public BooleanProperty isModifiedProperty() {
        return isModifiedProperty;
    }

    public void setModified(boolean isModified) {
        this.isModifiedProperty.set(isModified);
    }
    public Board4Design() {
        grid4Design = new Grid4Design(15,10);
        maxFilledCells = grid4Design.getArea()/2;
        isFull = grid4Design.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    public ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return grid4Design.valueProperty(line, col);
    }

    public void play(int line, int col, Element newElem) {

        String type = newElem.getClass().getSimpleName();

        // peut jouer si le Grid n'est pas rempli || si la cellule visée est déjà remplie || si on place un Ground
        // || si on place un Player et qu'il est déjà placé ailleurs sur une case où il n'est pas avec une Target

        if(!isFull() || !grid4Design.getCell(line, col).isEmpty() || type.equals("Ground")
                || (type.equals("Player") && grid4Design.playerIsSet() && grid4Design.playerIsAlone())) {
            grid4Design.play(line, col, newElem);
            setModified(true);
        }
        grid4Design.invalidateBinding();
    }
    public void setGrid(Grid4Design newGrid4Design) {
        this.grid4Design = newGrid4Design;
        this.maxFilledCells = this.grid4Design.getArea() / 2;
        this.isFull = grid4Design.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }
    public void resetGrid(int width, int height) {
        grid4Design.filledCellsCount.invalidate();
        this.grid4Design = new Grid4Design(width, height);
        this.isModifiedProperty.set(false);
        this.maxFilledCells = this.grid4Design.getArea() / 2;
        this.isFull = grid4Design.filledCellsCountProperty().isEqualTo(maxFilledCells);
        grid4Design.setHeight(height);
        grid4Design.setWidth(width);

    }
    public boolean isFull() {
        return isFull.get();
    }
    public boolean isEmpty(int line, int col) {
        return grid4Design.isEmpty(line,col);
    }

    public int maxFilledCells() {
        return this.maxFilledCells;
    }

    public LongBinding filledCellsCountProperty(){
        return grid4Design.filledCellsCountProperty();
    }
    public BooleanBinding isCharMissed(){
        return grid4Design.isCharacterMissedProperty();
    }
    public BooleanBinding isBoxMissed(){
        return grid4Design.isBoxMissedProperty();
    }
    public BooleanBinding isTargetMissed(){
        return grid4Design.isTargetMissedProperty();
    }
    public BooleanBinding isSameNumberOfBoxAndTarget(){
        return grid4Design.isSameNumberOfBoxAndTargetProperty();
    }
    public BooleanBinding isAnError(){
        return grid4Design.IsAnErrorProperty();
    }
    public Grid4Design getGrid(){
        return this.grid4Design;
    }
}

