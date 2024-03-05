package sokoban;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.view.BoardView;
import sokoban.viewmodel.BoardViewModel;

import java.util.Objects;

public class SokobanApp extends Application  {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board();
        BoardViewModel boardViewModel = new BoardViewModel(board);
        BoardView boardView = new BoardView(boardViewModel);

        Scene scene = new Scene(boardView, 700, 600);
        String css = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
