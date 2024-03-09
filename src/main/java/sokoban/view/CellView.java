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

    private final ColorAdjust colorAdjust = new ColorAdjust();
    private final ObjectProperty<CellValue> cellValue = new SimpleObjectProperty<>();
    private final ImageView toolImageView = new ImageView();
    private final ImageView overlayImageView = new ImageView();
    private static final ObjectProperty<CellValue> selectedTool = new SimpleObjectProperty<>(CellValue.GROUND);

    public CellView() {
        Image groundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ground.png")));
        ImageView backgroundImageView = new ImageView(groundImage);
        cellValue.addListener((obs, oldVal, newVal) -> updateImage(newVal));

        bindImageViewSize(backgroundImageView);
        bindImageViewSize(toolImageView);
        bindImageViewSize(overlayImageView);

        this.getChildren().addAll(backgroundImageView, toolImageView, overlayImageView);

        setupMouseEvents();
    }
    private void bindImageViewSize(ImageView imageView) {
        imageView.fitWidthProperty().bind(this.widthProperty());
        imageView.fitHeightProperty().bind(this.heightProperty());
        imageView.setPreserveRatio(true);
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




