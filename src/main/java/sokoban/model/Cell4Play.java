package sokoban.model;

import javafx.collections.ObservableList;

public class Cell4Play extends Cell{
    private int  boxnumber ;
    public Cell4Play() {
        super();
        stack.add(new Ground4Play());
    }
    public Cell4Play(ObservableList<Element> stack, int boxnumber) {
        super();
        this.boxnumber = boxnumber;
        stack.add(new Ground4Play());
        recreateStack(stack);
    }
    private void recreateStack(ObservableList<Element> stack) {
        int i = 0;
        for(Element elem : stack) {
            String type = elem.getClass().getSimpleName();
            if(type.equals("Ground4Design")) {
                this.stack.add(new Ground4Play());
            } else if (type.equals("Wall4Design")) {
                this.stack.add(new Wall4Play());
            } else if (type.equals("Player4Design")) {
                this.stack.add(new Player4Play());
            } else if (type.equals("Box4Design")) {
                this.stack.add(new Box4Play(boxnumber));
            } else if(type.equals("Target4Design")) {
                this.stack.add(new Target4Play());
            }
        }
    }

    public void addElement(Element element){
            boolean containsTarget =  stack.stream().anyMatch(item -> item instanceof Target4Play);
            stack.clear();
            stack.add(new Ground4Design());
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

    public boolean containsBox(){
        return stack.stream().anyMatch(item -> item instanceof Box4Play);
    }
    public Element getBox(){
        if (containsBox()){
            for (Element elem : stack) {
                if (elem instanceof Box4Play)
                    return elem;
            }
        }
        return new Box4Play(0);

    }
    public void removeElement(Element element) {
        stack.remove(element);
    }

    public void removePlayer(){
        for (Element element : stackProperty()) {
            if (element instanceof Player4Play) {
                removeElement(element);
                ((Player4Play) element).removePlayer();
                break;
            }
        }
    }

}
