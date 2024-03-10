package sokoban.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseButton;
import sokoban.model.CellValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import sokoban.viewmodel.GridViewModel;

import java.util.Objects;


public class CellView extends StackPane {
    private final int rowIndex;
    private final int columnIndex;
    private final ColorAdjust colorAdjust = new ColorAdjust();
    private final ObjectProperty<CellValue> cellValue = new SimpleObjectProperty<>();
    private final ImageView toolImageView = new ImageView();
    private final ImageView overlayImageView = new ImageView();
    private ImageView backgroundImageView = new ImageView();
    private static final ObjectProperty<CellValue> selectedTool = new SimpleObjectProperty<>(CellValue.GROUND);
    private final GridViewModel viewModel;
    public CellView(int rowIndex, int columnIndex,GridViewModel viewModel) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.viewModel = viewModel;
        Image groundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ground.png")));
        backgroundImageView = new ImageView(groundImage);
        cellValue.addListener((obs, oldVal, newVal) -> updateImage(newVal));
        this.getChildren().addAll(backgroundImageView, toolImageView, overlayImageView);

        setupMouseEvents();
    }

    public static void setSelectedTool(CellValue tool) {
        selectedTool.set(tool);
    }
    public ObjectProperty<CellValue> getCellValue() {
        return cellValue;
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
        this.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                viewModel.setValue(rowIndex, columnIndex, selectedTool.get());
                processCellUpdate(selectedTool.get(), cellValue.get());
                updateImage(cellValue.get());
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND, cellValue.get());
                updateImage(CellValue.GROUND);
                overlayImageView.setImage(null);
                viewModel.setValue(rowIndex, columnIndex, CellValue.GROUND);
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

        this.setOnMouseEntered(e -> {
            colorAdjust.setBrightness(-0.2);
            applyEffects();
        });

        this.setOnMouseExited(e -> {
            colorAdjust.setBrightness(0);
            applyEffects();
        });


        this.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                processCellUpdate(selectedTool.get(), cellValue.get());
               updateImage(cellValue.get());
            } else if (e.getButton() == MouseButton.SECONDARY) {
                processCellUpdate(CellValue.GROUND, cellValue.get());
               updateImage(CellValue.GROUND);
                overlayImageView.setImage(null);
            }
        });
    }
    void processCellUpdate(CellValue newCellValue, CellValue currentCellValue) {
            if (newCellValue == CellValue.TARGET) {
                if (currentCellValue != CellValue.WALL) {
                    overlayImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/goal.png"))));
                    viewModel.getGrid().setOverlay(rowIndex, columnIndex, newCellValue);
                }
            } else if (currentCellValue == CellValue.TARGET) {
                if (newCellValue != CellValue.BOX && newCellValue != CellValue.PLAYER) {
                    overlayImageView.setImage(null);
                    viewModel.getGrid().setOverlay(rowIndex, columnIndex, null);
                } else {
                    overlayImageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/goal.png"))));
                    viewModel.getGrid().setOverlay(rowIndex, columnIndex, newCellValue);
                }
            } else {
                cellValue.set(newCellValue);
                updateImage(newCellValue);
                overlayImageView.setImage(null);
                viewModel.getGrid().setOverlay(rowIndex, columnIndex, null);
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
    private void applyEffects() {
        backgroundImageView.setEffect(colorAdjust);
        toolImageView.setEffect(colorAdjust);
        overlayImageView.setEffect(colorAdjust);
    }
}




