package sokoban.view;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sokoban.model.CellValue;
import sokoban.viewmodel.CellViewModel;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;

public class CellView extends StackPane {
    private ImageView imageView = new ImageView();
    private ColorAdjust colorAdjust = new ColorAdjust();
    public CellView() {
        Image groundImage = new Image("ground.png");

        imageView = new ImageView(groundImage);
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setEffect(colorAdjust);

        this.getChildren().add(imageView);

        setupMouseEvents();
    }

    private void setupMouseEvents() {
        this.setOnMouseEntered(e -> colorAdjust.setBrightness(-0.2));

        this.setOnMouseExited(e -> colorAdjust.setBrightness(0));

    }

    public void updateImage(CellValue value) {
        switch (value) {
            case GROUND:
                imageView.setImage(new Image("ground.png"));
                break;
            case WALL:
                imageView.setImage(new Image("wall.png"));
                break;
            case BOX:
                imageView.setImage(new Image("box.png"));
                break;
            case PLAYER:
                imageView.setImage(new Image("player.png"));
                break;
            case TARGET:
                imageView.setImage(new Image("goal.png"));
                break;
        }
    }

}
