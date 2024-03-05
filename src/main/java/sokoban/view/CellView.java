package sokoban.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseButton;
import sokoban.model.CellValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

public class CellView extends StackPane {

    private final ObjectProperty<CellValue> cellValue = new SimpleObjectProperty<>();
    private final ImageView toolImageView;
    private final ColorAdjust colorAdjust = new ColorAdjust();

    private static final ObjectProperty<CellValue> selectedTool = new SimpleObjectProperty<>(CellValue.GROUND);
    public CellView() {
        Image groundImage = new Image("ground.png");
        ImageView backgroundImageView = new ImageView(groundImage);
        backgroundImageView.setFitWidth(40);
        backgroundImageView.setFitHeight(40);
        backgroundImageView.setEffect(colorAdjust);

        toolImageView = new ImageView();
        toolImageView.setFitWidth(40);
        toolImageView.setFitHeight(40);
        toolImageView.setEffect(colorAdjust);

        this.getChildren().addAll(backgroundImageView, toolImageView);

        setupMouseEvents();
    }
    public static void setSelectedTool(CellValue tool) {
        selectedTool.set(tool);
    }
    private void setupMouseEvents() {

        this.setOnDragDetected(event -> this.startFullDrag());
        this.setOnMouseDragOver(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                cellValue.set(selectedTool.get());
                updateImage(cellValue.get());
                e.consume();
            }
        });

        this.setOnMouseEntered(e -> colorAdjust.setBrightness(-0.2));
        this.setOnMouseExited(e -> colorAdjust.setBrightness(0));


        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                cellValue.set(selectedTool.get());
                updateImage(cellValue.get());
            } else if (e.getButton() == MouseButton.SECONDARY) {
                cellValue.set(CellValue.GROUND);
                updateImage(CellValue.GROUND);
            }
        });

        this.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                cellValue.set(selectedTool.get());
                updateImage(cellValue.get());
            }
        });
    }

    public void updateImage(CellValue value) {
        String imagePath = switch (value) {
            case GROUND -> "/ground.png";
            case WALL -> "/wall.png";
            case BOX -> "/box.png";
            case PLAYER -> "/player.png";
            case TARGET -> "/goal.png";
        };
        toolImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
    }

}
