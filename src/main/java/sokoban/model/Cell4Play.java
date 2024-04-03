package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cell4Play extends Cell{
    private Player4Play player;
    public Cell4Play() {
        super();
        stack.add(new Ground4Play());
    }
    public Cell4Play(ObservableList<Element> stack) {
        super();
        recreateStack(stack);
    }
    private void recreateStack(ObservableList<Element> stack) {
        for(Element elem : stack) {
            String type = elem.getClass().getSimpleName();
            if(type.equals("Ground4Design")) {
                this.stack.add(new Ground4Play());
            } else if (type.equals("Wall4Design")) {
                this.stack.add(new Wall4Play());
            } else if (type.equals("Player4Design")) {
                this.player = new Player4Play(Player.getX(), Player.getY());
                this.stack.add(this.player);
            } else if (type.equals("Box4Design")) {
                this.stack.add(new Box4Play());
            } else if(type.equals("Target4Design")) {
                this.stack.add(new Target4Play());
            }
        }
    }
}
