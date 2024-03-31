package sokoban.model;

import javafx.beans.property.* ;

public class Player extends ComposableElement implements Movable {

    private static int X, Y;

    public Player(int x, int y) {
        super();
        X = x;
        Y = y;
    }

    public Player() {
        super();
    }

    public static int getX() {
        return X;
    }

    public static int getY() {
        return Y;
    }

    public boolean playerIsSet() {
        return getX() >= 0;
    }
    public void removePlayer() {
        X = -1;
        Y = -1;
    }
}
