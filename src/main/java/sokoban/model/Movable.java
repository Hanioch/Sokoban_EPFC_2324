package sokoban.model;

import sokoban.Move;

import java.util.ArrayList;
import java.util.List;

public interface Movable {
   public List<Integer> move(Move direction);
}
