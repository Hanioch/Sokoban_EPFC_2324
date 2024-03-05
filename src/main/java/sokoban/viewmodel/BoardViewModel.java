package sokoban.viewmodel;

import sokoban.model.Board;


public class BoardViewModel {
    private final Board board;

    public BoardViewModel(Board board) {
        this.board = board;
    }

    public GridViewModel getGridViewModel() {

        return new GridViewModel(board.getGrid());
    }
}
