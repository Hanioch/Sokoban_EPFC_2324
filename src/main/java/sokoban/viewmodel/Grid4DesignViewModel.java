package sokoban.viewmodel;
import sokoban.model.Board4Design;

public class Grid4DesignViewModel extends GridViewModel {
    private Board4Design board;
    private Board4DesignViewModel boardViewModel;
    public Grid4DesignViewModel(Board4Design board4Design, Board4DesignViewModel board4DesignViewModel) {
        this.board = board4Design;
        this.boardViewModel = board4DesignViewModel;
    }
    public Cell4DesignViewModel getCellViewModel (int line, int col) {
        return new Cell4DesignViewModel(line, col, board, boardViewModel);
    }
}
