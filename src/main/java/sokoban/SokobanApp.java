package sokoban;

import javafx.application.Application;
import javafx.stage.Stage;
import sokoban.model.Board4Design;
import sokoban.view.Board4DesignView;
import sokoban.viewmodel.Board4DesignViewModel;

public class SokobanApp extends Application  {

    @Override
    public void start(Stage primaryStage) {
        Board4Design board4Design = new Board4Design();
        Board4DesignViewModel vm = new Board4DesignViewModel(board4Design);
        new Board4DesignView(primaryStage, vm);
    }

    public static void main(String[] args) {
        launch();
    }

}
