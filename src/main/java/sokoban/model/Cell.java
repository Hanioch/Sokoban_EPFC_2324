package sokoban.model;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

public abstract class Cell {
    protected  ObservableList<Element> stack;
    void addValue(Cell4Design value){}

    public ObservableList<Element> getValue() {
        return stack;
    }

    public ObservableList<Element> getStack() {
        return stack;
    }

    public ReadOnlyListProperty<Element> stackProperty() {
        return new SimpleListProperty<>(stack);
    }

    public boolean isEmpty() {
        boolean containsGround =  stack.stream().anyMatch(item -> item instanceof Ground4Design);
        return stack.size() == 1 && containsGround;
    }
}
