package sokoban.model;

public abstract class Player extends ComposableElement implements Movable{
    protected static int X, Y;
    public Player(int x, int y) {
        X = x;
        Y = y;
    }

    public Player() {
    }

    public static int getX() {
        return X;
    }

    public static int getY() {
        return Y;
    }
    public boolean playerIsSet() {
        return getX() >= 0;
    }
    public void removePlayer() {
        X = -1;
        Y = -1;
    }
}
