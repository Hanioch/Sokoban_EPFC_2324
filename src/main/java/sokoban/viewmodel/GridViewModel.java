package sokoban.viewmodel;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.*;

public class GridViewModel {
    private final Grid grid;

    public GridViewModel(Grid grid) {
        this.grid = grid;
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }

    public void setValue(int i, int j, CellValue value) {
        if (i >= 0 && i < Grid.getWidth() && j >= 0 && j < Grid.getHeight()) {
            grid.setValue(i, j, value);
        }
    }
}
