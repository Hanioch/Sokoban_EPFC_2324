package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import sokoban.model.Movable.Direction;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Grid4Play extends Grid {
    private Cell4Play[][] matrix;
    private Grid4Design oldGrid;
    private int width;
    private int height;
    private Player4Play player;
    private Mushroom mushroom;
    private int boxNumber = 0;
    private IntegerProperty boxesOnTargetsProperty = new SimpleIntegerProperty(0);
    private IntegerProperty totalTargetProperty = new SimpleIntegerProperty(0);
    Random random = new Random();


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
        this.mushroom = new Mushroom(0,0);

        recreateMatrix();
        recalculateBoxesAndTargets();
        generateMushroom();
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

        if (cell.containsMushroom())
            activeMushroom();

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

        matrix[x][y].addElement(new Player4Play(x,y));
        player.setX(x);
        player.setY(y);
        recalculateBoxesAndTargets();
        return true;

    }

    public boolean moveBox(int x, int y, int boxNumber){
        Cell4Play cell = getCell(x,y);
        if (cell == null || cell.containsBox() || cell.containsWall() || cell.containsMushroom() )return false;
        matrix[x][y].addElement(new Box4Play(boxNumber));

        return true;
    }


    public Element getPlayerElement() {
        Cell4Play cellPlayer = getCell(player.getX(),player.getY());
        for (Element elem : cellPlayer.stack  )
            if (elem instanceof Player4Play) return elem;
        return null;
    }



    public void activeMushroom(){
        ArrayList<ArrayList<Integer>> boxPosition = new ArrayList<>();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Cell4Play cell = getCell(i,j);
                for (Element elem : cell.getStack()) {
                    if (elem instanceof Box4Play) {
                        ArrayList<Integer> position = new ArrayList<>();
                        position.add(i);
                        position.add(j);
                        boxPosition.add(position);
                    }
                }
            }
        }

        for (ArrayList<Integer> position : boxPosition){
            int x = position.get(0);
            int y = position.get(1);
            Cell4Play cell = getCell(x,y);
            Element box = cell.getBox();
            cell.removeElement(box);
            addBoxFreeCase((Box4Play) box);
        }
        generateMushroom();

    }

    public void addBoxFreeCase(Box4Play elem){
        ArrayList<Integer> position = searchPositionfree(true);
        int x = position.get(0);
        int y = position.get(1);
        Cell4Play cell = getCell(x, y);

        int boxNum = elem.getNumber();
        cell.getStack().add(new Box4Play(boxNum));
    }



    public void generateMushroom() {
        ArrayList<Integer> position = searchPositionfree(false);
        Cell4Play oldCell = getCell(mushroom.getX(), mushroom.getY());
        if (oldCell.containsMushroom()){
            Element elem = oldCell.getMushroom();
            oldCell.removeElement(elem);
        }
        int x = position.get(0);
        int y = position.get(1);
        Cell4Play cell = getCell(x, y);

        mushroom.setX(x);
        mushroom.setY(y);
        cell.getStack().add(mushroom);
    }

    public ArrayList<Integer> searchPositionfree(boolean noBorder){
        Set<String> usedPositions = new HashSet<>();
        //ArrayList<ArrayList> listPosition = new ArrayList<>();


        boolean isFounded = false;
        while (!isFounded) {
            int x = noBorder? random.nextInt(width - 2) + 1 : random.nextInt(width);
            int y = noBorder? random.nextInt(height - 2) + 1: random.nextInt(height);

            String positionKey = x + "," + y;

            if (!usedPositions.contains(positionKey)) {
                Cell4Play cell = getCell(x, y);
                boolean haveOnly1Elem = cell.getStack().size() == 1;
                boolean isGround = cell.getStack().get(0) instanceof Ground4Play;
                if (haveOnly1Elem && isGround) {
                    isFounded = true;
                    ArrayList<Integer> freePosition = new ArrayList<>();
                    freePosition.add(x);
                    freePosition.add(y);
                    return freePosition;
                }
                usedPositions.add(positionKey);
            }
        }
        return null;
    }
}
