package sokoban.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import sokoban.model.*;
import sokoban.viewmodel.BoardViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;


public class BoardView extends BorderPane {
    private final BoardViewModel boardViewModel;
    private static final int GRID_WIDTH = BoardViewModel.gridWidth();
    private static final int GRID_HEIGHT = BoardViewModel.gridHeight();
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 600;
    private static final int SCENE_MAX_WIDTH = 1400;
    private static final int SCENE_MAX_HEIGHT = 1050;
    private final Label headerLabel = new Label("");
    private final HBox headerBox = new HBox();
    private final VBox toolBox = new VBox();
    private final VBox top = new VBox();
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
        createMenuBar();
        createGrid();
        createHeader();
        createToolBox();
        setTop(top);
    }
    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem newItem = new MenuItem("New...");
        newItem.setOnAction(e -> {
            NewGameDialog newGameDialog = new NewGameDialog();
            Dimension dimension = newGameDialog.showDimension();

        });
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> {});
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction(e -> {
            boolean saveSuccessful = SaveAsDialog.showSaveDialog(boardViewModel);
            if (saveSuccessful) {
                showAlert("Sauvegarde réussie", "Le jeu a été sauvegardé avec succès.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur de sauvegarde", "La sauvegarde du jeu a échoué.", Alert.AlertType.ERROR);
            }
        });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        fileMenu.getItems().addAll(newItem, openItem, saveAsItem, exitItem);
        menuBar.getMenus().add(fileMenu);
        top.getChildren().add(menuBar);
        top.setSpacing(10);
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void createHeader() {
        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells : %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        top.getChildren().add(headerBox);
    }

    private void createGrid() {
        DoubleBinding gridWidth = Bindings.createDoubleBinding(
            () -> {
                    var size = Math.min(widthProperty().get() - toolBox.widthProperty().get(), heightProperty().get()
                            - headerBox.heightProperty().get());
                    return Math.floor(size / GRID_WIDTH) * GRID_WIDTH;
        },
        widthProperty(),
        heightProperty(),
        headerBox.heightProperty());

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(heightProperty().get() - headerBox.heightProperty().get(), widthProperty().get()
                            - toolBox.widthProperty().get());
                    return Math.floor(size / GRID_HEIGHT) * GRID_HEIGHT;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());


        GridView gridView = new GridView(boardViewModel.getGridViewModel(), gridWidth, gridHeight);

        gridView.minHeightProperty().bind(gridHeight);
        gridView.minWidthProperty().bind(gridWidth);

        gridView.setAlignment(Pos.CENTER);
        setCenter(gridView);
    }

    private void createToolBox() {
        String[] elements = {"ground.png", "goal.png", "wall.png", "player.png", "box.png"};
        for (String elemPath : elements) {
            Image image = new Image(elemPath);
            ImageView imageView = new ImageView(image);
            StackPane imageContainer = new StackPane(imageView);

            imageContainer.setOnMouseClicked(e -> {
                if (selectedTool != null) {
                    selectedTool.setStyle("");
                }
                selectedTool = imageContainer;
                selectedTool.setStyle("-fx-border-color: blue; -fx-border-width: 5;");
                Element selectedElement = selectedElementFromImagePath(elemPath);
                boardViewModel.setSelectedElement(selectedElement);
            });


            toolBox.getChildren().add(imageContainer);
        }
        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(20));
        toolBox.setSpacing(20);
        setLeft(toolBox);
    }
    private Element selectedElementFromImagePath(String imagePath) {
        switch (imagePath) {
            case "ground.png":
                return new Ground();
            case "goal.png":
                return new Target();
            case "wall.png":
                return new Wall();
            case "player.png":
                return new Player();
            case "box.png":
                return new Box();
            default:
                return null;
        }
    }
}
