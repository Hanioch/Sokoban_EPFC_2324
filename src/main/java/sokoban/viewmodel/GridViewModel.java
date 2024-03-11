package sokoban.viewmodel;
import javafx.collections.ObservableList;
import sokoban.model.Board;
import sokoban.model.Element;
import sokoban.model.Ground;

public class GridViewModel {
    private final Board board;

    public GridViewModel (Board board) {
        this.board = board;
    }
    public CellViewModel getCellViewModel (int line, int col) {
        return new CellViewModel(line, col, board);
    }
}
