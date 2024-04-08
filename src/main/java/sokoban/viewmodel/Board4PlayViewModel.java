package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sokoban.model.Board4Design;
import sokoban.model.Board4Play;
import sokoban.model.Player4Play;
import sokoban.model.Movable.Direction;
import sokoban.model.Target4Play;

public class Board4PlayViewModel extends BoardViewModel {
    private Grid4PlayViewModel gridViewModel;
    private Board4Play board;
    public Board4PlayViewModel(Board4Play board4Play) {
        this.board = board4Play;
        gridViewModel = new Grid4PlayViewModel(board4Play, this);
    }
    public int gridWidth(){
        return board.getGrid().getWidth();
    }
    public int gridHeight(){
        return board.getGrid().getHeight();
    }
    public Grid4PlayViewModel getGridViewModel(){
        return gridViewModel;
    }
    public void movePlayer(Direction direction){
        Player4Play player = getPlayer();
        Cell4PlayViewModel cell = gridViewModel.getCellViewModel(player.getX(), player.getY());
        cell.movePlayer(direction);
    }
    public void undo() {
        boolean successful = board.getGrid().undo();
        if (successful) {
            board.addFiveMoves();
        }
    }
    public void redo(){
        boolean successful = board.getGrid().redo();
        if (successful) {
            board.incrementMoves();
        }
    }
    public BooleanBinding isStone(){
        return board.getGrid().getIsStone();
    }
    public Player4Play getPlayer() {
        return this.board.getPlayer();
    }
    public IntegerProperty moves() {
        return board.movesProperty();
    }
    public IntegerProperty boxOnTarget(){
        return board.boxOnTarget();
    }
    public IntegerProperty totalTarget(){
        return board.totalTarget();
    }
    public BooleanProperty gameWon() {
        return board.gameWonProperty();
    }
}
