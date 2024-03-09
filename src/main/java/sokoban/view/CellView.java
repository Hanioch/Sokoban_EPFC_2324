package sokoban.view;

import javafx.collections.ObservableList;
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
    /*
    private static final Image boxImage = new Image("box.png");
    private static final Image targetImage = new Image("goal.png");
    private static final Image groundImage = new Image("ground.png");
    private static final Image playerImage = new Image("player.png");
    private static final Image wallImage = new Image("wall.png");
    */

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

        // à voir si nécessaire car on n'a pas de notion de rescale
//        imageView.fitWidthProperty().bind(widthProperty);

        // copié du tuto Grid, à voir comment importer le bon élément et comment valider
        /*
        this.setOnMouseClicked(e -> viewModel.play());

        viewModel.valueProperty().addListener((obs, old, newVal) -> setImage(imageView, newVal));
        */

        hoverProperty().addListener(this::hoverChanged);
    }

    public void setImage(ObservableList<Element> stack) {

        Image groundImage = new Image("src/main/resources/ground.png");

        imageView.setImage(groundImage);

        /*
        for (Element element : stack) {
            if (element instanceof Player) {
                imageView.setImage(new Image("player.png"));
                break;
            } else if (element instanceof Box) {
                imageView.setImage(new Image("box.png"));
                break;
            } else if (element instanceof Target) {
                imageView.setImage(new Image("goal.png"));
                break;
            } else if (element instanceof Wall) {
                imageView.setImage(new Image("wall.png"));
                break;
            } else if (element instanceof Ground) {
                imageView.setImage(new Image("ground.png"));
                break;
            }
        }
        */
    }

    private void hoverChanged(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        // à faire : griser la cellule en survol
    }
}
