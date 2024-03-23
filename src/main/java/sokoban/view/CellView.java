package sokoban.view;

import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import sokoban.model.*;
import sokoban.model.Cell;
import sokoban.model.Element;
import sokoban.viewmodel.CellViewModel;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;

import java.util.Objects;

public class CellView extends StackPane {
    private static final Image boxImage = new Image("box.png");
    private static final Image targetImage = new Image("goal.png");
    private static final Image groundImage = new Image("ground.png");
    private static final Image playerImage = new Image("player.png");
    private static final Image wallImage = new Image("wall.png");

    private final CellViewModel viewModel;
    private final DoubleBinding widthProperty;
    private final DoubleBinding heightProperty;

    private final ImageView imageView = new ImageView();

    CellView(CellViewModel cellViewModel, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        this.viewModel = cellViewModel;
        this.widthProperty = cellWidthProperty;
        this.heightProperty = cellHeightProperty;

        setAlignment(Pos.CENTER);
        layoutControls();
        configureBindings();

        setImage(viewModel.getStack());
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
    }
    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        this.setOnMouseClicked(e -> {

            if (e.getButton() == MouseButton.PRIMARY) {
                viewModel.play();
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                viewModel.removeTopElement();
            }
            setImage(viewModel.getStack());
        });

        hoverProperty().addListener(this::hoverChanged);
    }
    public void setImage(ObservableList<Element> stack) {
        this.getChildren().clear();
        for (Element element : stack) {
            ImageView imageView = new ImageView(getImageForElement(element));
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(this.widthProperty);
            imageView.fitHeightProperty().bind(this.heightProperty);
            this.getChildren().add(imageView);
        }
    }

    private Image getImageForElement(Element element) {
        if (element instanceof Player) {
            return new Image("player.png");
        } else if (element instanceof Box) {
            return new Image("box.png");
        } else if (element instanceof Target) {
            return new Image("goal.png");
        } else if (element instanceof Wall) {
            return new Image("wall.png");
        } else if (element instanceof Ground) {
            return new Image("ground.png");
        }
        return null;
    }
    private void hoverChanged(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        // à faire : griser la cellule en survol
    }
}
