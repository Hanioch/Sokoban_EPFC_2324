package sokoban.viewmodel;

import javafx.beans.binding.LongBinding;
import javafx.beans.property.BooleanProperty;
import sokoban.model.Board;


public class BoardViewModel {
    private final Board board;

    public BoardViewModel(Board board) {
        this.board = board;
    }

    public GridViewModel getGridViewModel() {
        return new GridViewModel(board.getGrid(),board);
    }

    public BooleanProperty isModifiedProperty() {
        return board.isModifiedProperty();
    }

    public LongBinding filledCellsCountProperty() { return board.filledCellsCountProperty();}

    public int maxFilledCells() {
        return board.getMaxFilledCells();
    }
}

