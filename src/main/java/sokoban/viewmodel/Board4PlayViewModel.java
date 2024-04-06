package sokoban.viewmodel;

import sokoban.model.Board4Play;
import sokoban.model.Player4Play;
import sokoban.model.Movable.Direction;

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
    public Player4Play getPlayer() {
        return this.board.getPlayer();
    }
}
