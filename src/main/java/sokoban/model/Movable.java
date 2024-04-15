package sokoban.model;

import java.util.List;

public interface Movable {
    public int[] move(Direction direction);
    public enum Direction{
        UP,DOWN,LEFT,RIGHT;
    }

}
