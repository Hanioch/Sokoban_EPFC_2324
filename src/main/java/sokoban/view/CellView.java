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
        overlayImageView.setEffect(colorAdjust);
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

            }
        });

        this.setOnMouseDragOver(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                processCellUpdate(selectedTool.get(), cellValue.get());
                updateImage(cellValue.get());
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND, cellValue.get());
                updateImage(CellValue.GROUND);
                overlayImageView.setImage(null);
            }
        });

        this.setOnMouseEntered(e -> colorAdjust.setBrightness(-0.2));
        this.setOnMouseExited(e -> colorAdjust.setBrightness(0));

        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                processCellUpdate(selectedTool.get(), cellValue.get());
                updateImage(cellValue.get());
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND, cellValue.get());
                updateImage(CellValue.GROUND);
                overlayImageView.setImage(null);
            }
        });


        this.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                processCellUpdate(selectedTool.get(), cellValue.get());
                updateImage(cellValue.get());
            } else if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND, cellValue.get());
                updateImage(CellValue.GROUND);
            }
        });
    }
    private void processCellUpdate(CellValue newCellValue, CellValue currentCellValue) {
        if (newCellValue != currentCellValue) {
            if (newCellValue == CellValue.TARGET) {
                if (currentCellValue != CellValue.WALL) {
                    overlayImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/goal.png"))));
                }
            } else if (currentCellValue == CellValue.TARGET) {
                if (newCellValue != CellValue.BOX && newCellValue != CellValue.PLAYER) {
                    overlayImageView.setImage(null);
                } else {
                    overlayImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/goal.png"))));
                }
            } else {
                cellValue.set(newCellValue);
                updateImage(newCellValue);
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




