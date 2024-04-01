package sokoban.view;

import javafx.application.Platform;
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

        Scene scene = new Scene(this, 600, 600);

        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }
}
