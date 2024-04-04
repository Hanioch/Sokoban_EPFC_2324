package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

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
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), Movable.Direction.DOWN);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                grid.getCell(player.getX(), player.getY()+1).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX(), player.getY()+2).getStack().add(new Box4Play());
            }
            player.move(Movable.Direction.DOWN);
            grid.getCell(player.getX(), player.getY()-1).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
    }
    public void goUp() {
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.UP);
        if(canGo) {
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), Movable.Direction.UP);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                grid.getCell(player.getX(), player.getY()-1).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX(), player.getY()-2).getStack().add(new Box4Play());
            }
            player.move(Movable.Direction.UP);
            grid.getCell(player.getX(), player.getY()+1).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
    }
    public void goRight() {
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.RIGHT);
        if(canGo) {
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), Movable.Direction.RIGHT);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                grid.getCell(player.getX()+1, player.getY()).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX()+2, player.getY()).getStack().add(new Box4Play());
            }
            player.move(Movable.Direction.RIGHT);
            grid.getCell(player.getX()-1, player.getY()).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
    }
    public void goLeft() {
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), Movable.Direction.LEFT);
        if(canGo) {
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), Movable.Direction.LEFT);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                grid.getCell(player.getX()-1, player.getY()).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX()-2, player.getY()).getStack().add(new Box4Play());
            }
            player.move(Movable.Direction.LEFT);
            grid.getCell(player.getX()+1, player.getY()).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
    }
    public Player4Play getPlayer(){
        return player;
    }
}