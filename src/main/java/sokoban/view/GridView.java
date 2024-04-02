package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.Grid4DesignViewModel;
import sokoban.viewmodel.GridViewModel;

public class GridView extends GridPane {

    BoardViewModel boardViewModel;
    GridViewModel gridViewModel;
    public static int PADDING = 20;

    public GridView(BoardViewModel boardViewModel, GridViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        this.boardViewModel = boardViewModel;
        this.gridViewModel = gridViewModel;

        setGridLinesVisible(false);
        //  setPadding(new Insets(PADDING));
    }
}
