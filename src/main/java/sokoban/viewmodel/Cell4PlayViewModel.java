package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import sokoban.model.Board4Design;
import sokoban.model.Board4Play;
import sokoban.model.Element;

public class Cell4PlayViewModel extends CellViewModel{
    private Board4PlayViewModel board4PlayViewModel;
    private Board4Play board4Play;

    Cell4PlayViewModel(int line, int col, Board4Play board4Play, Board4PlayViewModel board4PlayViewModel) {
        super();
        this.line = line;
        this.col = col;
        this.board4Play = board4Play;
        this.board4PlayViewModel = board4PlayViewModel;
        this.stack = board4Play.getGrid().getCell(line, col).getStack();
    }
}
