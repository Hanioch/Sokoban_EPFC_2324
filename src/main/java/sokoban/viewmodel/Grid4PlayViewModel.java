package sokoban.viewmodel;

import sokoban.model.Board4Play;

public class Grid4PlayViewModel extends GridViewModel {
    private Board4Play board;
    private Board4PlayViewModel boardViewModel;

    public Grid4PlayViewModel(Board4Play board4Play, Board4PlayViewModel board4PlayViewModel) {
        this.board = board4Play;
        this.boardViewModel = board4PlayViewModel;
    }

    public Cell4PlayViewModel getCellViewModel (int line, int col) {
        return new Cell4PlayViewModel(line, col, board, boardViewModel);
    }
}
