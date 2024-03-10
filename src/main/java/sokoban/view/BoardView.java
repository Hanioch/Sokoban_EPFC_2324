package sokoban.view;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import sokoban.model.Board;
import sokoban.model.CellValue;
import sokoban.viewmodel.BoardViewModel;
import javafx.geometry.Pos;
import sokoban.viewmodel.GridViewModel;


import java.awt.*;
import java.util.Objects;
import java.util.Optional;


public class BoardView extends BorderPane {

    private final VBox mainVBox;
    private final MenuBar menuBar;
    private final VBox errorVBox;
    private final Label errorLabel;
    private final HBox contentArea;
    private final VBox toolbox;
    private BoardViewModel boardViewModel;
    private GridView gridView;
    private StackPane selectedTool;
    public BoardView(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.mainVBox = new VBox();
        this.menuBar = createMenuBar();
        this.errorVBox = new VBox();
        this.errorLabel = new Label();
        this.contentArea = new HBox();
        this.toolbox = initializeToolbox();
        this.gridView = new GridView(boardViewModel.getGridViewModel());
        setupLayout();
        setupStyles();
        updateBoardView(boardViewModel);
    }

    private void setupLayout() {
        errorLabel.setText("Message d'erreur ici");
        errorLabel.setVisible(false);
        errorVBox.getChildren().add(errorLabel);
        errorVBox.setAlignment(Pos.CENTER);

        mainVBox.getChildren().addAll(menuBar, errorVBox, contentArea);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        mainVBox.setAlignment(Pos.CENTER);

        contentArea.getChildren().addAll(toolbox, gridView);
        contentArea.setSpacing(10);

        HBox.setHgrow(gridView, Priority.ALWAYS);
        contentArea.setAlignment(Pos.CENTER);
        HBox.setMargin(toolbox, new Insets(0, 10, 0, 0));
        HBox.setMargin(gridView, new Insets(0, 0, 0, 10));
        this.setCenter(mainVBox);
    }

    private void setupStyles() {
        mainVBox.setAlignment(Pos.CENTER);
        errorVBox.setStyle("-fx-spacing: 10");
        contentArea.setAlignment(Pos.CENTER);
        toolbox.setAlignment(Pos.CENTER);
        gridView.setStyle("-fx-spacing: 5");
        gridView.setAlignment(Pos.CENTER);
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Files");
        MenuItem newMenuItem = new MenuItem("New...");
        MenuItem openMenuItem = new MenuItem("Open...");
        MenuItem saveAsMenuItem = new MenuItem("Save As...");
        MenuItem exitMenuItem = new MenuItem("Exit");
        menuBar.getMenus().add(fileMenu);
        newMenuItem.setOnAction(event -> {
            NewGameDialog newGameDialog = new NewGameDialog();
            Dimension dimension = newGameDialog.showDimension();
            if (dimension != null) {
                createBoard(dimension.width, dimension.height);
            }
        });
        saveAsMenuItem.setOnAction(event -> {
            boolean saveSuccessful = SaveAsDialog.showSaveDialog(boardViewModel);
            if (saveSuccessful) {
                showAlert("Sauvegarde réussie", "Le jeu a été sauvegardé avec succès.", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Erreur de sauvegarde", "La sauvegarde du jeu a échoué.", Alert.AlertType.ERROR);
            }
        });
        exitMenuItem.setOnAction(event -> {
            Platform.exit();
        });
        openMenuItem.setOnAction(event -> {
            boolean openSuccessful = OpenDialog.openBoardFromFile(boardViewModel, this);
            if (openSuccessful) {
                updateBoardView(boardViewModel);
            } else {
                showAlert("Erreur d'ouverture", "Impossible d'ouvrir le fichier.", Alert.AlertType.ERROR);
            }
        });
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveAsMenuItem, exitMenuItem);

        return menuBar;
    }
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Pas de texte d'en-tête nécessaire
        alert.setContentText(message);
        alert.showAndWait();
    }
    private VBox initializeToolbox() {
        String[] elements = {"ground.png", "goal.png", "wall.png", "player.png", "box.png"};
        VBox toolbox = new VBox(15);
        for (String elementPath : elements) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + elementPath)), 50, 50, true, true);
            ImageView imageView = new ImageView(image);
            StackPane imageContainer = new StackPane(imageView);
            imageContainer.setUserData(elementPath);

            imageContainer.setOnMouseClicked(e -> {
                if (selectedTool != null) {
                    selectedTool.getStyleClass().remove("selected-tool");
                    selectedTool.setPadding(Insets.EMPTY);
                }
                imageContainer.getStyleClass().add("selected-tool");
                selectedTool = imageContainer;

                CellValue tool = convertPathToCellValue(elementPath);
                CellView.setSelectedTool(tool);
            });
            imageContainer.setOnDragDetected(event -> {
                Dragboard db = imageContainer.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(convertPathToCellValue(elementPath).name());
                db.setContent(content);
                event.consume();
            });

            toolbox.getChildren().add(imageContainer);
        }
        return toolbox;
    }
    private CellValue convertPathToCellValue(String imagePath) {
        return switch (imagePath) {
            case "wall.png" -> CellValue.WALL;
            case "box.png" -> CellValue.BOX;
            case "player.png" -> CellValue.PLAYER;
            case "goal.png" -> CellValue.TARGET;
            default -> CellValue.GROUND;
        };
    }
    private void createBoard(int width, int height) {
        Board newBoard = new Board(width, height);
        BoardViewModel newBoardViewModel = new BoardViewModel(newBoard);
        GridView newGridView = new GridView(newBoardViewModel.getGridViewModel());
        newGridView.setStyle("-fx-spacing: 0;");
        newGridView.setAlignment(Pos.CENTER);

        contentArea.getChildren().set(1, newGridView);
        contentArea.requestLayout();

        this.gridView = newGridView;
    }



    public void updateBoardView(BoardViewModel newBoardViewModel) {
        this.boardViewModel = newBoardViewModel;
        GridView newGridView = new GridView(newBoardViewModel.getGridViewModel()); // Pass actual dimensions
        newGridView.setStyle("-fx-spacing: 0;");
        newGridView.setAlignment(Pos.CENTER);

        contentArea.getChildren().set(1, newGridView);
        contentArea.requestLayout();

        this.gridView = newGridView;

        newGridView.initializeGrid(newBoardViewModel.getGridViewModel().getGrid().getWidth(), newBoardViewModel.getGridViewModel().getGrid().getHeight()); // Call with actual dimensions
    }



    public void hideError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
}
