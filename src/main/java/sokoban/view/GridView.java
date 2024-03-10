package sokoban.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Window;
import sokoban.viewmodel.GridViewModel;
import sokoban.model.*;
import sokoban.view.CellView;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.GridViewModel;
import sokoban.model.CellValue;

import static sokoban.model.Grid.GRID_HEIGHT;
import static sokoban.model.Grid.GRID_WIDTH;

public class GridView extends GridPane {

    private final GridViewModel viewModel;


    public GridView(GridViewModel viewModel) {
        this.viewModel = viewModel;
        initializeGrid(viewModel.getGrid().getWidth(), viewModel.getGrid().getHeight());
    }

    public void initializeGrid(int numCols, int numRows) {

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                CellView cellView = new CellView(i,j, viewModel);
                setupCellListeners(cellView, i, j);
                this.add(cellView, i, j);
            }
        }

        this.parentProperty().addListener((obs, oldParent, newParent) -> {
            if (newParent instanceof Region parentRegion) {
                VBox.setVgrow(this, Priority.ALWAYS);
                parentRegion.widthProperty().addListener((observable, oldValue, newValue) -> {
                    double parentWidth = parentRegion.getWidth();
                    double parentHeight = parentRegion.getHeight();
                    adjustGridSize(parentWidth, parentHeight);
                });
                parentRegion.heightProperty().addListener((observable, oldValue, newValue) -> {
                    double parentWidth = parentRegion.getWidth();
                    double parentHeight = parentRegion.getHeight();
                    adjustGridSize(parentWidth, parentHeight);
                });
                this.setStyle("-fx-spacing: 0");
            }
        });
    }

    private void adjustGridSize(double parentWidth, double parentHeight) {
        double targetHeight = GRID_HEIGHT;
        double targetWidth = parentWidth * targetHeight / parentHeight;
        this.setPrefWidth(targetWidth);
        this.setPrefHeight(targetHeight);

        if (targetHeight > parentHeight) {
            targetHeight = parentHeight;
            targetWidth =  parentWidth * targetHeight / parentHeight;
        }

        this.setPrefWidth(targetWidth);
        this.setPrefHeight(targetHeight);

        adjustCellSizes(targetWidth, targetHeight);
    }


    private void adjustCellSizes(double width, double height) {
        double cellWidth = width / viewModel.getGrid().getWidth();
        double cellHeight = height / viewModel.getGrid().getHeight();

        for (Node child : this.getChildren()) {
            if (child instanceof CellView cell) {
                cell.setPrefSize(cellWidth, cellHeight);
            }
        }
    }


    private void setupCellListeners(CellView cellView, int i, int j) {
        viewModel.valueProperty(i, j).addListener((obs, oldVal, newVal) -> {
            cellView.updateImage(newVal);
            System.out.println(newVal);
        });

        cellView.setOnDragOver(event -> {
            if (event.getGestureSource() != cellView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        cellView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
                if (db.hasString()) {
                    CellValue value = CellValue.valueOf(db.getString());
                    viewModel.setValue(i, j, value);
                    cellView.processCellUpdate(value, cellView.getCellValue().get());
                    success = true;
                }
            event.setDropCompleted(success);
            event.consume();
        });

    }
}

