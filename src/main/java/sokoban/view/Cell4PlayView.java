package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import sokoban.model.Element;
import sokoban.model.Mushroom;
import sokoban.viewmodel.Cell4PlayViewModel;

public class Cell4PlayView extends CellView{
    Cell4PlayViewModel cellViewModel;
    Cell4PlayView(Cell4PlayViewModel cell4PlayViewModel, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        super(cellWidthProperty, cellHeightProperty);
        this.cellViewModel = cell4PlayViewModel;
        ObservableList<Element> stack = cellViewModel.getStack();
        setImage(stack);
        configureBindings();
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);
        cellViewModel.valueProperty().addListener((obs, old, newVal) -> setImage(cellViewModel.getStack()));
    }
}
