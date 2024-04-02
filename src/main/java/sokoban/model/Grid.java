package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Grid {
    protected  Cell4Design[][] matrix;
    public int width;
    public int height;
    public IntegerProperty widthProperty ;
    public IntegerProperty heightProperty ;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new Cell4Design[width][height];
        widthProperty = new SimpleIntegerProperty(width);
        heightProperty = new SimpleIntegerProperty(height);

        for (int i = 0; i < width; i++){
            // matrix[i] = new Cell[width];
            for (int j = 0; j < height; j++){
                matrix[i][j] = new Cell4Design();
            }
        }
    }
    public Grid(){

    }
}
