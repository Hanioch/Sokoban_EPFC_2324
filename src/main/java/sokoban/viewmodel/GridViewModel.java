package sokoban.viewmodel;
import javafx.collections.ObservableList;
import sokoban.model.Board;
import sokoban.model.Element;
import sokoban.model.Ground;

public class GridViewModel {
    private final Board board;
    private BoardViewModel boardViewModel;
    public GridViewModel(Board board, BoardViewModel boardViewModel) {
        this.board = board;
        this.boardViewModel = boardViewModel;
    }
    public CellViewModel getCellViewModel (int line, int col) {
        return new CellViewModel(line, col, board, boardViewModel);
    }


}
