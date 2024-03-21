package sokoban.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private static final int GRID_HEIGHT = BoardViewModel.gridHeight();
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 500;
    private final Label headerLabel = new Label("");
    private final HBox headerBox = new HBox();
    private final VBox toolBox = new VBox();
    private StackPane selectedTool;

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
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
        createToolBox();
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

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(heightProperty().get(), widthProperty().get() - headerBox.heightProperty().get());
                    return Math.floor(size / GRID_HEIGHT) * GRID_HEIGHT;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());

        GridView gridView = new GridView(boardViewModel.getGridViewModel(), gridWidth, gridHeight);

        gridView.minHeightProperty().bind(gridHeight);
        gridView.minWidthProperty().bind(gridWidth);
        gridView.maxHeightProperty().bind(gridHeight);
        gridView.maxWidthProperty().bind(gridWidth);

        setCenter(gridView);
    }

    private void createToolBox() {
        String[] elements = {"ground.png", "goal.png", "wall.png", "player.png", "box.png"};
        for (String elemPath : elements) {
            Image image = new Image(elemPath);
            ImageView imageView = new ImageView(image);
            StackPane imageContainer = new StackPane(imageView);

            imageContainer.setOnMouseClicked(e -> {
                selectedTool = imageContainer;
            });
//            imageContainer.setOnDragDetected(event -> {
//                Dragboard db = imageContainer.startDragAndDrop(TransferMode.ANY);
//                ClipboardContent content = new ClipboardContent();
//                content.putString(convertPathToCellValue(elementPath).name());
//                db.setContent(content);
//                event.consume();
//            });

            toolBox.getChildren().add(imageContainer);
        }
        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(20));
        setLeft(toolBox);
    }
}
