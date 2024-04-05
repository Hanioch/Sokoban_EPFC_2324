package sokoban.model;

public class Player4Design extends Player {

    public Player4Design(int x, int y) {
        super(x, y);
    }

    public Player4Design() {
        super();
    }

    public boolean playerIsSet() {
        return getX() >= 0;
    }
    public void removePlayer() {
        setX(-1);
        setY(-1);
    }


}
