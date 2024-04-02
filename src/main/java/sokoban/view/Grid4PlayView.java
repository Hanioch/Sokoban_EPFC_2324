package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import sokoban.viewmodel.Board4PlayViewModel;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.Grid4PlayViewModel;
import sokoban.viewmodel.GridViewModel;

public class Grid4PlayView extends GridView {
    public Grid4PlayView(Board4PlayViewModel boardViewModel, Grid4PlayViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        super(boardViewModel, gridViewModel, gridWidth, gridHeight);
    }
}
