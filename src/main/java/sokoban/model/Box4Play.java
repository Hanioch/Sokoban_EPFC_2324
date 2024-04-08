package sokoban.model;

import java.util.ArrayList;
import java.util.Stack;

public class Box4Play extends Box implements Movable {
    private int number;
    private int x, y;
    private boolean isOnTarget = false;

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

    public boolean isOnTarget() {
        return isOnTarget;
    }

    public void setOnTarget(boolean onTarget) {
        isOnTarget = onTarget;
    }

    public int[] move(Direction direction) {
        int[] list = new int[2];

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
