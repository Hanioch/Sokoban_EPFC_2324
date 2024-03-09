package sokoban.model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.viewmodel.CellViewModel;


public class Cell {
    private ObjectProperty<CellValue> value = new SimpleObjectProperty<>(CellValue.GROUND);
    private ObjectProperty<CellValue> overlay = new SimpleObjectProperty<>();

    public Cell() {
        this.value = new SimpleObjectProperty<>(CellValue.GROUND);
        this.overlay = new SimpleObjectProperty<>();
    }

    public ObjectProperty<CellValue> valueProperty() {
        return value;
    }

    public void setValue(CellValue value) {
        this.value.set(value);
    }
    boolean isEmpty() {
        return value.get() == CellValue.GROUND && overlay.get() == null;
    }
    public CellValue getValue() {
        return value.get();
    }

}

