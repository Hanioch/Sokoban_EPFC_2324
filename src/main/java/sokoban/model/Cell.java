package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Cell {
    private final ObservableList<Element> stack;

    public Cell() {
        this.stack = FXCollections.observableArrayList();
        stack.add(new Ground());
    }

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

    public void addElement(Element element) {
        if (element instanceof Ground || element instanceof Wall) {
            stack.clear();
            if(element instanceof Wall){
                stack.add(new Ground());
            }
            stack.add(element);
        } else if (element instanceof Box || element instanceof Player ) {
            boolean containsTarget =  stack.stream().anyMatch(item -> item instanceof Target);
            stack.clear();
            stack.add(new Ground());

            if (containsTarget){
                  stack.add(new Target());
            }
            stack.add(element);

        } else if (element instanceof Target) {
            boolean containsTarget =  stack.stream().anyMatch(item -> item instanceof Target);
            boolean containsWall =  stack.stream().anyMatch(item -> item instanceof Wall);

            if (containsWall){
                stack.clear();
                stack.add(new Ground());
                stack.add(element);
            } else if (!containsTarget) {
                stack.add(element);
            }
        }
    }

    public void removeElement(ComposableElement element) {
        stack.remove(element);
    }
    public void removePlayer() {
        Element playerElement = null;
        for (Element element : stack) {
            if (element instanceof Player) {
                playerElement = element;
                break;
            }
        }
        if (playerElement != null) {
            stack.remove(playerElement);
        }
    }
    public boolean isEmpty() {
        boolean containsGround =  stack.stream().anyMatch(item -> item instanceof Ground);
        return stack.size() == 1 && containsGround;
    }

}
