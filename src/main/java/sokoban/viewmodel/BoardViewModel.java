package sokoban.viewmodel;

import sokoban.model.Board;
import sokoban.model.Grid;
import javafx.beans.binding.LongBinding;
import sokoban.model.Board;

public class BoardViewModel {
    private GridViewModel gridViewModel;

    public BoardViewModel(Grid grid) {
        this.gridViewModel = new GridViewModel(grid);
    }

    public GridViewModel getGridViewModel() {
        return gridViewModel;
    }
}
