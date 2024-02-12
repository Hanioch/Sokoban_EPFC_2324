package sokoban.view;

import sokoban.viewmodel.BoardViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;


public class BoardView extends BorderPane {
    private final BoardViewModel boardViewModel;
    private static final int GRID_WIDTH = BoardViewModel.gridWidth();
    private static final int SCENE_MIN_WIDTH = 420;
    private static final int SCENE_MIN_HEIGHT = 420;
    private final Label headerLabel = new Label("");
    private final HBox headerBox = new HBox();

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.BoardViewModel = boardViewModel;
        start(primaryStage);
    }

    private void start(Stage stage) {
        configMainComponents(stage);

        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void configMainComponents(Stage stage) {
        stage.setTitle("SOKOBAN");
        createGrid();
        createHeader();
    }

    private void createHeader() {
        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells : %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        setTop(headerBox);
    }

    private void createGrid() {
        DoubleBinding gridWidth = Bindings.createDoubleBinding(
            () -> {
                    var size = Math.min(widthProperty().get(), heightProperty().get() - headerBox.heightProperty().get());
                    return Math.floor(size / GRID_WIDTH) * GRID_WIDTH;
        },
        widthProperty(),
        heightProperty(),
        headerBox.heightProperty());

        GridView gridView = new GridView(boardViewModel.getGridViewModel(), gridWidth);

        gridView.minHeightProperty().bind(gridWidth);
        gridView.minWidthProperty().bind(gridWidth);
        gridView.maxHeightProperty().bind(gridWidth);
        gridView.maxWidthProperty().bind(gridWidth);

        setCenter(gridView);
    }
}
