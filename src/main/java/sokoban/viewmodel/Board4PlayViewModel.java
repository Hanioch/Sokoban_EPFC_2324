package sokoban.viewmodel;

import sokoban.Move;
import sokoban.model.Board4Design;
import sokoban.model.Board4Play;
import sokoban.model.Grid4Play;
import sokoban.model.Player4Play;

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
    public void movePlayer(Move direction){
        Cell4PlayViewModel cell = gridViewModel.getCellViewModel(Player4Play.getX(),Player4Play.getY());
        cell.movePlayer(direction);
    }
}
