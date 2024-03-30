package sokoban.viewmodel;
import sokoban.model.Board;

public class GridViewModel {
    private  Board board;
    private BoardViewModel boardViewModel;
    public GridViewModel(Board board, BoardViewModel boardViewModel) {
        this.board = board;
        this.boardViewModel = boardViewModel;
    }
    public CellViewModel getCellViewModel (int line, int col) {
        return new CellViewModel(line, col, board, boardViewModel);
    }
}
