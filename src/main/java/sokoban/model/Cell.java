package sokoban.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Cell {
    private ListProperty<Element> elements;

    public Cell() {
        this.elements = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

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

    boolean isEmpty(){
        return elements.size() == 1 && elements.get(0) == CellValue.GROUND;
    }*/

    /*
    ReadOnlyObjectProperty<CellValue> valueProperty(){
        return values;
    }*/
}
