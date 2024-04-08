package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import sokoban.model.Movable.Direction;


import java.util.*;

public class Grid4Play extends Grid {
    private CommandManager commandManager;
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
    private BooleanBinding isStone;
    private int countBeforeToBeNormal = 0;


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
        this.isStone= Bindings.createBooleanBinding(()->false);
        this.commandManager = new CommandManager();

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


    public Player4Play getPlayer() {
        return player;
        /**Cell4Play cellPlayer = getCell(player.getX(),player.getY());
        for (Element elem : cellPlayer.stack  )
            if (elem instanceof Player4Play) return elem;
        return null;*/
    }



    public void activeMushroom(){
        ArrayList<int[]> boxPosition = new ArrayList<>();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Cell4Play cell = getCell(i,j);
                for (Element elem : cell.getStack()) {
                    if (elem instanceof Box4Play) {
                        int[] position = new int[2];
                        position[0] = i;
                        position[1] = j;
                        boxPosition.add(position);
                    }
                }
            }
        }

        for (int[] position : boxPosition){
            int x = position[0];
            int y = position[1];
            Cell4Play cell = getCell(x,y);
            Box4Play box = cell.getBox();
            cell.removeElement(box);
            addBoxFreeCase((Box4Play) box);
        }
        generateMushroom();

    }

    public void addBoxFreeCase(Box4Play elem){
        int[] position = searchPositionfree(true);
        int x = position[0];
        int y = position[1];
        Cell4Play cell = getCell(x, y);

        elem.setPosition(x,y);
        cell.getStack().add(elem);
    }



    public void generateMushroom() {
        Cell4Play oldCell = getCell(mushroom.getX(), mushroom.getY());
        if (oldCell.containsMushroom()){
            Mushroom elem = oldCell.getMushroom();
            oldCell.removeElement(elem);
        }

        int[] position = searchPositionfree(true);
        int x = position[0];
        int y = position[1];

        Cell4Play cell = getCell(x, y);

        mushroom.setX(x);
        mushroom.setY(y);
        cell.getStack().add(mushroom);
    }

    public int[] searchPositionfree(boolean noBorder){
        Set<String> usedPositions = new HashSet<>();
        usedPositions.clear();

        boolean isFounded = false;
        while (!isFounded) {
            int x = noBorder? random.nextInt(width - 2) + 1 : random.nextInt(width);
            int y = noBorder? random.nextInt(height - 2) + 1: random.nextInt(height);

            String positionKey = x + "," + y;
            if (!usedPositions.contains(positionKey)) {
                Cell4Play cell = getCell(x, y);
                boolean haveOnly1Elem = cell.getStack().size() == 1;
                boolean isGround = cell.getStack().get(0) instanceof Ground4Play;
                /**
                 * haveOnly1Elem && isGround
                 */
                if (cell.isEmpty()) {
                    isFounded = true;
                    int[] freePosition = new int[2];

                    freePosition[0] = x;
                    freePosition[1] = y;
                    return freePosition;
                }
                if (usedPositions.size()>50)
                    return null;
                usedPositions.add(positionKey);

            }
        }
        return null;
    }

    public BooleanBinding getIsStone() {
        return isStone;
    }

    public void setIsStone(){
        isStone=isStone.not();
        countBeforeToBeNormal=-1;
    }

    public int getCountBeforeToBeNormal() {
        return countBeforeToBeNormal;
    }

    public void setCountBeforeToBeNormal(int countBeforeToBeNormal) {
        this.countBeforeToBeNormal += countBeforeToBeNormal;
    }

    public boolean moveBox(Box4Play box, Direction direction){
        int[] positionBox = box.move(direction);
        int x = positionBox[0];
        int y = positionBox[1];


        Cell4Play cell = getCell(x,y);
        if (cell == null || cell.containsBox() || cell.containsWall() || cell.containsMushroom()) return false;
        matrix[x][y].addElement(box);

        box.setOnTarget(cell.containsTarget());

        return true;
    }

    public boolean movePlayer(Direction direction) {
        return commandManager.executeCommand(new movePlayerCommand(direction));
    }

    public boolean undo(){
        return commandManager.undo();
    }
    public boolean redo(){
        return commandManager.redo();
    }



    private class movePlayerCommand implements Command {
        private Box4Play movedBox = null;
        private Direction direction;
        int[] newPosition = new int[2];
        int[] oldPosition = new int[2];




        private movePlayerCommand(Direction direction) {
            this.direction = direction;
        }

        public boolean execute() {
            this.oldPosition[0] = player.getX();
            this.oldPosition[1] = player.getY();
            this.newPosition = player.move(direction);
            int newX = newPosition[0];
            int newY = newPosition[1];


            Cell4Play cell = getCell(newX, newY);

            if (cell == null) return false;

            boolean cellContainBoxAndCannotMove = false;

            if (cell.containsMushroom()){
                System.out.println("tu rentre la ouuuu");
                activeMushroom();
                setIsStone();
            }

            if (cell.containsBox()){
                Box4Play box = (Box4Play) cell.getBox();
                box.setPosition(newX, newY);
                boolean nextCellCanMove = moveBox(box,direction);
                if (nextCellCanMove) {
                    movedBox = box;
                }

                cellContainBoxAndCannotMove = !nextCellCanMove;
            }

            boolean cannotMove = cell.containsWall() || cellContainBoxAndCannotMove;

            if (cannotMove) {
                return false;
            }

            matrix[newX][newY].addElement(player);
            player.setX(newX);
            player.setY(newY);
            recalculateBoxesAndTargets();
            Cell4Play oldCell = getCell(oldPosition[0], oldPosition[1]);
            oldCell.removeElement(player);

            return true;
        }

        public void undo() {
            Cell4Play oldCell = getCell(newPosition[0],newPosition[1]);
            oldCell.removeElement(player);
            matrix[oldPosition[0]][oldPosition[1]].addElement(player);
            player.setX(oldPosition[0]);
            player.setY(oldPosition[1]);
            if (movedBox != null) {
                Cell4Play cellToClear = getCellToClearBoxFrom(oldCell, direction);
                cellToClear.removeElement(movedBox);
                oldCell.getStack().add(movedBox);
                movedBox.setPosition(newPosition[0], newPosition[1]);
            }

        }

        private Cell4Play getCellToClearBoxFrom(Cell4Play oldCell, Direction direction) {
            return switch(direction) {
                case LEFT -> getCell(newPosition[0]-1, newPosition[1]);
                case RIGHT -> getCell(newPosition[0]+1, newPosition[1]);
                case UP -> getCell(newPosition[0], newPosition[1]-1);
                case DOWN -> getCell(newPosition[0], newPosition[1]+1);
            };
        }
    }
}
