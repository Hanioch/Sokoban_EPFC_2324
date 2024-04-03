package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Grid4Play extends Grid {
    private Cell4Play[][] matrix;
    private Grid4Design oldGrid;

    public Grid4Play(int width, int height, Grid4Design oldGrid) {
        super(width, height);
        this.matrix = new Cell4Play[width][height];
        this.oldGrid = oldGrid;

        /*matrix = new Cell4Play[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                matrix[i][j] = new Cell4Play();
            }
        }*/
        recreateMatrix();

    }

    private void recreateMatrix() {
        Cell4Design[][] oldMatrix = oldGrid.getMatrix();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                this.matrix[i][j] = new Cell4Play(oldMatrix[i][j].getStack());
            }
        }
        System.out.println(Player4Play.getX());
    }

    public Cell4Play getCell(int x, int y) {
        if (matrix[x][y] == null) {
            return new Cell4Play();
        }
        return matrix[x][y];
    }

    public ObservableList<Element> getStack(int line, int col) {
        ObservableList<Element> stack  = matrix[line][col].getValue();
        return stack ;
    }
}
