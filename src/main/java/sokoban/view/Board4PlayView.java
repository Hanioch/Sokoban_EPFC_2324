package sokoban.view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.viewmodel.Board4DesignViewModel;
import sokoban.viewmodel.Board4PlayViewModel;


public class Board4PlayView extends BoardView {
    private static  int SCENE_MIN_WIDTH = 1000;
    private static  int SCENE_MIN_HEIGHT = 800;
    private Board4PlayViewModel board4PlayViewModel;
    private  Stage secondaryStage;
    private  Stage primaryStage;
    private Button finishButton;

    private Label scoreLabel;
    private Label movesLabel;
    private Label goalsLabel;

    private HBox bottomContainer = new HBox();
    private VBox topContainer = new VBox();
    public Board4PlayView(Stage secondaryStage,Stage primaryStage, Board4PlayViewModel board4PlayViewModel) {
        this.board4PlayViewModel = board4PlayViewModel;
        this.secondaryStage = secondaryStage;
        this.primaryStage = primaryStage;


        init();
        start(secondaryStage);
    }
    private void init() {
        finishButton = new Button("Finish");
        finishButton.setPrefSize(80,30);
        finishButton.setOnAction(event -> onFinishClicked());

        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.getChildren().addAll(finishButton);
        setBottom(bottomContainer);

        topContainer.setAlignment(Pos.CENTER_LEFT);
        topContainer.setSpacing(10);
        topContainer.setPadding(new Insets(10,0,0,300));

        scoreLabel = new Label("Score");
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        movesLabel = new Label("Number of moves played: " );
        goalsLabel = new Label("Number of goals reached: " );

        topContainer.getChildren().addAll(scoreLabel, movesLabel, goalsLabel);

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
}
