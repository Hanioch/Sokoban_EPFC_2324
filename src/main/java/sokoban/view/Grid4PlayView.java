package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import sokoban.viewmodel.Board4PlayViewModel;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.Grid4PlayViewModel;
import sokoban.viewmodel.GridViewModel;

public class Grid4PlayView extends GridView {
    public Grid4PlayView(Board4PlayViewModel boardViewModel, Grid4PlayViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        super(boardViewModel, gridViewModel, gridWidth, gridHeight);

        int GRID_WIDTH = boardViewModel.gridWidth();
        int GRID_HEIGHT = boardViewModel.gridHeight();

        DoubleBinding cellSize = Bindings.createDoubleBinding(
                () -> Math.min(
                        gridWidth.subtract(PADDING * 2).divide(GRID_WIDTH).doubleValue(),
                        gridHeight.subtract(PADDING * 2).divide(GRID_HEIGHT).doubleValue()
                ),
                gridWidth, gridHeight
        );

        for(int i = 0 ; i < GRID_HEIGHT; ++i) {
            for (int j = 0 ; j < GRID_WIDTH; ++j) {
                Cell4PlayView cellView = new Cell4PlayView(gridViewModel.getCellViewModel(j,i),  cellSize, cellSize);
                add(cellView, j, i);
            }
        }
    }
}
