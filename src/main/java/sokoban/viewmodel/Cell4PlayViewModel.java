package sokoban.viewmodel;

import javafx.beans.property.ReadOnlyListProperty;
import sokoban.model.Board4Play;
import sokoban.model.Element;
import sokoban.model.Player4Play;
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
        Element player =  board4Play.getGrid().getPlayerElement();
        if (player!= null){
           boolean moveSuccessfully =  board4Play.getGrid().movePlayer(direction);
           if (moveSuccessfully){
               board4Play.incrementMoves();
               stack.remove((player));
               board4Play.checkWinCondition();
           }
        }
    }

    public Player4Play getPlayer() {
        return board4Play.getPlayer();
    }
}
