package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;

public class Grid4Play extends Grid {
    private Cell4Play[][] matrix;

    public Grid4Play(int width, int height) {
        super(width, height);
        matrix = new Cell4Play[width][height];
        widthProperty = new SimpleIntegerProperty(width);
        heightProperty = new SimpleIntegerProperty(height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new Cell4Play();
            }
        }

    }
    public Cell4Play getCell(int x, int y) {
        if (matrix[x][y] == null) {
            return new Cell4Play();
        }
        return matrix[x][y];
    }

}
