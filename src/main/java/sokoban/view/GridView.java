package sokoban.view;

import javafx.beans.binding.Bindings;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import static java.lang.Math.min;


public class GridView extends GridPane {
    BoardViewModel boardViewModel;
    public static int PADDING = 20;

    GridView (BoardViewModel boardViewModel,GridViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        this.boardViewModel = boardViewModel;
        int GRID_WIDTH = boardViewModel.gridWidth();
        int GRID_HEIGHT = boardViewModel.gridHeight();

        setGridLinesVisible(false);
        setPadding(new Insets(PADDING));

        DoubleBinding cellSize = Bindings.createDoubleBinding(
                () -> Math.min(
                        gridWidth.subtract(PADDING * 2).divide(GRID_WIDTH).doubleValue(),
                        gridHeight.subtract(PADDING * 2).divide(GRID_HEIGHT).doubleValue()
                ),
                gridWidth, gridHeight
        );

        for(int i = 0 ; i < GRID_HEIGHT; ++i) {
            for (int j = 0 ; j < GRID_WIDTH; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(j,i),  cellSize, cellSize);
                add(cellView, j, i);
            }
        }
    }
}
