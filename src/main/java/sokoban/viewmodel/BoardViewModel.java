package sokoban.viewmodel;

import sokoban.model.Board;
import sokoban.model.Grid;
import javafx.beans.binding.LongBinding;
import sokoban.model.Board;

public class BoardViewModel {
    private final GridViewModel gridViewModel;
    private final Board board;

    public BoardViewModel(Board board) {
        this.board = board;
        gridViewModel = new GridViewModel(board);
    }

    public static int gridWidth(){
        return Grid.getGridWidth();
    }
    public GridViewModel getGridViewModel(){
        return gridViewModel;
    }
    public LongBinding filledCellsCountProperty() {
        return board.filledCellsCountProperty();
    }
    public int maxFilledCells() {
        return this.board.maxFilledCells();
    }
    public Board getBoard(){
        return this.board;
    }
}
