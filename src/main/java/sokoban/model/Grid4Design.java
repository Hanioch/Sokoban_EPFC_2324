package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class Grid4Design extends Grid {
    private  Cell4Design[][] matrix;

    public LongBinding filledCellsCount;
    private final BooleanBinding characterMissed;
    private final BooleanBinding targetMissed ;
    private final BooleanBinding boxMissed;
    private final BooleanBinding sameNumberOfBoxAndTarget;
    private final BooleanBinding isAnError;
    ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return matrix[line][col].stackProperty();
    }

    public Grid4Design(int width, int height) {
        super(width, height);

        matrix = new Cell4Design[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                matrix[i][j] = new Cell4Design();
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

        characterMissed = checkIfNotContain(Player4Design.class);
        boxMissed = checkIfNotContain(Box4Design.class);
        targetMissed =  checkIfNotContain(Target4Design.class);
        sameNumberOfBoxAndTarget = isNotSameNumberBoxAndTarget();
        isAnError = checkErrorsProperty();
    }
    public void addElementsToCell(int x, int y, List<Element> elements) {
        Cell4Design cell4Design = getCell(x, y);
        for (Element element : elements) {
            cell4Design.addElement(element);
        }
    }

    public ObservableList<Element> getStack(int line, int col) {
        ObservableList<Element> stack  = matrix[line][col].getValue();
        return stack ;
    }


    public void play(int line, int col, Element elem){
        if (elem instanceof Player4Design) {
            placePlayer(line, col);
        }else{
            matrix[line][col].addElement(elem);
        }
        filledCellsCount.invalidate();
    }

    public void placePlayer(int newX, int newY) {
        int oldX = Player4Design.getX();
        int oldY = Player4Design.getY();
        if (oldX >= 0 || oldY >= 0) {
            matrix[oldX][oldY].removePlayer();
        }
        matrix[newX][newY].addElement(new Player4Design(newX, newY));
    }
    public LongBinding filledCellsCountProperty() {
        return filledCellsCount;
    }

    public Cell4Design getCell(int x, int y) {
        if (matrix[x][y] == null) {
            return new Cell4Design();
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
            if (e instanceof Player4Design){
                return true;
            }
        }
        return false;
    }

    public boolean playerIsAlone() {
        ReadOnlyListProperty<Element> stack = valueProperty(Player4Design.getX(), Player4Design.getY());
        return stack.size() == 2;
    }

    private BooleanBinding checkIfNotContain(Class<?> type){
        return Bindings.createBooleanBinding(()->{
            for (int i = 0; i < getWidth(); i++) {
                for (int j = 0; j < getHeight(); j++) {
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

         for (int i = 0; i < getWidth(); i++) {
             for (int j = 0; j < getHeight(); j++) {
                 List stackCell = matrix[i][j].getStack();
                 for (Object elem : stackCell) {
                     if (elem instanceof Box4Design)
                         countBox ++;

                     if (elem instanceof Target4Design)
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
