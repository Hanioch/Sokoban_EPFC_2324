package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Board4Play extends Board{
    private Grid4Play grid;
    private Player4Play player = new Player4Play();

    private BooleanBinding isFull;
    private BooleanProperty isModifiedProperty = new SimpleBooleanProperty(false);
    public ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return grid.valueProperty(line, col);
    }


    public Board4Play(int width, int height, Grid4Design oldGrid, Player4Design player) {
        this.player.setX(player.getX());
        this.player.setY(player.getY());
        grid = new Grid4Play(width, height, oldGrid, this.player);
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
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.DOWN);
        if(canGo) {
            player.move(Movable.Direction.DOWN);
            setModified(true);
            System.out.println("y : " + player.getY());
        }
    }
    public void goUp() {
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.UP);
        if(canGo) {
            player.move(Movable.Direction.UP);
            setModified(true);
            System.out.println("y : " + player.getY());
        }
    }
    public void goRight() {
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.RIGHT);
        if(canGo) {
            player.move(Movable.Direction.RIGHT);
            setModified(true);
            System.out.println("x : " + player.getX());
        }
    }
    public void goLeft() {
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.LEFT);
        if(canGo) {
            player.move(Movable.Direction.LEFT);
            setModified(true);
            System.out.println("x : " + player.getX());
        }
    }
    public Player4Play getPlayer(){
        return player;
    }
}