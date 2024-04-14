package sokoban.viewmodel;

import javafx.beans.property.ReadOnlyListProperty;
import sokoban.model.*;
import sokoban.model.Movable.Direction;

public class Cell4PlayViewModel extends CellViewModel{
    private Board4PlayViewModel board4PlayViewModel;
    private Board4Play board4Play;

    Cell4PlayViewModel(int line, int col, Board4Play board4Play, Board4PlayViewModel board4PlayViewModel) {
        super();
        this.line = line;
        this.col = col;
        this.board4Play = board4Play;
        this.board4PlayViewModel = board4PlayViewModel;
        stack = board4Play.getGrid().getStack(line, col);
    }
    public ReadOnlyListProperty<Element> valueProperty(){
        return board4Play.valueProperty(line,col);
    }

    public void movePlayer(Direction direction){
        Grid4Play grid = board4Play.getGrid();
        Player4Play player =  grid.getPlayer();
        if (player!= null){
           boolean moveSuccessfully = grid.movePlayer(direction);
           if (moveSuccessfully){
               board4Play.incrementMoves();
               board4Play.checkWinCondition();
           }
        }
    }

    public void clicOnMushroom() {
        board4PlayViewModel.clicOnMushroom(line, col);
    }


    public Player4Play getPlayer() {
        return board4Play.getPlayer();
    }
}
