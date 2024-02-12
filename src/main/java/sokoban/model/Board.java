package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;

public class Board {
    private final int maxFilledCells;

    private final Grid grid = new Grid();

    private final BooleanBinding isFull;

    public Board(){
        maxFilledCells = grid.getArea()/2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    /*public cellValue play(int line,int col) {
     if (grid.getValue(line, col) == CellValue.EMPTY && isFull())
            return CellValue.EMPTY;

        grid.play(line, col, grid.getValue(line, col) == CellValue.EMPTY ? CellValue.X : CellValue.EMPTY);
        return grid.getValue(line, col);
}
*/
    public boolean isFull() {
        return isFull.get();
    }

    public LongBinding filledCellsCountProperty(){
        return grid.filledCellsCountProperty();
    }

    public boolean isEmpty(int line, int col) {
        return grid.isEmpty(line,col);
    }

    /*
    *     public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }
*/


}
