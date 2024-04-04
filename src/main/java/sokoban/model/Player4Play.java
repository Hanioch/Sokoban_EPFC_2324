package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player4Play extends Player implements Movable {

    public Player4Play(int x, int y) {
        super();
        setX(x);
        setY(y);
    }
    public Player4Play() {
        super();
    }

    public void move(Direction direction) {
        switch(direction){
            case UP -> this.setY(this.getY()-1);
            case DOWN -> this.setY(this.getY()+1);
            case LEFT -> this.setX(this.getX()-1);
            case RIGHT -> this.setX(this.getX()+1);
        }
    }
}
