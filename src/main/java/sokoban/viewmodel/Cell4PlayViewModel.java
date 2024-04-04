package sokoban.viewmodel;

import javafx.beans.property.ReadOnlyListProperty;
import sokoban.Move;
import sokoban.model.*;

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

    public void movePlayer(Move direction){
        Element player =  board4Play.getGrid().getPlayerElement();
        if (player!= null){
           boolean moveSuccessfully =  board4Play.getGrid().movePlayer(direction);
           if (moveSuccessfully)
               stack.remove((player));

        }
    }
}
