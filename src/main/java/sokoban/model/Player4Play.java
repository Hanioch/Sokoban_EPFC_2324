package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player4Play extends Player implements Movable {
    private final IntegerProperty x = new SimpleIntegerProperty();
    private final IntegerProperty y = new SimpleIntegerProperty();
    public Player4Play(int x, int y) {
        super();
        setX(x);
        setY(y);
    }
    public Player4Play() {
    }


    // Getters et setters pour les coordonnées du joueur
    public final int getXInt() {
        return x.get();
    }

    public final void setX(int value) {
        x.set(value);
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public final int getYInt() {
        return y.get();
    }

    public final void setY(int value) {
        y.set(value);
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public static void move(Direction direction) {
        switch(direction){
            case UP -> Y--;
            case DOWN -> Y++;
            case LEFT -> X--;
            case RIGHT -> X++;
        }
    }
}
