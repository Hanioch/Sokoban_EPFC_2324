package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class Element {
    private ObjectProperty<Cell> cell;
    private IntegerProperty row;
    private IntegerProperty column;

    public Element() {
        this.cell = new SimpleObjectProperty<>();
    }
    public Cell getCell() {
        return cell.get();
    }
    public ObjectProperty<Cell> cellProperty() {
        return cell;
    }
    public void setCell(Cell cell) {
        this.cell.set(cell);
    }
}
