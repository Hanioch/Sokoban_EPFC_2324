package sokoban.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Grid4Play extends Grid {
    private Cell4Play[][] matrix;
    private Grid4Design oldGrid;
    private int width;
    private int height;
    private Player4Play player;
    ReadOnlyListProperty<Element> valueProperty(int line, int col) {
        return matrix[line][col].stackProperty();
    }

    public Grid4Play(int width, int height, Grid4Design oldGrid, Player4Play player) {
        super(width, height);
        this.matrix = new Cell4Play[width][height];
        this.oldGrid = oldGrid;
        this.width = width;
        this.height = height;
        this.player = player;

        /*matrix = new Cell4Play[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                matrix[i][j] = new Cell4Play();
            }
        }*/
        recreateMatrix();

    }

    private void recreateMatrix() {
        Cell4Design[][] oldMatrix = oldGrid.getMatrix();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                this.matrix[i][j] = new Cell4Play(oldMatrix[i][j].getStack(), this.player, i, j);
            }
        }
    }

    public Cell4Play getCell(int x, int y) {
        if (matrix[x][y] == null) {
            return new Cell4Play();
        }
        return matrix[x][y];
    }

    public ObservableList<Element> getStack(int line, int col) {
        ObservableList<Element> stack  = matrix[line][col].getValue();
        return stack;
    }


    public boolean canGo(int playerX, int playerY, Movable.Direction direction) {
        if (!isStillOnGrid(playerX, playerY, direction)) {
            return false;
        }
        ObservableList<Element> nextStack = getNextStack(playerX, playerY, direction);
        // vérifie que tous les éléments contenus dans le stack visé soient Movable
        return (nextStack.stream().allMatch(item -> item instanceof Movable || item instanceof Ground));
    }

    private boolean isStillOnGrid(int playerX, int playerY, Movable.Direction direction) {
        switch(direction) {
            case UP -> playerY --;
            case DOWN -> playerY ++;
            case LEFT -> playerX --;
            case RIGHT -> playerX ++;
        }
        return (playerX >= 0 && playerX < this.width && playerY >= 0 && playerY < this.height);
    }

    private ObservableList<Element> getNextStack(int playerX, int playerY, Movable.Direction direction) {
        return switch (direction) {
            case UP -> getStack(playerX, playerY - 1);
            case DOWN -> getStack(playerX, playerY + 1);
            case LEFT -> getStack(playerX - 1, playerY);
            case RIGHT -> getStack(playerX + 1, playerY);
        };
    }
}
