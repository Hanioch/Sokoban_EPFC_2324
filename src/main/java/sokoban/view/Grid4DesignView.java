package sokoban.view;

import javafx.beans.binding.Bindings;
import sokoban.viewmodel.Board4DesignViewModel;
import sokoban.viewmodel.Grid4DesignViewModel;

import javafx.beans.binding.DoubleBinding;

import static java.lang.Math.min;


public class Grid4DesignView extends GridView {

    Grid4DesignView(Board4DesignViewModel board4DesignViewModel, Grid4DesignViewModel grid4DesignViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight) {
        super(board4DesignViewModel, grid4DesignViewModel, gridWidth, gridHeight);
    }
}
