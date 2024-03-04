package sokoban.view;

import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;
import sokoban.model.*;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class GridView extends GridPane {
    public GridView( GridViewModel viewModel) {
        int width = Grid.GRID_WIDTH;
        int height = Grid.GRID_HEIGHT;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                CellView cellView = new CellView();
                viewModel.valueProperty(i, j).addListener((obs, oldVal, newVal) -> cellView.updateImage(newVal));
                this.add(cellView, i, j);
            }
        }
    }
}
