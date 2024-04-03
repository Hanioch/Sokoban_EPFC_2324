package sokoban.view;

import javafx.scene.layout.BorderPane;
import sokoban.model.*;

public abstract class BoardView extends BorderPane {
    protected Element selectedElementFromImagePath(String imagePath) {
        return switch (imagePath) {
            case "ground.png" -> new Ground4Design();
            case "goal.png" -> new Target4Design();
            case "wall.png" -> new Wall4Design();
            case "player.png" -> new Player4Design();
            case "box.png" -> new Box4Design();
            default -> null;
        };
    }

}
