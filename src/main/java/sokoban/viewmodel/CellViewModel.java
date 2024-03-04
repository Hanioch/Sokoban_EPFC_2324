package sokoban.viewmodel;

import sokoban.model.*;
import sokoban.model.Board;
import sokoban.model.CellValue;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CellViewModel {
    private ReadOnlyObjectProperty<CellValue> valueProperty;

    public CellViewModel(Cell cell) {
        this.valueProperty = cell.valueProperty();
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty() {
        return valueProperty;
    }
}
