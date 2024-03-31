package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class Grid {
    static int MIN_WIDTH = 10, MIN_HEIGHT = 10, MAX_WIDTH = 50, MAX_HEIGHT = 50;
    static final int GRID_WIDTH = 15;
    static final int GRID_HEIGHT = MIN_HEIGHT;

    private final Cell[][] matrix;

    private final IntegerProperty widthProperty;
    private final IntegerProperty heightProperty;
    public final LongBinding filledCellsCount;
    private int playerX = -1;
    private int playerY = -1;
    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    private final BooleanBinding characterMissed;
    private final BooleanBinding targetMissed ;
    private final BooleanBinding boxMissed;
    private final BooleanBinding sameNumberOfBoxAndTarget;
    private final BooleanBinding isAnError;



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

        characterMissed = checkIfNotContain(Player.class);
        boxMissed = checkIfNotContain(Box.class);
        targetMissed =  checkIfNotContain(Target.class);
        sameNumberOfBoxAndTarget = isNotSameNumberBoxAndTarget();
        isAnError = checkErrorsProperty();
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
        if (playerX >= 0 && playerY >= 0) {
            matrix[playerX][playerY].removePlayer();
        }
        matrix[newX][newY].addElement(new Player());
        playerX = newX;
        playerY = newY;
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

    private BooleanBinding checkIfNotContain(Class<?> type){
        return Bindings.createBooleanBinding(()->{
            for (int i = 0; i < GRID_WIDTH; i++) {
                for (int j = 0; j < GRID_HEIGHT; j++) {
                    List stackCell = matrix[i][j].getStack();
                    for (Object elem : stackCell) {
                        if (type.isInstance(elem)) {
                            return false;
                        }
                        }
                }
            }
            return true;
        });

    }

    private BooleanBinding isNotSameNumberBoxAndTarget(){

        return Bindings.createBooleanBinding(()->{

         int countTarget = 0;
         int countBox = 0;

         for (int i = 0; i < GRID_WIDTH; i++) {
             for (int j = 0; j < GRID_HEIGHT; j++) {
                 List stackCell = matrix[i][j].getStack();
                 for (Object elem : stackCell) {
                     if (elem instanceof Box)
                         countBox ++;

                     if (elem instanceof Target)
                         countTarget++;

                 }
             }
         }
         return  countTarget != countBox;
        });

    }

    private BooleanBinding checkErrorsProperty(){
        return Bindings.createBooleanBinding(()-> targetMissed.get() || characterMissed.get() || boxMissed.get()|| sameNumberOfBoxAndTarget.get());
    }


    public BooleanBinding isCharacterMissedProperty(){
        return characterMissed ;
    }
    public BooleanBinding isTargetMissedProperty(){
        return targetMissed;
    }
    public BooleanBinding isBoxMissedProperty(){
        return boxMissed;
    }
    public BooleanBinding isSameNumberOfBoxAndTargetProperty(){
        return sameNumberOfBoxAndTarget;
    }
    public BooleanBinding IsAnErrorProperty(){
        return isAnError;
    }

    public void invalidateBinding(){
        characterMissed.invalidate();
        boxMissed.invalidate();
        targetMissed.invalidate();
        sameNumberOfBoxAndTarget.invalidate();
        isAnError.invalidate();
    }

}
