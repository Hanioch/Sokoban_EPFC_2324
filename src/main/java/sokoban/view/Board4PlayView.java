package sokoban.view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.Move;
import sokoban.viewmodel.Board4DesignViewModel;
import sokoban.viewmodel.Board4PlayViewModel;


public class Board4PlayView extends BoardView {
    private static  int SCENE_MIN_WIDTH = 1000;
    private static  int SCENE_MIN_HEIGHT = 800;
    private Board4PlayViewModel board4PlayViewModel;
    private  Stage secondaryStage;
    private  Stage primaryStage;
    private Button finishButton;
    public Board4PlayView(Stage secondaryStage,Stage primaryStage, Board4PlayViewModel board4PlayViewModel) {
        this.board4PlayViewModel = board4PlayViewModel;
        this.secondaryStage = secondaryStage;
        this.primaryStage = primaryStage;


        finishButton = new Button("Finish");
        finishButton.setOnAction(event -> onFinishClicked());

        HBox bottomContainer = new HBox(finishButton);
        bottomContainer.setAlignment(Pos.CENTER);
        setBottom(bottomContainer);
        start(secondaryStage);
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
        setupKeyEvents();
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
                    var size = Math.min(widthProperty().get() , heightProperty().get());
                    return Math.floor(size / GRID_WIDTH) * GRID_WIDTH;
                },
                widthProperty(),
                heightProperty());

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> {
                    var size = Math.min(heightProperty().get() , widthProperty().get());
                    return Math.floor(size / GRID_HEIGHT) * GRID_HEIGHT;
                },
                widthProperty(),
                heightProperty());

        Grid4PlayView grid4PlayView = new Grid4PlayView(board4PlayViewModel, board4PlayViewModel.getGridViewModel(), gridWidth, gridHeight);

        //grid4PlayView.minHeightProperty().bind(gridHeight);
        //grid4PlayView.minWidthProperty().bind(gridWidth);
        grid4PlayView.setAlignment(Pos.CENTER);
        grid4PlayView.setStyle("-fx-border-color: red; -fx-border-width: 5;");
        setCenter(grid4PlayView);
    }
    private void setupKeyEvents() {
        System.out.println("check");
        getScene().setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case Z -> board4PlayViewModel.movePlayer(Move.UP);
                case Q -> board4PlayViewModel.movePlayer(Move.LEFT);
                case D -> board4PlayViewModel.movePlayer(Move.RIGHT);
                case S -> board4PlayViewModel.movePlayer(Move.DOWN);
            }
        });

    }

}

