package sokoban.model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class Cell {
    private final ObjectProperty<CellValue> value = new SimpleObjectProperty<>(CellValue.GROUND);
    public Cell() {
    }

    public ObjectProperty<CellValue> valueProperty() {
        return value;
    }

    public void setValue(CellValue value) {
        this.value.set(value);
    }
    boolean isEmpty() {
        return value.get() == CellValue.GROUND;
    }
    public CellValue getValue() {
        return value.get();
    }

}

