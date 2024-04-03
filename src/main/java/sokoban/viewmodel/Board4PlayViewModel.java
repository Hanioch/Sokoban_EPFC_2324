package sokoban.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import sokoban.model.Board4Design;
import sokoban.model.Board4Play;

public class Board4PlayViewModel extends BoardViewModel {
    private Grid4PlayViewModel gridViewModel;
    private Board4Play board;
    public Board4PlayViewModel(Board4Play board4Play) {
        this.board = board4Play;
        gridViewModel = new Grid4PlayViewModel(board4Play, this);
    }
    public int gridWidth(){
        return board.getGrid().getWidth();
    }
    public int gridHeight(){
        return board.getGrid().getHeight();
    }
    public Grid4PlayViewModel getGridViewModel(){
        return gridViewModel;
    }
    public BooleanProperty isModifiedProperty() {
        return board.isModifiedProperty();
    }


    public void goDown() {
        board.goDown();
    }

    public void goUp() {
        board.goUp();
    }

    public void goRight() {
        board.goRight();
    }

    public void goLeft() {
        board.goLeft();
    }

    /*public IntegerProperty playerXProperty() {
        return player.xProperty();
    }

    public int getPlayerX() {
        return player.getX();
    }

    public void setPlayerX(int x) {
        player.setX(x);
    }

    public IntegerProperty playerYProperty() {
        return player.yProperty();
    }

    public int getPlayerY() {
        return player.getY();
    }

    public void setPlayerY(int y) {
        player.setY(y);
    }*/
}
