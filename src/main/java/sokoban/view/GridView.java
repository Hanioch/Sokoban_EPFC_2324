package sokoban.view;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import sokoban.viewmodel.GridViewModel;
import sokoban.model.*;
import javafx.scene.layout.GridPane;

public class GridView extends GridPane {
    public GridView( GridViewModel viewModel) {
        int width = Grid.GRID_WIDTH;
        int height = Grid.GRID_HEIGHT;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                CellView cellView = new CellView();
                viewModel.valueProperty(i, j).addListener((obs, oldVal, newVal) -> cellView.updateImage(newVal));
                cellView.setOnDragOver(event -> {
                    if (event.getGestureSource() != cellView && event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                });

                int finalI = i;
                int finalJ = j;
                cellView.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        CellValue value = CellValue.valueOf(db.getString());
                        viewModel.setValue(finalI, finalJ, value);
                        cellView.updateImage(value);
                        success = true;
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });

                this.add(cellView, i, j);
            }
        }
    }
}
