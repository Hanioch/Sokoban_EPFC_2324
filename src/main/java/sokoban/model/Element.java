package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class Element {
    private ObjectProperty<Cell> cell;
    private IntegerProperty row;
    private IntegerProperty column;

    public Element(int row, int column) {
        this.row = new SimpleIntegerProperty(row);
        this.column = new SimpleIntegerProperty(column);
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
    public int getRow() {
        return row.get();
    }
    public IntegerProperty rowProperty() {
        return row;
    }

    public void setRow(int row) {
        this.row.set(row);
    }

    public int getColumn() {
        return column.get();
    }

    public IntegerProperty columnProperty() {
        return column;
    }

    public void setColumn(int column) {
        this.column.set(column);
    }
}
