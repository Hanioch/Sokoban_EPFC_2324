package sokoban.view;

import javafx.beans.binding.Bindings;
import sokoban.viewmodel.Board4DesignViewModel;
import sokoban.viewmodel.Grid4DesignViewModel;

import javafx.beans.binding.DoubleBinding;

import static java.lang.Math.min;


public class Grid4DesignView extends GridView {
    Board4DesignViewModel board4DesignViewModel;
    public static int PADDING = 20;

    Grid4DesignView(Board4DesignViewModel board4DesignViewModel, Grid4DesignViewModel grid4DesignViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        this.board4DesignViewModel = board4DesignViewModel;
        int GRID_WIDTH = board4DesignViewModel.gridWidth();
        int GRID_HEIGHT = board4DesignViewModel.gridHeight();

        setGridLinesVisible(false);
      //  setPadding(new Insets(PADDING));

        DoubleBinding cellSize = Bindings.createDoubleBinding(
                () -> Math.min(
                        gridWidth.subtract(PADDING * 2).divide(GRID_WIDTH).doubleValue(),
                        gridHeight.subtract(PADDING * 2).divide(GRID_HEIGHT).doubleValue()
                ),
                gridWidth, gridHeight
        );

        for(int i = 0 ; i < GRID_HEIGHT; ++i) {
            for (int j = 0 ; j < GRID_WIDTH; ++j) {
                Cell4DesignView cell4DesignView = new Cell4DesignView(grid4DesignViewModel.getCellViewModel(j,i),  cellSize, cellSize);
                add(cell4DesignView, j, i);
            }
        }
    }
}
