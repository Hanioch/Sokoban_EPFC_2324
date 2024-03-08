package sokoban.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Cell;
import sokoban.model.CellValue;

public class CellViewModel {

    private final ObjectProperty<CellValue> valueProperty;
    private final ObjectProperty<CellValue> overlayProperty;

    public CellViewModel(Cell cell) {
        this.valueProperty = cell.valueProperty();
        this.overlayProperty = new SimpleObjectProperty<>();
        valueProperty.addListener((obs, oldVal, newVal) -> updateOverlay());
    }

    public ObjectProperty<CellValue> valueProperty() {
        return valueProperty;
    }

    public ObjectProperty<CellValue> overlayProperty() {
        return overlayProperty;
    }

    private void updateOverlay() {
        CellValue cellValue = valueProperty.get();
        if (cellValue == CellValue.TARGET) {
            overlayProperty.set(CellValue.TARGET);
        } else {
            overlayProperty.set(null);
        }
    }
}

