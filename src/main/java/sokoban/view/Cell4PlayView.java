package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import sokoban.model.*;
import sokoban.viewmodel.Cell4PlayViewModel;
import sokoban.viewmodel.CellViewModel;

public class Cell4PlayView extends CellView{
    Cell4PlayViewModel cellViewModel;
    Cell4PlayView(Cell4PlayViewModel cell4PlayViewModel, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        super(cellWidthProperty, cellHeightProperty);
        this.cellViewModel = cell4PlayViewModel;
        setImage(cellViewModel.getStack());
        configureBindings();
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);
        cellViewModel.valueProperty().addListener((obs, old, newVal) -> setImage(cellViewModel.getStack()));
    }
}
