package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;

import java.util.Arrays;

public class Grid {
    int gridWidth = 10;
    int gridHeight = 15;

    static int MIN_HEIGHT = 15;
    static int MIN_WIDTH = 15;
    static int MAX_WIDTH = 15;
    static int MAX_HEIGHT = 15;


    //liste avec chaque celulle et son contenu (pas sur)
    private final Cell[][] matrix;
    //Le nombre de celulle utilisé.
    private final LongBinding filledCellsCount;

    Grid(){
        matrix = new Cell[gridWidth][gridHeight];
        for (int i = 0; i < gridWidth; i++){
            matrix[i] = new Cell[gridWidth];
            for (int j = 0; j < gridHeight; j++){
                matrix[i][j] = new Cell(i, j);
            }
        }

        filledCellsCount = Bindings.createLongBinding(()-> Arrays
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

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public int getArea() {
        return gridHeight * gridWidth;
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

    void play(int line, int col, CellValue playerValue){
        /* Faire
        matrix[line][col].setValue(playerValue);
        filledCellsCount.invalidate();*/
    }

    public LongBinding filledCellsCountProperty() {
        return filledCellsCount;
    }


    public boolean isEmpty(int line, int col) {
        return matrix[line][col].isEmpty();
    }
}
