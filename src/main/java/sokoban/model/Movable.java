package sokoban.model;

import java.util.List;

public interface Movable {
   public List<Integer> move(Direction direction);
    public enum Direction{
        UP,DOWN,LEFT,RIGHT;
    }

}
