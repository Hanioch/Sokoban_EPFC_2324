package sokoban.view;

import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sokoban.model.*;
import sokoban.model.Element;
import sokoban.viewmodel.Cell4DesignViewModel;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sokoban.viewmodel.CellViewModel;

public class Cell4DesignView extends CellView {


    Cell4DesignView(Cell4DesignViewModel cell4DesignViewModel, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        super(cell4DesignViewModel,cellWidthProperty, cellHeightProperty);
    }
}
