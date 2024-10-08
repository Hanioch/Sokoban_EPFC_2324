package sokoban.view;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import sokoban.model.*;
import sokoban.viewmodel.Board4DesignViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sokoban.viewmodel.Board4PlayViewModel;

import java.io.File;
import java.util.Optional;


public class Board4DesignView extends BoardView {
    private static Board4DesignViewModel board4DesignViewModel;
    private static  int SCENE_MIN_WIDTH = 1000;
    private static  int SCENE_MIN_HEIGHT = 800;
    private static  int SCENE_MAX_WIDTH = 1400;
    private static  int SCENE_MAX_HEIGHT = 1050;
    private  Label headerLabel = new Label("");
    private  HBox headerBox = new HBox();
    private  VBox toolBox = new VBox();
    private  VBox top = new VBox();
    private final VBox errorBox = new VBox();
    private HBox buttonsBox = new HBox();
    private StackPane selectedTool;
    private Stage primaryStage;
    private Button playButton;
    private Button clearButton;
    private Button randomButton;
    public Board4DesignView(Stage primaryStage, Board4DesignViewModel board4DesignViewModel) {
        this.primaryStage = primaryStage;
        this.board4DesignViewModel = board4DesignViewModel;
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
        createPlayButton();
        createClearButton();
        createRandomButton();
    }
    private void updateWindowTitle() {
        String title = "Sokoban";
        if (board4DesignViewModel.isModifiedProperty().get()) {
            title += " (*)";
        }
        primaryStage.setTitle(title);
    }
    private void setupModificationListener() {
        board4DesignViewModel.isModifiedProperty().addListener((obs, oldVal, newVal) -> {
            updateWindowTitle();
        });
    }
    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        MenuItem newItem = new MenuItem("New...");
        newItem.setOnAction(e -> {newGrid();});
        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e -> {openGrid();});
        MenuItem saveAsItem = new MenuItem("Save As...");
        saveAsItem.setOnAction(e -> {saveGrid();});
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        fileMenu.getItems().addAll(newItem, openItem, saveAsItem, exitItem);
        menuBar.getMenus().add(fileMenu);
        top.getChildren().add(menuBar);
        top.setSpacing(20);
        // top.setStyle("-fx-border-color: red;");
    }
    private boolean confirmSaveChanges() {
        if (!board4DesignViewModel.isModifiedProperty().get()) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Your board has been modified.");
        alert.setContentText("Do you want to save your changes?");

        ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            return saveGrid();
        } else return result.isPresent() && result.get() == buttonNo;
    }
    private void newGrid() {
        if (confirmSaveChanges()) {
            NewGameView dialog = new NewGameView();
            int[] dimensions = dialog.showDimension();
            if (dimensions.length == 2) {
                int width = dimensions[0];
                int height = dimensions[1];
                board4DesignViewModel.createNewGrid(width, height);
                refreshView();
            }
        }
    }

    private void openGrid() {
        if (confirmSaveChanges()) {
            boolean success = openBoardFromFile();
            if (success) {
                refreshView();
            }
        }
    }
    public boolean openBoardFromFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers XSB (*.xsb)", "*.xsb");
        fileChooser.getExtensionFilters().add(filter);

        File openFile = fileChooser.showOpenDialog(stage);
        if (openFile != null) {
            return board4DesignViewModel.loadBoardFromFile(openFile);
        }
        return false;
    }
    private boolean saveGrid() {
        boolean ok = showSaveDialog();
        if (ok) {
            board4DesignViewModel.isModifiedProperty().set(false);
            showAlert("Save Successful", "The game was saved successfully.", Alert.AlertType.INFORMATION);
            updateWindowTitle();
        } else {
            showAlert("Save Error", "Saving the game failed.", Alert.AlertType.ERROR);
        }
        return ok;
    }
    public boolean showSaveDialog() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers XSB (*.xsb)", "*.xsb");
        fileChooser.getExtensionFilters().add(filter);

        String userHomePath = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(userHomePath));

        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile != null) {
            return board4DesignViewModel.saveBoardToFile(saveFile);
        }
        return false;
    }
    private void refreshView() {
        this.setCenter(null);
        toolBox.getChildren().clear();
        headerBox.getChildren().clear();
        top.getChildren().clear();
        errorBox.getChildren().clear();
        buttonsBox.getChildren().clear();
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
        headerLabel.textProperty().bind(board4DesignViewModel.filledCellsCountProperty()
                .asString("Number of filled cells : %d of " + board4DesignViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        top.getChildren().add(headerBox);

        showError();
    }

    private void createGrid() {
        int GRID_WIDTH = board4DesignViewModel.gridWidth();
        int GRID_HEIGHT = board4DesignViewModel.gridHeight();


        DoubleBinding gridWidth = Bindings.createDoubleBinding(() -> {
                    var size = Math.min(widthProperty().get() - toolBox.widthProperty().get(), heightProperty().get()
                            - top.heightProperty().get() - buttonsBox.heightProperty().get());
                    return Math.floor(size / GRID_WIDTH) * GRID_WIDTH;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());

        DoubleBinding gridHeight = Bindings.createDoubleBinding(() -> {
                    var size = Math.min(heightProperty().get() - top.heightProperty().get() - buttonsBox.heightProperty().get(), widthProperty().get()
                            - toolBox.widthProperty().get());
                    return Math.floor(size / GRID_HEIGHT) * GRID_HEIGHT;
                },
                widthProperty(),
                heightProperty(),
                headerBox.heightProperty());

        Grid4DesignView grid4DesignView = new Grid4DesignView(board4DesignViewModel, board4DesignViewModel.getGridViewModel(), gridWidth, gridHeight);
        grid4DesignView.setAlignment(Pos.CENTER);
        setCenter(grid4DesignView);
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
                board4DesignViewModel.setSelectedElement(selectedElement);
            });


            toolBox.getChildren().add(imageContainer);
            if (elemPath.equals("wall.png")) {
                selectedTool = imageContainer;
                selectedTool.setStyle("-fx-border-color: blue; -fx-border-width: 5;");
                Element selectedElement = selectedElementFromImagePath(elemPath);
                board4DesignViewModel.setSelectedElement(selectedElement);
            }
        }
        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(20));
        toolBox.setSpacing(20);
        setLeft(toolBox);
    }
    private void createPlayButton() {
        playButton = new Button("Play");
        playButton.setPrefSize(80,30);
        buttonsBox.getChildren().add(playButton);
        playButton.setOnAction(e -> onPlayClicked());
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setSpacing(20);
        playButton.disableProperty().bind(board4DesignViewModel.isAnError());

        setBottom(buttonsBox);
    }

    private void createClearButton() {
        clearButton = new Button("Clear");
        clearButton.setPrefSize(80,30);
        buttonsBox.getChildren().add(clearButton);
        clearButton.setOnAction(e -> onClearClicked());
        clearButton.disableProperty().bind(board4DesignViewModel.isEmpty());

        setBottom(buttonsBox);
    }
    private void createRandomButton() {
        randomButton = new Button("Random Grid");
        randomButton.setPrefSize(130,30);
        buttonsBox.getChildren().add(randomButton);
        randomButton.setOnAction(e -> onRandomClicked());

        setBottom(buttonsBox);
    }

    private void onPlayClicked() {
        if (board4DesignViewModel.isModifiedProperty().get()) {
            boolean proceed = confirmSaveChanges();
            if (proceed) {
                primaryStage.hide();
                Stage playStage = new Stage();
                startPlay(playStage);

            }
        }else {
            primaryStage.hide();
            Stage playStage = new Stage();
            startPlay(playStage);
        }

    }

    private void onClearClicked() {
        board4DesignViewModel.clearGrid();
        board4DesignViewModel.isModifiedProperty().set(false);
    }
    private void onRandomClicked() {
        board4DesignViewModel.createRandomGrid();
        board4DesignViewModel.isModifiedProperty().set(true);
    }

    public void startPlay(Stage secondaryStage) {
        int width = board4DesignViewModel.gridWidth();
        int height = board4DesignViewModel.gridHeight();

        Board4Play board4Play = new Board4Play(width, height, board4DesignViewModel.getBoard().getGrid(), this.board4DesignViewModel.getBoard().getPlayer());
        Board4PlayViewModel vm= new Board4PlayViewModel(board4Play);
        new Board4PlayView(secondaryStage, primaryStage, vm);
    }

    private void showError(){

        BooleanBinding error = board4DesignViewModel.isAnError();
        errorBox.visibleProperty().bind(error);
        Label titleError= new Label("Please correct the following error(s)");
        errorBox.getChildren().add(titleError);
        titleError.setTextFill(Color.RED);
        errorBox.managedProperty().bind(errorBox.visibleProperty());

        fillLabelError("A player is required", board4DesignViewModel.isCharacterMissed());
        fillLabelError("At least one target is required", board4DesignViewModel.isTargetMissed());
        fillLabelError("At least one box required", board4DesignViewModel.isBoxMissed());
        fillLabelError("Number of boxes and targets must be equals", board4DesignViewModel.isSameNumberOfBoxAndTarget());
        errorBox.setAlignment(Pos.CENTER);
        top.getChildren().add(errorBox);
    }

    public void fillLabelError(String msg ,BooleanBinding bool){
        Label newLabel = new Label("\u2022 "+msg);
        errorBox.getChildren().add(newLabel);
        newLabel.visibleProperty().bind(bool);
        newLabel.managedProperty().bind(newLabel.visibleProperty());
        newLabel.setTextFill(Color.RED);
    }
}
