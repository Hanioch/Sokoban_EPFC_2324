package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sokoban.model.Movable.Direction;


import java.util.ArrayList;

public class Grid4Play extends Grid {
    private CommandManager commandManager;
    private Cell4Play[][] matrix;
    private Grid4Design oldGrid;
    private int width;
    private int height;
    private Player4Play player;
    private int boxNumber = 0;
    private IntegerProperty boxesOnTargetsProperty = new SimpleIntegerProperty(0);
    private IntegerProperty totalTargetProperty = new SimpleIntegerProperty(0);

    ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return matrix[line][col].stackProperty();
    }

    public IntegerProperty totalTargetProperty() {
        return totalTargetProperty;
    }
    public IntegerProperty boxesOnTargetsProperty() {
        return boxesOnTargetsProperty;
    }
    public Grid4Play(int width, int height, Grid4Design oldGrid, Player4Play player) {
        super(width, height);
        this.matrix = new Cell4Play[width][height];
        this.oldGrid = oldGrid;

        this.width = width;
        this.height = height;
        this.player = player;

        recreateMatrix();
        recalculateBoxesAndTargets();
    }
    public void recreateMatrix() {
        Cell4Design[][] oldMatrix = oldGrid.getMatrix();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                ObservableList<Element> oldStack = oldGrid.getCell(i, j).getStack();
                for (Element elem : oldStack) {
                    if (elem instanceof Box4Design) {
                        boxNumber++;
                    }
                }
                this.matrix[i][j] = new Cell4Play(oldMatrix[i][j].getStack(), this.player, i, j, boxNumber);
            }
        }
    }
    public void recalculateBoxesAndTargets() {
        int count = 0;
        int target = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ObservableList<Element> stack = matrix[i][j].getValue();
                if (stack.stream().anyMatch(item -> item instanceof Target)){
                    target++;
                    if( stack.stream().anyMatch(item -> item instanceof Box)) {
                        count++;
                    }
                }
            }
        }
        boxesOnTargetsProperty.set(count);
        totalTargetProperty.set(target);
    }
    public Cell4Play getCell(int x, int y) {
        if (x<0 || x>=getWidth() || y<0 || y>=getHeight()) return null;

        if (matrix[x][y] == null) return new Cell4Play();

        return matrix[x][y];
    }

    public ObservableList<Element> getStack(int line, int col) {
        ObservableList<Element> stack  = matrix[line][col].getValue();
        return stack;
    }

    public boolean movePlayer(Direction direction){
        ArrayList<Integer> position = player.move(direction);
        int x = position.get(0);
        int y = position.get(1);

        Cell4Play cell = getCell(x,y);

        if (cell == null) return false;

        boolean cellContainBoxAndCannotMove =false;

        if (cell.containsBox()){
            Box4Play box = (Box4Play) cell.getBox();
            box.setPosition(x,y);
            ArrayList<Integer> positionBox = box.move(direction);
            int currentBoxNumber = box.getNumber();
            boolean nextCellCanMove = moveBox(positionBox.get(0), positionBox.get(1),currentBoxNumber);
            cellContainBoxAndCannotMove = !nextCellCanMove;
        }
        boolean cannotMove= cell.containsWall() || cellContainBoxAndCannotMove ;

        if (cannotMove) {
          return false;
        }

        matrix[x][y].addElement(player);
        player.setX(x);
        player.setY(y);
        recalculateBoxesAndTargets();
        return true;

    }

    public boolean moveBox(int x, int y, int boxNumber){
        Cell4Play cell = getCell(x,y);

        if (cell == null || cell.containsBox() || cell.containsWall() )return false;

        matrix[x][y].addElement(new Box4Play(boxNumber));


        return true;

    }

    public Element getPlayerElement() {
        Cell4Play cellPlayer = getCell(player.getX(),player.getY());
        for (Element elem : cellPlayer.stack  )
            if (elem instanceof Player4Play) return elem;
        return null;
    }

    private class movePlayerCommand implements Command {
        public void execute() {
        }
        public void undo() {
        }
        public void redo(){
        }
    }

    private class moveBoxCommand implements Command {
        public void execute() {
        }
        public void undo() {
        }
        public void redo(){
        }
    }
}
