package sokoban.model;

import java.util.ArrayList;

public class Box4Play extends Box implements Movable {
    private int number;
    private int x, y;

    public Box4Play(int number) {
        super();
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Integer> move(Direction direction) {
        ArrayList<Integer> list = new ArrayList<>();

        switch (direction){
            case UP ->  {
                list.add(x);
                list.add(y-1);
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
                list.add(y+1);
            }
            default -> {
                list.add(x);
                list.add(y);
            }
        }

        return list;

    }
}
