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
import java.util.Optional;

import static sokoban.view.GridView.PADDING;


public class BoardView extends BorderPane {
    private  BoardViewModel boardViewModel;
    private static  int SCENE_MIN_WIDTH = 800;
    private static  int SCENE_MIN_HEIGHT = 600;
    private static  int SCENE_MAX_WIDTH = 1400;
    private static  int SCENE_MAX_HEIGHT = 1050;
    private  Label headerLabel = new Label("");
    private  HBox headerBox = new HBox();
    private  VBox toolBox = new VBox();
    private  VBox top = new VBox();
    private StackPane selectedTool;
    private  Stage primaryStage;
    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.primaryStage = primaryStage;
        this.boardViewModel = boardViewModel;
        start(primaryStage);
    }

    private void start(Stage stage) {
        stage.setTitle("Sokoban");
        configMainComponents();
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void configMainComponents() {
        setupModificationListener();
        createMenuBar();
        createGrid();
        createHeader();
        createToolBox();
        setTop(top);
    }
    private void setupModificationListener() {
        boardViewModel.isModifiedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                primaryStage.setTitle("Sokoban (*)");
            }
        });
    }
    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem newItem = new MenuItem("New...");
        newItem.setOnAction(e -> {
            newGrid();

        });
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> {
            openGrid();
        });
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction(e -> {
            saveGrid();
        });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        fileMenu.getItems().addAll(newItem, openItem, saveAsItem, exitItem);
        menuBar.getMenus().add(fileMenu);
        top.getChildren().add(menuBar);
        top.setSpacing(10);
    }
    private boolean openGrid() {
        if (boardViewModel.isModifiedProperty().get()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Your board has been modified");
            alert.setContentText("Do you want to save your changes?");
            ButtonType buttonYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
            ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
            ButtonType buttonCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                return saveGrid();
            } else if (result.isPresent() && result.get() == buttonCancel) {
                return false;
            }
        }

        boolean success = OpenDialog.openBoardFromFile(boardViewModel, this);
        refreshView();
        return success;
    }

    private boolean saveGrid() {
        boolean saveSuccessful = SaveAsDialog.showSaveDialog(boardViewModel);
        if (saveSuccessful) {
            showAlert("Save Successful", "The game was saved successfully.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Save Error", "Saving the game failed.", Alert.AlertType.ERROR);
        }
        return saveSuccessful;
    }
    private boolean newGrid() {
        if (boardViewModel.isModifiedProperty().get()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Your board has been modified");
            alert.setContentText("Do you want to save your changes?");
            ButtonType buttonYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
            ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.NO);
            ButtonType buttonCancel = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                return saveGrid();
            } else if (result.isPresent() && result.get() == buttonCancel) {
                return false;
            }
        }
        NewGameDialog newGameDialog = new NewGameDialog();
        Dimension dimension = newGameDialog.showDimension();
        if (dimension != null) {
            boardViewModel.createNewGrid(dimension.width, dimension.height);
            refreshView();
        }
        return true;
    }
    private void refreshView() {
        this.setCenter(null);
        toolBox.getChildren().clear();
        headerBox.getChildren().clear();
        top.getChildren().clear();
        configMainComponents();
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
        int GRID_WIDTH = boardViewModel.gridWidth();
        int GRID_HEIGHT = boardViewModel.gridHeight();
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


        GridView gridView = new GridView( boardViewModel,boardViewModel.getGridViewModel(), gridWidth, gridHeight);

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
            if (elemPath.equals("wall.png")) {
                selectedTool = imageContainer;
                selectedTool.setStyle("-fx-border-color: blue; -fx-border-width: 5;");
                Element selectedElement = selectedElementFromImagePath(elemPath);
                boardViewModel.setSelectedElement(selectedElement);
            }
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
