package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Arrays;

public class Grid {
    static int MIN_WIDTH = 10, MIN_HEIGHT = 10, MAX_WIDTH = 50, MAX_HEIGHT = 50;
    static final int GRID_WIDTH = 15;
    static final int GRID_HEIGHT = MIN_HEIGHT;

    private final Cell[][] matrix;

    private final IntegerProperty widthProperty;
    private final IntegerProperty heightProperty;
    private final LongBinding filledCellsCount;

    Grid() {
        matrix = new Cell[GRID_WIDTH][GRID_HEIGHT];
        this.widthProperty = new SimpleIntegerProperty(GRID_WIDTH);
        this.heightProperty = new SimpleIntegerProperty(GRID_HEIGHT);

        for (int i = 0; i < GRID_WIDTH; i++){
            matrix[i] = new Cell[GRID_WIDTH];
            for (int j = 0; j < GRID_HEIGHT; j++){
                matrix[i][j] = new Cell(i, j);
            }
        }

        this.filledCellsCount = Bindings.createLongBinding(()-> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isEmpty())
                .count());
    }
    public static int getMinHeight() {
        return MIN_HEIGHT;
    }

    public static int getMinWidth() {
        return MIN_WIDTH;
    }

    public static int getMaxWidth() {
        return MAX_WIDTH;
    }

    public static int getMaxHeight() {
        return MAX_HEIGHT;
    }

    public int getWidth() {
        return widthProperty.get();
    }

    public IntegerProperty widthProperty() {
        return widthProperty;
    }

    public void setWidth(int width) {
        widthProperty.set(width);
    }

    public int getHeight() {
        return heightProperty.get();
    }

    public IntegerProperty heightProperty() {
        return heightProperty;
    }

    public void setHeight(int height) {
        heightProperty.set(height);
    }

    public int getArea() {
        return GRID_HEIGHT * GRID_WIDTH;
    }


    /*
    * Je sais aps ce qu'il faut faire
    *
    ObservableList<CellValue> valueProperty(int line, int col){
        return matrix[line][col].;
    }
    *
    * FAIRE UN
    *     CellValue getValue(int line, int col) {
        return matrix[line][col].getValue();
    }
     */

    /*
    void play(int line, int col, CellValue playerValue){
        *//* Faire
        matrix[line][col].setValue(playerValue);
        filledCellsCount.invalidate();*//*
    }
    */

    public LongBinding filledCellsCountProperty() {
        return filledCellsCount;
    }

    public Cell getCell(int x, int y) {
        return matrix[x][y];
    }

    public boolean isEmpty(int x, int y) {
        return matrix[x][y].isEmpty();
    }
}
