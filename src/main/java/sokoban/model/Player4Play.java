package sokoban.model;

import java.util.ArrayList;

public class Player4Play extends Player implements Movable {

    public Player4Play(int x, int y) {
        super();
        setX(x);
        setY(y);
    }
    public Player4Play() {
        super();
    }

    public ArrayList<Integer> move(Direction direction) {
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
