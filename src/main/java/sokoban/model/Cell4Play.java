package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cell4Play extends Cell{
    private int  boxnumber ;
    private Player4Play player;
    private int x;
    private int y;
    public Cell4Play() {
        super();
        stack.add(new Ground4Play());
    }
    public Cell4Play(ObservableList<Element> stack, Player4Play player, int x, int y, int boxnumber) {
        super();
        this.boxnumber = boxnumber;
        this.player = player;
        this.x = x;
        this.y = y;
        recreateStack(stack);
    }
    public Cell4Play(ObservableList<Element> stack, Player4Play player, int x, int y) {
        super();
        this.boxnumber = boxnumber;
        this.player = player;
        this.x = x;
        this.y = y;
        recreateStack(stack);
    }
    public void recreateStack(ObservableList<Element> stack) {
        for(Element elem : stack) {
            String type = elem.getClass().getSimpleName();
            switch (type) {
                case "Ground4Design" -> this.stack.add(new Ground4Play());
                case "Wall4Design" -> this.stack.add(new Wall4Play());
                case "Player4Design" -> {this.player.setX(x);
                                            this.player.setY(y);
                                            this.stack.add(this.player);}
                case "Box4Design" -> this.stack.add(new Box4Play());
                case "Target4Design" -> this.stack.add(new Target4Play());
            }
        }
    }
}
