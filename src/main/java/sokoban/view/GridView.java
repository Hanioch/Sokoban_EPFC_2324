package sokoban.view;

import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class GridView extends GridPane {
    private static final int PADDING = 20;
    private static final int GRID_WIDTH = BoardViewModel.gridWidth();

    GridView (GridViewModel gridViewModel, DoubleBinding gridWidth) {
        setGridLinesVisible(true);
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = gridWidth
                .subtract(PADDING * 2)
                .divide(GRID_WIDTH);

        for(int i = 0 ; i < GRID_WIDTH; ++i) {
            for (int j = 0 ; j < GRID_WIDTH; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(i,j), cellWidth);
                add(cellView, j, i); // lignes et colonnes inversées dans gridpane
            }
        }
    }
}
