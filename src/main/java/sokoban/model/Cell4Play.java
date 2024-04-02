package sokoban.model;

import javafx.collections.FXCollections;

public class Cell4Play extends Cell{
    public Cell4Play() {
        super();
        stack.add(new Ground4Play());
    }
}
