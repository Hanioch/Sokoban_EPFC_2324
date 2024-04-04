package sokoban.model;

import sokoban.Move;

import java.util.ArrayList;
import java.util.List;

public class Player4Play extends Player implements Movable {
    public Player4Play(int x, int y) {
        super(x, y);
    }
    public Player4Play() {

    }

    public ArrayList<Integer> move(Move direction) {
        ArrayList<Integer> list = new ArrayList<>();

        switch (direction){
            case UP ->  {
                list.add(getX());
                list.add(getY()-1);
            }
            case LEFT -> {
                list.add(getX()-1);
                list.add(getY());
            }
            case RIGHT ->  {
                list.add(getX()+1);
                list.add(getY());
            }
            case DOWN ->  {
                list.add(getX());
                list.add(getY()+1);
            }
            default -> {
            list.add(getX());
            list.add(getY());
            }
        }
        return list;
    }
}
