package sokoban.model;

import javafx.collections.ObservableList;

public class Cell4Play extends Cell{
    private int boxNumber;
    private Player4Play player;
    private int x;
    private int y;
    public Cell4Play() {
        super();
        stack.add(new Ground4Play());

    }
    public Cell4Play(ObservableList<Element> stack, Player4Play player, int x, int y, int boxNumber) {
        super();
        this.boxNumber = boxNumber;
        this.player = player;
        this.x = x;
        this.y = y;

        recreateStack(stack);
    }
    private void recreateStack(ObservableList<Element> stack) {
        for(Element elem : stack) {
            String type = elem.getClass().getSimpleName();
            switch (type) {
                case "Ground4Design" -> this.stack.add(new Ground4Play());
                case "Wall4Design" -> this.stack.add(new Wall4Play());
                case "Player4Design" -> {this.player.setX(x);
                                            this.player.setY(y);
                                            this.stack.add(this.player);}
                case "Box4Design" -> this.stack.add(new Box4Play(boxNumber));
                case "Target4Design" -> this.stack.add(new Target4Play());
            }
        }
    }

    public void addElement(Element element){
            boolean containsTarget = containsTarget();
            stack.clear();
            stack.add(new Ground4Play());
            stack.add(element);

            if (containsTarget)
                stack.add(new Target4Play());
    }
    public boolean containsWall(){
        return stack.stream().anyMatch(item -> item instanceof Wall4Play);
    }

    public boolean containsOnlyTarget(){
       return stack.size() == 2 && stack.get(1) instanceof Target4Play;
    }
    public boolean containsTarget () {return stack.stream().anyMatch(item -> item instanceof Target4Play);}

    public boolean containsBox(){
        return stack.stream().anyMatch(item -> item instanceof Box4Play);
    }
    public Box4Play getBox(){
        if (containsBox()){
            for (Element elem : stack) {
                if (elem instanceof Box4Play)
                    return (Box4Play) elem;
            }
        }
        return new Box4Play(0);

    }

    public Mushroom getMushroom(){
        if (containsMushroom()){
            for (Element elem : stack) {
                if (elem instanceof Mushroom)
                    return (Mushroom) elem;
            }
        }
        return null;
    }

    public boolean containsMushroom(){
        return stack.stream().anyMatch(item -> item instanceof Mushroom);
    }

    public void removeElement(Element element) {
        stack.remove(element);
    }
}
