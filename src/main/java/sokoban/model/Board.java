package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Board {
    private int maxFilledCells;
    private BooleanBinding characterMissed;
    private BooleanBinding targetMissed;
    private  BooleanBinding boxMissed;
    private BooleanBinding sameNumberOfBoxAndTarget;



    private final Grid grid;

    private final BooleanBinding isFull;

    public Board() {
        grid = new Grid();
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
        characterMissed = grid.checkIfContain(Player.class);
        boxMissed = grid.checkIfContain(Box.class);
        targetMissed = grid.checkIfContain(Target.class);
        sameNumberOfBoxAndTarget = grid.isSameNumberBoxAndTarget();
    }

    public void play(int line, int col, Element newElem) {
        if (!isFull() || (newElem instanceof Ground) )
            grid.play(line, col, newElem);
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

    public BooleanBinding isCharacterMissed(){
        System.out.println("check Model " + characterMissed.get());
        return characterMissed ;
    }
    public BooleanBinding isTargetMissed(){
        return targetMissed;
    } public BooleanBinding isBoxMissed(){
        return boxMissed;
    } public BooleanBinding isSameNumberOfBoxAndTarget(){
        return sameNumberOfBoxAndTarget;
    }
}
