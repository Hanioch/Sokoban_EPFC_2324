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

    public int[] move(Direction direction) {
        int[] list = new int[2];
        int x= getX();
        int y= getY();

        switch (direction){
            case UP ->  {
                list[0] = x;
                list[1] =y-1 ;
            }
            case LEFT -> {
                list[0] = x-1;
                list[1] =y ;
            }
            case RIGHT ->  {
                list[0] = x+1;
                list[1] =y ;
            }
            case DOWN ->  {
                list[0] = x;
                list[1] =y+1 ;
            }
            default -> {
                list[0] = x;
                list[1] =y ;
            }
        }

        return list;

    }
}
