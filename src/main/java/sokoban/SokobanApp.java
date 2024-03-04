package sokoban;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.model.Grid;
import sokoban.view.BoardView;
import sokoban.view.GridView;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;

public class SokobanApp extends Application  {

    @Override
    public void start(Stage primaryStage) {
        Grid grid = new Grid();
        BoardViewModel boardViewModel = new BoardViewModel(grid);
        BoardView boardView = new BoardView(boardViewModel);

        Scene scene = new Scene(boardView, 700, 600);
        primaryStage.setTitle("Sokoban");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
