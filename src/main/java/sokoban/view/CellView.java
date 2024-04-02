package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import sokoban.model.*;
import sokoban.viewmodel.Cell4DesignViewModel;
import sokoban.viewmodel.CellViewModel;

public abstract class CellView extends StackPane {
    protected static final Image boxImage = new Image("box.png");
    protected static final Image targetImage = new Image("goal.png");
    protected static final Image groundImage = new Image("ground.png");
    protected static final Image playerImage = new Image("player.png");
    protected static final Image wallImage = new Image("wall.png");

    protected DoubleBinding widthProperty;
    protected  DoubleBinding heightProperty;

    protected ImageView imageView = new ImageView();

    public CellView(DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {
        this.widthProperty = cellWidthProperty;
        this.heightProperty = cellHeightProperty;

        setAlignment(Pos.CENTER);
        layoutControls();
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);
        getChildren().add(imageView);
    }




    public void setImage(ObservableList<Element> stack) {
        this.getChildren().clear();
        for (Element element : stack) {
            ImageView imageView = new ImageView(getImageForElement(element));
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(this.widthProperty);
            imageView.fitHeightProperty().bind(this.heightProperty);
            this.getChildren().add(imageView);
        }
    }

    private Image getImageForElement(Element element) {
        if (element instanceof Player4Design) {
            return new Image("player.png");
        } else if (element instanceof Box4Design) {
            return new Image("box.png");
        } else if (element instanceof Target4Design) {
            return new Image("goal.png");
        } else if (element instanceof Wall4Design) {
            return new Image("wall.png");
        } else if (element instanceof Ground4Design) {
            return new Image("ground.png");
        }
        return null;
    }
}
