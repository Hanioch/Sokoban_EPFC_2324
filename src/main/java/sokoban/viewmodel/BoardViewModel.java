package sokoban.viewmodel;

import sokoban.model.Board;
import sokoban.model.Grid;
import javafx.beans.binding.LongBinding;
import sokoban.model.Board;

public class BoardViewModel {
    private Board board;

    public BoardViewModel(Board board) {
        this.board = board;
    }

    public GridViewModel getGridViewModel() {

        return new GridViewModel(board.getGrid());
    }
}
