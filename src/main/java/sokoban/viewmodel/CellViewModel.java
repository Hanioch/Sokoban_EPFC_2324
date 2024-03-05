package sokoban.viewmodel;

import sokoban.model.*;
import sokoban.model.CellValue;
import javafx.beans.property.ReadOnlyObjectProperty;


public class CellViewModel {
    private final ReadOnlyObjectProperty<CellValue> valueProperty;

    public CellViewModel(Cell cell) {
        this.valueProperty = cell.valueProperty();
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty() {
        return valueProperty;
    }
}
