package sokoban.view;

import javafx.beans.binding.Bindings;
import sokoban.viewmodel.Board4DesignViewModel;
import sokoban.viewmodel.Grid4DesignViewModel;

import javafx.beans.binding.DoubleBinding;

import static java.lang.Math.min;


public class Grid4DesignView extends GridView {

    Grid4DesignView(Board4DesignViewModel boardViewModel, Grid4DesignViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
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
                CellView cellView = new Cell4DesignView(gridViewModel.getCellViewModel(j,i),  cellSize, cellSize);
                add(cellView, j, i);
            }
        }
    }
}
