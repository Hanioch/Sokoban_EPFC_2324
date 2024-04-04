package sokoban.model;

import sokoban.Move;

import java.util.ArrayList;
import java.util.List;

public class Box4Play extends Box implements Movable{
    private int number;

    public Box4Play(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public ArrayList<Integer> move(Move direction, int x , int y) {
        ArrayList<Integer> list = new ArrayList<>();

        switch (direction){
            case UP ->  {
                list.add(x);
                list.add(y+1);
            }
            case LEFT -> {
                list.add(x-1);
                list.add(y);
            }
            case RIGHT ->  {
                list.add(x+1);
                list.add(y);
            }
            case DOWN ->  {
                list.add(x);
                list.add(y-1);
            }
            default -> {
                list.add(x);
                list.add(y);
            }
        }

        return list;

    }


    @Override
    public List<Integer> move(Move direction) {
        return null;
    }
}
