package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.SimpleIntegerProperty;

public class Board {
    private int maxFilledCells;

    private final Grid grid = new Grid();

    private final BooleanBinding isFull;

    public Board(){
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    /*
    public cellValue play(int line,int col) {
     if (grid.getValue(line, col) == CellValue.EMPTY && isFull())
            return CellValue.EMPTY;

        grid.play(line, col, grid.getValue(line, col) == CellValue.EMPTY ? CellValue.X : CellValue.EMPTY);
        return grid.getValue(line, col);
    }
    */
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


    /*
    *     public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }
*/
}
