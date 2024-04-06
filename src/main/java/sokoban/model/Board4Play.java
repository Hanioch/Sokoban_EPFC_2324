package sokoban.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

public class Board4Play extends Board{
    private IntegerProperty moves = new SimpleIntegerProperty(0);
    private BooleanProperty gameWon = new SimpleBooleanProperty(false);
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

    // goDown, goUp, goLeft, goRight à factoriser

    public void goDown() {
        Movable.Direction direction = Movable.Direction.DOWN;
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), direction);
        if(canGo) {
            incrementMoves();
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), direction);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                Box4Play box = new Box4Play();
                for(Element elem : grid.getCell(player.getX(), player.getY()+1).getStack()){
                    if(elem instanceof Box) {
                        box = (Box4Play)elem;
                    }
                }
                grid.getCell(player.getX(), player.getY()+1).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX(), player.getY()+2).getStack().add(box);
            }
            player.move(direction);
            grid.getCell(player.getX(), player.getY()-1).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
        grid.recalculateBoxesAndTargets();
        checkWinCondition();
    }

    public void goUp() {
        Movable.Direction direction = Movable.Direction.UP;
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), direction);
        if(canGo) {
            incrementMoves();
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), direction);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                Box4Play box = new Box4Play();
                for(Element elem : grid.getCell(player.getX(), player.getY()-1).getStack()){
                    if(elem instanceof Box) {
                        box = (Box4Play)elem;
                    }
                }
                grid.getCell(player.getX(), player.getY()-1).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX(), player.getY()-2).getStack().add(box);
            }
            player.move(direction);
            grid.getCell(player.getX(), player.getY()+1).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
        grid.recalculateBoxesAndTargets();
        checkWinCondition();
    }
    public void goRight() {
        Movable.Direction direction = Movable.Direction.RIGHT;
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), direction);
        if(canGo) {
            incrementMoves();
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), direction);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                Box4Play box = new Box4Play();
                for(Element elem : grid.getCell(player.getX()+1, player.getY()).getStack()){
                    if(elem instanceof Box) {
                        box = (Box4Play)elem;
                    }
                }
                grid.getCell(player.getX()+1, player.getY()).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX()+2, player.getY()).getStack().add(box);
            }
            player.move(direction);
            grid.getCell(player.getX()-1, player.getY()).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
        grid.recalculateBoxesAndTargets();
        checkWinCondition();
    }
    public void goLeft() {
        Movable.Direction direction = Movable.Direction.LEFT;
        boolean canGo = false;
        canGo = grid.canGo(player.getX(), player.getY(), direction);
        if(canGo) {
            incrementMoves();
            ObservableList<Element> nextStack = grid.getNextStack(player.getX(), player.getY(), direction);
            if(nextStack.stream().anyMatch(item -> item instanceof Box)) {
                Box4Play box = new Box4Play();
                for(Element elem : grid.getCell(player.getX()-1, player.getY()).getStack()){
                    if(elem instanceof Box) {
                        box = (Box4Play)elem;
                    }
                }
                grid.getCell(player.getX()-1, player.getY()).getStack().removeIf(item -> item instanceof Box);
                grid.getCell(player.getX()-2, player.getY()).getStack().add(box);
            }
            player.move(direction);
            grid.getCell(player.getX()+1, player.getY()).getStack().remove(player);
            grid.getCell(player.getX(), player.getY()).getStack().add(player);
        }
        grid.recalculateBoxesAndTargets();
        checkWinCondition();
    }
    private void go(){

    }
    public void incrementMoves() {
        moves.set(moves.get() + 1);
    }

    public IntegerProperty movesProperty() {
        return moves;
    }
    public IntegerProperty boxOnTarget(){
        return grid.boxesOnTargetsProperty();
    }
    public IntegerProperty totalTarget(){
        return grid.totalTargetProperty();
    }
    public BooleanProperty gameWonProperty() {
        return gameWon;
    }

    private void checkWinCondition() {
        if (boxOnTarget().get() == totalTarget().get()) {
            gameWon.set(true);
        }
    }

    public Player4Play getPlayer(){
        return player;
    }
}