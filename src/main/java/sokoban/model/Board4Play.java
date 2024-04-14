package sokoban.model;

import javafx.beans.binding.Bindings;
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
    public void incrementMoves() {
        moves.set(moves.get() + 1);
        if (grid.getIsStone().get()){
            if (grid.getCountBeforeToBeNormal() < 5){
                grid.setCountBeforeToBeNormal(1);
            }
            else
                grid.setIsStone();
        }
        grid.getIsStone().invalidate();
    }


    public void incrementsMore(int mv){
        moves.set(moves.get()+mv);
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

    public void checkWinCondition() {
        if (boxOnTarget().get() == totalTarget().get()) {
            gameWon.set(true);
        }
    }

    public Player4Play getPlayer(){
        return player;
    }


}