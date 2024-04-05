package sokoban.model;

public class Box4Play extends Box implements Movable {
    private int number;

    public Box4Play(int number) {
        super();
        this.number = number;
    }

    public Box4Play() {
    }

    public int getNumber() {
        return this.number;
    }
}
