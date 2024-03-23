package sokoban.view;

import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.CellViewModel;
import sokoban.viewmodel.GridViewModel;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class GridView extends GridPane {
    private static final int PADDING = 20;
    private static final int GRID_WIDTH = BoardViewModel.gridWidth();
    private static final int GRID_HEIGHT = BoardViewModel.gridHeight();

    GridView (GridViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        setGridLinesVisible(false);
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = gridWidth
                .subtract(PADDING * 2)
                .divide(GRID_WIDTH);

        DoubleBinding cellHeight = gridHeight
                .subtract(PADDING * 2)
                .divide(GRID_WIDTH);

        for(int i = 0 ; i < GRID_HEIGHT; ++i) {
            for (int j = 0 ; j < GRID_WIDTH; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(j,i), cellWidth, cellHeight);
                add(cellView, j, i);
            }
        }
    }
}
