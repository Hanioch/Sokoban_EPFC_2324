package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;

public class Board {
    private int maxFilledCells;



    private final Grid grid;

    private final BooleanBinding isFull;

    public Board() {
        grid = new Grid();
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);

    }

    public void play(int line, int col, Element newElem) {
        if (!isFull() || (newElem instanceof Ground) )
            grid.play(line, col, newElem);
        grid.invalidateBinding();
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
    public Grid getGrid(){
        return this.grid;
    }

    }
