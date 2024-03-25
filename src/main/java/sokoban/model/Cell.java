package sokoban.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

            stack.add(element);

            if (containsTarget){
                  stack.add(new Target());
            }


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

    public void removeElement(Element element) {
        stack.remove(element);
    }
    public void removePlayer() {
        for (Element element : stackProperty()) {
            if (element instanceof Player) {
                removeElement(element);
                break;
            }
        }
    }
    public boolean isEmpty() {
        boolean containsGround =  stack.stream().anyMatch(item -> item instanceof Ground);
        return stack.size() == 1 && containsGround;
    }


}
