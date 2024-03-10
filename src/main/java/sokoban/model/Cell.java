package sokoban.model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.viewmodel.CellViewModel;


public class Cell {
    private ObjectProperty<CellValue> value = new SimpleObjectProperty<>(CellValue.GROUND);
    private ObjectProperty<CellValue> overlay = new SimpleObjectProperty<>(null);

    public Cell() {

    }

    public ObjectProperty<CellValue> valueProperty() {

        return value;
    }
    public ObjectProperty<CellValue> overlayProperty() {
        return overlay;
    }

    public CellValue getOverlay() {
        return overlay.get();
    }

    public void setOverlay(CellValue overlay) {
        this.overlay.set(overlay);
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

