package sokoban.model;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Cell {
    protected  ObservableList<Element> stack;
    void addValue(Cell value){}

    public ObservableList<Element> getValue() {
        return stack;
    }

    public ObservableList<Element> getStack() {
        return stack;
    }

    public ReadOnlyListProperty<Element> stackProperty() {
        return new SimpleListProperty<>(stack);
    }

    public Cell(){
        this.stack = FXCollections.observableArrayList();
    }

    public boolean isEmpty() {
        boolean containsGround = stack.stream().anyMatch(item -> item instanceof Ground);
        return stack.size() == 1 && containsGround;
    }
}
