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
    Cell4DesignViewModel cellViewModel;
    Cell4DesignView(Cell4DesignViewModel cell4DesignViewModel, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        super(cellWidthProperty, cellHeightProperty);
        this.cellViewModel = cell4DesignViewModel;
        setImage(cellViewModel.getStack());
        configureBindings();
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        this.setOnMouseDragEntered(this::handleMouseEvent);
        this.setOnMouseClicked(this::handleMouseEvent);
        this.setOnMouseDragged(this::handleMouseEvent);

        this.setOnMouseEntered(e -> this.setOpacity(0.6));
        this.setOnMouseExited(e -> this.setOpacity(1.0));

        this.setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY || e.getButton() == MouseButton.SECONDARY) {
                this.startFullDrag();
                handleMouseEvent(e);
            }
        });

        cellViewModel.valueProperty().addListener((obs, old, newVal) -> setImage(cellViewModel.getStack()));
    }

    private void handleMouseEvent(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            cellViewModel.play();
        } else if (e.getButton() == MouseButton.SECONDARY) {
            cellViewModel.removeTopElement();
        }
        setImage(cellViewModel.getStack());
    }
}
