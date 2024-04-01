package sokoban.model;

public class Player4Design extends Player {

    private static int X, Y;

    public Player4Design(int x, int y) {
        super();
        X = x;
        Y = y;
    }

    public Player4Design() {
        super();
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
