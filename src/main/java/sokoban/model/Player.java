package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Player extends ComposableElement {
    // protected static int X, Y;

    private IntegerProperty x = new SimpleIntegerProperty();
    private IntegerProperty y = new SimpleIntegerProperty();
    public Player(int x, int y) {
        setX(x);
        setY(y);
    }

    public Player() {
        this(-1, -1);
    }

    public final int getX() {
        return x.getValue();
    }

    public void setX(int value) {
        x.set(value);
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public final int getY() {
        return y.getValue();
    }

    public final void setY(int value) {
        y.set(value);
    }

    public IntegerProperty yProperty() {
        return y;
    }
}
