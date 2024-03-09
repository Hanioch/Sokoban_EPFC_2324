package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.Arrays;


public class Grid {
    public static final int GRID_WIDTH = 15;
    public static final int GRID_HEIGHT = 10;
    private final Cell[][] matrix ;
    private final int width;
    private final int height;
    private final LongBinding filledCellsCount;
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new Cell[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                matrix[i][j] = new Cell();
            }
        }
        filledCellsCount = Bindings.createLongBinding(() -> Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isEmpty())
                .count());
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
        if (line >= 0 && line < this.width && col >= 0 && col < this.height) {
            matrix[line][col].setValue(value);
        }
    }
    public LongBinding filledCellsCountProperty() {
        return filledCellsCount;
    }
}

