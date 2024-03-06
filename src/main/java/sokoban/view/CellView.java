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
    private final ImageView overlayImageView;
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

        overlayImageView = new ImageView();
        overlayImageView.setFitWidth(40);
        overlayImageView.setFitHeight(40);
        this.getChildren().addAll(backgroundImageView, toolImageView, overlayImageView);

        setupMouseEvents();
    }

    public static void setSelectedTool(CellValue tool) {
        selectedTool.set(tool);
    }

    private void setupMouseEvents() {
        this.setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                this.startFullDrag();
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                this.startFullDrag();
                e.consume();
            }
        });

        this.setOnMouseDragOver(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                processCellUpdate(selectedTool.get());
                updateImage(cellValue.get());
                e.consume();
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND);
                updateImage(CellValue.GROUND);
                e.consume();
            }
        });

        this.setOnMouseEntered(e -> colorAdjust.setBrightness(-0.2));
        this.setOnMouseExited(e -> colorAdjust.setBrightness(0));

        this.setOnMouseClicked(e -> {
            processCellUpdate(selectedTool.get());
            updateImage(cellValue.get());
            e.consume();
        });

        this.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                processCellUpdate(selectedTool.get());
                updateImage(cellValue.get());
            } else if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND);
                updateImage(CellValue.GROUND);
            }
            e.consume();
        });
    }

    private void processCellUpdate(CellValue newCellValue) {
        CellValue currentCellValue = cellValue.get();
        if (newCellValue != currentCellValue) {
            cellValue.set(newCellValue);
            if (newCellValue == CellValue.TARGET) {
                overlayImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/goal.png"))));
            } else if (currentCellValue == CellValue.TARGET) {
                overlayImageView.setImage(null);
            }
        }
    }


    public void updateImage(CellValue value) {
        if (value != null) {
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
}




