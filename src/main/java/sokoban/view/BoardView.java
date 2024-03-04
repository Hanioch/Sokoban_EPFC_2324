package sokoban.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sokoban.viewmodel.BoardViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Objects;


public class BoardView extends BorderPane {
    private GridView gridView;
    private VBox toolbox;

    private HBox contentArea;

    public BoardView(BoardViewModel boardViewModel) {
        gridView = new GridView(boardViewModel.getGridViewModel());
        toolbox = new VBox(15);
        contentArea = new HBox(5);
        initializeToolbox();

        contentArea.getChildren().addAll(toolbox, gridView);
        toolbox.setAlignment(Pos.CENTER);
        gridView.setAlignment(Pos.CENTER);
        HBox.setHgrow(gridView, Priority.ALWAYS);
       // contentArea.setAlignment(Pos.CENTER_LEFT);

        this.setCenter(contentArea);
    }

    private void initializeToolbox() {
        String[] elements = { "ground.png", "wall.png", "box.png", "player.png", "goal.png"};
        for (String elementPath : elements) {
            Image image = new Image(elementPath);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            toolbox.getChildren().add(imageView);
        }
    }
}
