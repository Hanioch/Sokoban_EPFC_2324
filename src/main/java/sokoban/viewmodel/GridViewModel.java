package sokoban.viewmodel;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.*;

public class GridViewModel {
    private Grid grid;

    public GridViewModel(Grid grid) {
        this.grid = grid;
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }
}
