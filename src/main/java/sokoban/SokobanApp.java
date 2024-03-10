package sokoban;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.view.BoardView;
import sokoban.view.SaveAsDialog;
import sokoban.viewmodel.BoardViewModel;

import java.util.Objects;
import java.util.Optional;

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
        boardViewModel.isModifiedProperty().addListener((obs, wasModified, isNowModified) -> {
            String title = "Sokoban";
            if (isNowModified) {
                title += " (*)";
            }
            primaryStage.setTitle(title);
        });
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            if (boardViewModel.isModifiedProperty().get()) {
                Optional<ButtonType> result = boardView.showAlertWithConfirmation("Sauvegarder les changements", "Voulez-vous sauvegarder les modifications avant de quitter ?");
                if (result.isPresent() && result.get() == ButtonType.YES) {
                    boolean saveSuccessful = SaveAsDialog.showSaveDialog(boardViewModel);
                    if (!saveSuccessful) {
                        event.consume();
                    }
                } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {

                    event.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
