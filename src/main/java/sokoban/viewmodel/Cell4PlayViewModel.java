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

    private  ObservableList<Element> stack;


    Cell4PlayViewModel( Board4Play board4Play, Board4PlayViewModel board4PlayViewModel) {
        this.board4Play = board4Play;
        this.board4PlayViewModel = board4PlayViewModel;
        stack = board4Play.getGrid().getCell(line, col).getStack();
    }
}
