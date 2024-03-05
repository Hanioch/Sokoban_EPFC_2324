package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;

import java.util.Arrays;

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
    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    public ReadOnlyObjectProperty<CellValue> valueProperty(int line, int col) {
        return matrix[line][col].valueProperty();
    }

    CellValue getValue(int line, int col) {
        return matrix[line][col].getValue();
    }

    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }

}
