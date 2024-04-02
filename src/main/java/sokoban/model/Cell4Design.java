package sokoban.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cell4Design extends Cell{

    public Cell4Design() {
        super();
    }

    public void addElement(Element element) {
        if (element instanceof Ground4Design || element instanceof Wall4Design) {
            stack.clear();
            if(element instanceof Wall4Design){
                stack.add(new Ground4Design());
            }
            stack.add(element);
        } else if (element instanceof Box4Design || element instanceof Player4Design) {
            boolean containsTarget =  stack.stream().anyMatch(item -> item instanceof Target4Design);
            stack.clear();
            stack.add(new Ground4Design());

            stack.add(element);

            if (containsTarget){
                  stack.add(new Target4Design());
            }


        } else if (element instanceof Target4Design) {
            boolean containsTarget =  stack.stream().anyMatch(item -> item instanceof Target4Design);
            boolean containsWall =  stack.stream().anyMatch(item -> item instanceof Wall4Design);

            if (containsWall){
                stack.clear();
                stack.add(new Ground4Design());
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
            if (element instanceof Player4Design) {
                removeElement(element);
                ((Player4Design) element).removePlayer();
                break;
            }
        }
    }



}
