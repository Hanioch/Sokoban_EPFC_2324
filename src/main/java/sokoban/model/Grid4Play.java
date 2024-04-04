package sokoban.model;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ObservableList;
import sokoban.Move;

import java.util.ArrayList;

public class Grid4Play extends Grid {
    private Cell4Play[][] matrix;

    private  Cell4Play player;
    private Grid4Design oldGrid;
    private int boxNumber = 0;

    ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return matrix[line][col].stackProperty();
    }
    public Grid4Play(int width, int height, Grid4Design oldGrid) {
        super(width, height);
        this.matrix = new Cell4Play[width][height];
        this.oldGrid = oldGrid;

        recreateMatrix();

    }

    private void recreateMatrix() {
        Cell4Design[][] oldMatrix = oldGrid.getMatrix();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                ObservableList<Element> oldStack = oldGrid.getCell(i, j).getStack();
                for (Element elem : oldStack) {
                    if (elem instanceof Box4Design) {
                       boxNumber++;
                    }
                }
                this.matrix[i][j] = new Cell4Play(oldMatrix[i][j].getStack(),boxNumber);
                for (Element elem : oldStack) {
                    if (elem instanceof Player4Design)
                        player=matrix[i][j];
                }
            }
        }
    }
    public Cell4Play getCell(int x, int y) {
        if (x<0 || x>=getWidth() || y<0 || y>=getHeight()) return null;

        if (matrix[x][y] == null) return new Cell4Play();

        return matrix[x][y];
    }

    public ObservableList<Element> getStack(int line, int col) {
        ObservableList<Element> stack  = matrix[line][col].getValue();
        return stack ;
    }

    public boolean movePlayer(Move direction){
        int oldX = Player4Play.getX();
        int oldY = Player4Play.getY();
        Player4Play j1 = new Player4Play(oldX, oldY);

        ArrayList<Integer> position = j1.move(direction);
        int x = position.get(0);
        int y = position.get(1);

        Cell4Play cell = getCell(x,y);
        if (cell == null) return false;

        boolean cellContainBoxAndCannotMove =false;

        if (cell.containsBox()){
            Box4Play box = (Box4Play) cell.getBox();
            box.setPosition(x,y);
            ArrayList<Integer> positionBox = box.move(direction);
            int currentBoxNumber = box.getNumber();
            boolean nextCellCanMove = moveBox(positionBox.get(0), positionBox.get(1),currentBoxNumber);

            cellContainBoxAndCannotMove = !nextCellCanMove;
        }
        boolean cannotMove= cell.containsWall() || cellContainBoxAndCannotMove ;

        if (cannotMove) {
          return false;
        }

        this.player.removePlayer();
        matrix[x][y].addElement(new Player4Play(x,y));
        this.player= matrix[x][y];
        return true;

    }

    public boolean moveBox(int x, int y, int boxNumber){
        Cell4Play cell = getCell(x,y);

        if (cell == null || cell.containsBox() || cell.containsWall() )return false;

        matrix[x][y].addElement(new Box4Play(boxNumber));
        return true;

    }

    public Element getPlayerElement() {
        for (Element elem : player.stack  )
            if (elem instanceof Player4Play) return elem;

        return null;
    }


}
