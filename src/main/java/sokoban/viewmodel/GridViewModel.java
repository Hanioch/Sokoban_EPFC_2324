package sokoban.viewmodel;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.*;

public class GridViewModel {
    private final Grid grid;

    private final Board board;

    public GridViewModel(Grid grid, Board board) {
        this.grid = grid;
        this.board = board;
    }

   public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
       return grid.valueProperty(line, col);
   }

    public void setValue(int i, int j, CellValue value) {
        if (i >= 0 && i < grid.getWidth() && j >= 0 && j < grid.getHeight()) {
            grid.setValue(i, j, value);
            board.isModifiedProperty().set(true);
        }
    }

    public int getWidth() {
        return grid.getWidth();
    }

    public int getHeight() {
        return grid.getHeight();
    }
    public Grid getGrid() {
        return this.grid;
    }

}
