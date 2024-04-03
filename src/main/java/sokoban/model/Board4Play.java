package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Board4Play extends Board{
    private Grid4Play grid;

    private BooleanBinding isFull;
    private BooleanProperty isModifiedProperty = new SimpleBooleanProperty(false);
    public ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }


    public Board4Play(int width, int height, Grid4Design oldGrid) {
        grid = new Grid4Play(width, height, oldGrid);

    }
    public BooleanProperty isModifiedProperty() {
        return isModifiedProperty;
    }

    public void setModified(boolean isModified) {
        this.isModifiedProperty.set(isModified);
    }
    public Grid4Play getGrid(){
        return this.grid;
    }

    public void goDown() {
        boolean canGo = false;
        canGo = grid.canGo(Player.getX(), Player.getY(), Movable.Direction.DOWN);
        if(canGo) {
            Player4Play.move(Movable.Direction.DOWN);
            setModified(true);
            System.out.println("y : " + Player.getY());
        }
    }
    public void goUp() {
        boolean canGo = false;
        canGo = grid.canGo(Player.getX(), Player.getY(), Movable.Direction.UP);
        if(canGo) {
            Player4Play.move(Movable.Direction.UP);
            setModified(true);
            System.out.println("y" + Player.getY());
        }
    }
    public void goRight() {
        boolean canGo = false;
        canGo = grid.canGo(Player.getX(), Player.getY(), Movable.Direction.RIGHT);
        if(canGo) {
            Player4Play.move(Movable.Direction.RIGHT);
            setModified(true);
            System.out.println("x" + Player.getX());
        }
    }
    public void goLeft() {
        boolean canGo = false;
        canGo = grid.canGo(Player.getX(), Player.getY(), Movable.Direction.LEFT);
        if(canGo) {
            Player4Play.move(Movable.Direction.LEFT);
            setModified(true);
            System.out.println("x" + Player.getX());
        }
    }
}