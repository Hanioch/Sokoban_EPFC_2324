package sokoban.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import sokoban.viewmodel.BoardViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;


public class BoardView extends BorderPane {
    private GridView gridView;
    private VBox toolbox;
    private HBox contentArea;

    private String selectedElement = "wall.png";
    private StackPane selectedTool;

    public BoardView(BoardViewModel boardViewModel) {
        gridView = new GridView(boardViewModel.getGridViewModel());
        toolbox = new VBox(15);
        contentArea = new HBox(5);
        initializeToolbox();

        contentArea.getChildren().addAll(toolbox, gridView);
        toolbox.setAlignment(Pos.CENTER);
        toolbox.setPadding(new Insets(0, 0, 0, 10));
        gridView.setAlignment(Pos.CENTER);
        HBox.setHgrow(gridView, Priority.ALWAYS);
       // contentArea.setAlignment(Pos.CENTER_LEFT);

        this.setCenter(contentArea);
    }
    private void initializeToolbox() {
        String[] elements = {"ground.png", "goal.png", "wall.png", "player.png", "box.png"};
        for (String elementPath : elements) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + elementPath)), 50, 50, true, true);
            ImageView imageView = new ImageView(image);
            StackPane imageContainer = new StackPane(imageView);
            imageContainer.setUserData(elementPath);

            if ("wall.png".equals(elementPath)) {
                imageContainer.getStyleClass().add("selected-tool");
                selectedTool = imageContainer;
            }

            imageContainer.setOnMouseClicked(e -> {
                if (selectedTool != null) {
                    selectedTool.getStyleClass().remove("selected-tool");
                    selectedTool.setPadding(Insets.EMPTY);
                }
                imageContainer.getStyleClass().add("selected-tool");
                selectedTool = imageContainer;
            });

            toolbox.getChildren().add(imageContainer);
        }
    }

}
