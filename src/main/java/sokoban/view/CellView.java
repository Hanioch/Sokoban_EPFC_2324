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
        cellViewModel.stackProperty().addListener((observable, oldValue, newValue) -> {
            setImage(newValue);
        });
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
//        imageView.fitWidthProperty().bind(widthProperty);

        // copié du tuto Grid, à voir comment importer le bon élément et comment valider
        /*
        this.setOnMouseClicked(e -> viewModel.play());

        viewModel.valueProperty().addListener((obs, old, newVal) -> setImage(imageView, newVal));
        */

        hoverProperty().addListener(this::hoverChanged);
    }

    //renvoie d'office l'image du wall, à voir comment récupérer la bonne image
    public void setImage(ObservableList<Element> stack) {
        // Efface l'image existante pour éviter les superpositions d'images
        imageView.setImage(null);

        // Parcours la pile d'éléments pour afficher les images
        for (Element element : stack) {
            if (element instanceof Player) {
                imageView.setImage(new Image("chemin_vers_image_joueur"));
                break; // Arrête la recherche après avoir trouvé le joueur
            } else if (element instanceof Box) {
                imageView.setImage(new Image("chemin_vers_image_caisse"));
                break; // Arrête la recherche après avoir trouvé la caisse
            } else if (element instanceof Target) {
                imageView.setImage(new Image("chemin_vers_image_cible"));
                break; // Arrête la recherche après avoir trouvé la cible
            } else if (element instanceof Wall) {
                imageView.setImage(new Image("chemin_vers_image_mur"));
                break; // Arrête la recherche après avoir trouvé le mur
            } else if (element instanceof Ground) {
                imageView.setImage(new Image("chemin_vers_image_terrain"));
                break; // Arrête la recherche après avoir trouvé le terrain
            }
            // Vous pouvez ajouter des conditions pour d'autres types d'éléments si nécessaire
        }
    }

    private void hoverChanged(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        // à faire : griser la cellule en survol
    }

}
