package sokoban.view;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import sokoban.model.CellValue;
import sokoban.viewmodel.BoardViewModel;
import javafx.geometry.Pos;


import java.util.Objects;


public class BoardView extends BorderPane {
    private final VBox toolbox;

    private StackPane selectedTool;

    public BoardView(BoardViewModel boardViewModel) {
        GridView gridView = new GridView(boardViewModel.getGridViewModel());
        toolbox = new VBox(15);
        HBox contentArea = new HBox(5);
        initializeToolbox();

        contentArea.getChildren().addAll(toolbox, gridView);
        toolbox.setAlignment(Pos.CENTER);
        toolbox.setPadding(new Insets(0, 0, 0, 10));
        gridView.setAlignment(Pos.CENTER);
        HBox.setHgrow(gridView, Priority.ALWAYS);
        this.setCenter(contentArea);
    }
    private void initializeToolbox() {
        String[] elements = {"ground.png", "goal.png", "wall.png", "player.png", "box.png"};
        for (String elementPath : elements) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + elementPath)), 50, 50, true, true);
            ImageView imageView = new ImageView(image);
            StackPane imageContainer = new StackPane(imageView);
            imageContainer.setUserData(elementPath);

            imageContainer.setOnMouseClicked(e -> {
                if (selectedTool != null) {
                    selectedTool.getStyleClass().remove("selected-tool");
                    selectedTool.setPadding(Insets.EMPTY);
                }
                imageContainer.getStyleClass().add("selected-tool");
                selectedTool = imageContainer;

                CellValue tool = convertPathToCellValue(elementPath);
                CellView.setSelectedTool(tool);
            });
            imageContainer.setOnDragDetected(event -> {
                Dragboard db = imageContainer.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(convertPathToCellValue(elementPath).name());
                db.setContent(content);
                event.consume();
            });


            toolbox.getChildren().add(imageContainer);
        }
    }
    private CellValue convertPathToCellValue(String imagePath) {
        return switch (imagePath) {
            case "wall.png" -> CellValue.WALL;
            case "box.png" -> CellValue.BOX;
            case "player.png" -> CellValue.PLAYER;
            case "goal.png" -> CellValue.TARGET;
            default -> CellValue.GROUND;
        };
    }

}
