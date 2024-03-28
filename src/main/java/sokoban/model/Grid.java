package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class Grid {

    private  Cell[][] matrix;

    private IntegerProperty widthProperty ;
    private IntegerProperty heightProperty ;
    public  LongBinding filledCellsCount;


    public Grid(int width, int height) {
        matrix = new Cell[width][height];
        widthProperty = new SimpleIntegerProperty(width);
        heightProperty = new SimpleIntegerProperty(height);

        for (int i = 0; i < width; i++){
           // matrix[i] = new Cell[width];
            for (int j = 0; j < height; j++){
                matrix[i][j] = new Cell();
            }
        }

        this.filledCellsCount = Bindings.createLongBinding(()-> {
            long count = 0;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j <  height; j++) {
                    if (matrix[i][j] != null && !matrix[i][j].isEmpty()) {
                        count++;
                    }
                }
            }
            return count;
        });
    }
    public void addElementsToCell(int x, int y, List<Element> elements) {
        Cell cell = getCell(x, y);
        for (Element element : elements) {
            cell.addElement(element);
        }
    }

    ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return matrix[line][col].stackProperty();
    }

    public  int getWidth() {
        return widthProperty.get();
    }
    public  int getGridWidth(){
        return widthProperty.get();
    }
    public  int getGridHeight(){
        return heightProperty.get();
    }

    public IntegerProperty widthProperty() {
        return widthProperty;
    }

    public void setWidth(int width) {
        widthProperty.set(width);
    }

    public  int getHeight() {
        return heightProperty.get();
    }

    public IntegerProperty heightProperty() {
        return heightProperty;
    }

    public void setHeight(int height) {
        heightProperty.set(height);
    }

    public int getArea() {
        return widthProperty.get() * heightProperty.get();
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
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
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
