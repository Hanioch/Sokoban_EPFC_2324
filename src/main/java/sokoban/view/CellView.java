package sokoban.view;

import sokoban.viewmodel.CellViewModel;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class CellView extends StackPane {
    private static final Image boxImage = new Image("box.png");
    private static final Image targetImage = new Image("goal.png");
    private static final Image groundImage = new Image("ground.png");
    private static final Image playerImage = new Image("player.png");
    private static final Image wallImage = new Image("wall.png");

    private final CellViewModel viewModel;
    private final DoubleBinding widthProperty;

    private final ImageView imageView = new ImageView();

    CellView(CellViewModel cellViewModel, DoubleBinding cellWidthProperty) {
        this.viewModel = cellViewModel;
        this.widthProperty = cellWidthProperty;
        setAlignment(Pos.CENTER);
        layoutControls();
        configureBindings();
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
    }
    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        // à voir si nécessaire car on n'a pas de notion de rescale
        imageView.fitWidthProperty().bind(widthProperty);

        // copié du tuto Grid, à voir comment importer le bon élément et comment valider
        this.setOnMouseClicked(e -> viewModel.play());

        viewModel.valueProperty().addListener((obs, old, newVal) -> setImage(imageView, newVal));

        hoverProperty().addListener(this::hoverChanged);
    }

    //renvoie d'office l'image du wall, à voir comment récupérer la bonne image
    private void setImage(ImageView imageView, CellValue cellValue) {
        imageView.setImage(cellValue == CellValue.GROUND ? null : wallImage);
    }

    private void hoverChanged(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        // à faire : griser la cellule en survol
    }

}
