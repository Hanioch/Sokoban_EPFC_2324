package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import sokoban.viewmodel.Cell4PlayViewModel;
import sokoban.viewmodel.CellViewModel;

public class Cell4playView extends CellView{
    Cell4playView(Cell4PlayViewModel cell4PlayViewModel, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        super(cell4PlayViewModel,cellWidthProperty, cellHeightProperty);
    }
}
