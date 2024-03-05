package sokoban.model;

import javafx.beans.property.ReadOnlyObjectProperty;


public class Grid {
    public static final int GRID_WIDTH = 15;
    public static final int GRID_HEIGHT = 10;

    private final Cell[][] matrix;


    public Grid() {
        matrix = new Cell[GRID_WIDTH][GRID_HEIGHT];
        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) {
                matrix[i][j] = new Cell();
            }
        }}
    public static int getWidth() {
        return GRID_WIDTH;
    }
    public static int getHeight() {
        return GRID_HEIGHT;
    }
    public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return matrix[line][col].valueProperty();
    }

    public CellValue getValue(int line, int col) {
        return matrix[line][col].getValue();
    }

    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }
    public void setValue(int line, int col, CellValue value) {
        if (line >= 0 && line < GRID_WIDTH && col >= 0 && col < GRID_HEIGHT) {
            matrix[line][col].setValue(value);
        }
    }
}
