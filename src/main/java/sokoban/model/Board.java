package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;

public class Board {
    private final int maxFilledCells;

    private final Grid grid = new Grid();

    private final BooleanBinding isFull;

    public Board() {
        this(Grid.GRID_WIDTH, Grid.GRID_HEIGHT);
    }

    public Board(int width, int height) {
        this.grid = new Grid(width, height);
        maxFilledCells = grid.getArea() / 2;
        isFull = grid.filledCellsCountProperty().isEqualTo(maxFilledCells);
    }

    public CellValue play(int line, int col, CellValue newElem) {
        if (grid.getValue(line, col) == CellValue.GROUND && isFull())
            return CellValue.GROUND;

        grid.play(line, col, grid.getValue(line, col) == CellValue.GROUND ? CellValue. : CellValue.EMPTY);
        return grid.getValue(line, col);
    }

    private CellValue putGoodImage(CellValue oldElem, CellValue newElem ){
        if (oldElem == CellValue. )
        return oldElem;
    }


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
