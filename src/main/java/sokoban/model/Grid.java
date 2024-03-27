package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Grid {
    static int MIN_WIDTH = 10, MIN_HEIGHT = 10, MAX_WIDTH = 50, MAX_HEIGHT = 50;
    static final int GRID_WIDTH = 15;
    static final int GRID_HEIGHT = MIN_HEIGHT;

    private final Cell[][] matrix;

    private final IntegerProperty widthProperty;
    private final IntegerProperty heightProperty;
    public final LongBinding filledCellsCount;


    Grid() {
        matrix = new Cell[GRID_WIDTH][GRID_HEIGHT];
        this.widthProperty = new SimpleIntegerProperty(GRID_WIDTH);
        this.heightProperty = new SimpleIntegerProperty(GRID_HEIGHT);

        for (int i = 0; i < GRID_WIDTH; i++){
            matrix[i] = new Cell[GRID_WIDTH];
            for (int j = 0; j < GRID_HEIGHT; j++){
                matrix[i][j] = new Cell();
            }
        }

        this.filledCellsCount = Bindings.createLongBinding(()-> {
            long count = 0;
            for (int i = 0; i < GRID_WIDTH; i++) {
                for (int j = 0; j < GRID_HEIGHT; j++) {
                    if (matrix[i][j] != null && !matrix[i][j].isEmpty()) {
                        count++;
                    }
                }
            }
            return count;
        });
    }

    ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return matrix[line][col].stackProperty();
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
    public static int getGridWidth(){
        return GRID_WIDTH;
    }
    public static int getGridHeight(){
        return GRID_HEIGHT;
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



    public ObservableList<Element> getStack(int line, int col) {
        ObservableList<Element> stack  = matrix[line][col].getValue();
        return stack ;
    }


    public void play(int line, int col, Element elem){
        if (elem instanceof Player) {
            placePlayer(line, col);
        }else{
            matrix[line][col].addElement(elem);
        }
        filledCellsCount.invalidate();
    }

    public void placePlayer(int newX, int newY) {
        int oldX = Player.getX();
        int oldY = Player.getY();
        if (oldX >= 0 || oldY >= 0) {
            matrix[oldX][oldY].removePlayer();
        }
        matrix[newX][newY].addElement(new Player(newX, newY));
    }
    public LongBinding filledCellsCountProperty() {
        return filledCellsCount;
    }

    public Cell getCell(int x, int y) {
        if (matrix[x][y] == null) {
            return new Cell();
        }
        return matrix[x][y];
    }

    public boolean isEmpty(int x, int y) {
        return matrix[x][y].isEmpty();
    }

    public boolean playerIsSet() {
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                if (containsPlayer(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean containsPlayer(int line, int col){
        ReadOnlyListProperty<Element> stack = valueProperty(line, col);
        for (Element e : stack) {
            if (e instanceof Player){
                return true;
            }
        }
        return false;
    }

    public boolean playerIsAlone() {
        ReadOnlyListProperty<Element> stack = valueProperty(Player.getX(), Player.getY());
        return stack.size() == 2;
    }
}
