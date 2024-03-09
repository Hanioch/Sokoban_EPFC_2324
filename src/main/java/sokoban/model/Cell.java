package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cell {
    private final IntegerProperty row;
    private final IntegerProperty column;
    private final ObservableList<Element> stack;

    public Cell(int row, int column) {
        this.row = new SimpleIntegerProperty(row);
        this.column = new SimpleIntegerProperty(column);
        this.stack = FXCollections.observableArrayList();
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

    public ObservableList<Element> getStack() {
        return stack;
    }

    public void addElement(ComposableElement element) {
        stack.add(element);
    }

    public void removeElement(ComposableElement element) {
        stack.remove(element);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }





    /*
    public ObservableList<Element> getElements() {
        return elements.get();
    }

    public ListProperty<Element> elementsProperty() {
        return elements;
    }

    public void setElements(ObservableList<Element> elements) {
        this.elements.set(elements);
    }

    public void addElement(Element element) {
        elements.add(element);
        element.setCell(this);
    }

    public void removeElement(Element element) {
        elements.remove(element);
        element.setCell(null);
    }
    */

    /*void addValue(CellValue value){
        switch (value){
            case GROUND,WALL :
                elements.removeAll();
                elements.add(value);
                break;
            case BOX,PLAYER:
                if (elements.size()>1 || containsWall() ){
                    elements.removeAll();
                    elements.add(CellValue.GROUND);
                    elements.add(value);
                }else{
                    elements.add(value);
                }
                break;
            case TARGET:
                if (containsWall()){
                    elements.removeAll();
                    elements.add(CellValue.GROUND);
                    elements.add(value);
                }else{
                    elements.add(value);
                }
                break;
        }
    }*/

    /*void removeValue(CellValue value){
        switch (value){
            case WALL:
                elements.remove(0);
                elements.add(CellValue.GROUND);
                break;
            case BOX, PLAYER:
                elements.remove(1);
                break;
            case TARGET:
                elements.remove(2);
                break;
        }
    }*/

    /*boolean containsWall(){
        return elements.contains(CellValue.WALL);
    }
    */

    /*
    ReadOnlyObjectProperty<CellValue> valueProperty(){
        return values;
    }*/
}
