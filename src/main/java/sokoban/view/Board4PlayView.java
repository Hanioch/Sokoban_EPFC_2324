package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.viewmodel.Board4PlayViewModel;
import sokoban.model.Movable.Direction;



public class Board4PlayView extends BoardView {
    private static  int SCENE_MIN_WIDTH = 1000;
    private static  int SCENE_MIN_HEIGHT = 800;
    private Board4PlayViewModel board4PlayViewModel;
    private  Stage secondaryStage;
    private  Stage primaryStage;
    private Button finishButton = new Button("Finish");
    private Button showMushroomButton = new Button("Show mushroom");

    private Label scoreLabel = new Label("Score");
    private Label movesLabel = new Label();
    private Label goalsLabel = new Label();
    private Label winMessageLabel = new Label();
    private Label messageWalkOnMushroom = new Label("you walk on a mushroom ");
    private HBox bottomContainer = new HBox();
    private VBox topContainer = new VBox();
    public Board4PlayView(Stage secondaryStage,Stage primaryStage, Board4PlayViewModel board4PlayViewModel) {
        this.board4PlayViewModel = board4PlayViewModel;
        this.secondaryStage = secondaryStage;
        this.primaryStage = primaryStage;

        init();
        start(secondaryStage);
        this.requestFocus();
    }
    private void init() {
        finishButton.setPrefSize(80,30);
        finishButton.setOnAction(event -> onFinishClicked());

        BooleanProperty isMushroomVisible = board4PlayViewModel.isMushroomVisible();

        isMushroomVisible.addListener((observable, oldValue, newValue)->{
            if (newValue) showMushroomButton.setText("Hide Mushroom");
            else showMushroomButton.setText("Show Mushroom");
        });
        showMushroomButton.setPrefSize(150,30);
        showMushroomButton.setOnAction(event->board4PlayViewModel.showMushroom());
        showMushroomButton.setFocusTraversable(false);


        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.getChildren().addAll(finishButton, showMushroomButton);
        bottomContainer.setSpacing(20);

        setBottom(bottomContainer);

        topContainer.setAlignment(Pos.CENTER_LEFT);
        topContainer.setSpacing(10);
        topContainer.setPadding(new Insets(10,0,0,300));

        scoreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        movesLabel.textProperty().bind(board4PlayViewModel.moves().asString("Number of moves played: %d"));
        goalsLabel.textProperty().bind(board4PlayViewModel.boxOnTarget().asString("Number of goals reached: %d"+" of "+ board4PlayViewModel.totalTarget().get()));
        messageWalkOnMushroom.visibleProperty().bind(board4PlayViewModel.isStone());
        messageWalkOnMushroom.managedProperty().bind(board4PlayViewModel.isStone());
        topContainer.getChildren().addAll(scoreLabel, movesLabel, goalsLabel,winMessageLabel,messageWalkOnMushroom);
        winMessageLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        winMessageLabel.setPrefSize(800,30);
        setTop(topContainer);
    }

    private void onFinishClicked() {
        secondaryStage.close();
        primaryStage.show();
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
        createGrid();
        configureKeyListeners();
    }

    private void createGrid() {
        int GRID_WIDTH = board4PlayViewModel.gridWidth();
        int GRID_HEIGHT = board4PlayViewModel.gridHeight();
        DoubleBinding gridWidth = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(widthProperty().get() , heightProperty().get()-topContainer.heightProperty().get()-bottomContainer.heightProperty().get());
                    return Math.floor(size / GRID_WIDTH) * GRID_WIDTH;
                },
                widthProperty(),
                heightProperty());

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(heightProperty().get()-topContainer.heightProperty().get()-bottomContainer.heightProperty().get() , widthProperty().get());
                    return Math.floor(size / GRID_HEIGHT) * GRID_HEIGHT;
                },
                widthProperty(),
                heightProperty());

        Grid4PlayView grid4PlayView = new Grid4PlayView(board4PlayViewModel, board4PlayViewModel.getGridViewModel(), gridWidth, gridHeight);

        grid4PlayView.setAlignment(Pos.CENTER);
        setCenter(grid4PlayView);
    }

    private void configureKeyListeners() {
        this.setOnKeyPressed(event -> {
            if (!board4PlayViewModel.gameWon().get()) {
                KeyCode keyCode = event.getCode();
                if(event.isControlDown() && keyCode == KeyCode.Z) {
                    board4PlayViewModel.undo();
                } else if (event.isControlDown() && keyCode == KeyCode.Y){
                    board4PlayViewModel.redo();
                } else {
                    switch (keyCode) {
                        case Z, UP:
                            board4PlayViewModel.movePlayer(Direction.UP);
                            break;
                        case Q, LEFT:
                            board4PlayViewModel.movePlayer(Direction.LEFT);
                            break;
                        case D, RIGHT:
                            board4PlayViewModel.movePlayer(Direction.RIGHT);
                            break;
                        case S, DOWN:
                            board4PlayViewModel.movePlayer(Direction.DOWN);
                            break;
                        default:
                            return;
                    }
                }
                checkGameStatus();
                event.consume();
            }
        });
    }


    private void checkGameStatus() {
        if (board4PlayViewModel.gameWon().get()) {
            winMessageLabel.setText("You won in " + board4PlayViewModel.moves().get() + " moves, congratulations!");
        }
    }
}
