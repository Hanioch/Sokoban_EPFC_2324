package sokoban.model;

import javafx.beans.property.* ;

public class Player extends ComposableElement implements Movable {

    public Player(int row, int column) {
        super(row, column);
    }
}
