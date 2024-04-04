package sokoban.model;

import javafx.collections.ObservableList;
import sokoban.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Grid4Play extends Grid {
    private Cell4Play[][] matrix;
    private Grid4Design oldGrid;
    private int boxNumber = 0;
    public Grid4Play(int width, int height, Grid4Design oldGrid) {
        super(width, height);
        this.matrix = new Cell4Play[width][height];
        this.oldGrid = oldGrid;

        recreateMatrix();

    }

    private void recreateMatrix() {
        Cell4Design[][] oldMatrix = oldGrid.getMatrix();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                ObservableList<Element> oldStack = oldGrid.getCell(i, j).getStack();
                for (Element elem : oldStack) {
                    if (elem instanceof Box4Design) {
                       boxNumber++;
                    }
                }
                this.matrix[i][j] = new Cell4Play(oldMatrix[i][j].getStack(),boxNumber);
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
        return stack ;
    }

    public void movePlayer(Move direction){
        Player4Play j1 = new Player4Play(Player.getX(), Player.getY());

        ArrayList<Integer> position = j1.move(direction);
        System.out.println("check "+ Player.getX() );
        int x = position.get(0);
        int y = position.get(1);
        System.out.println("check 2 "+ x);

        Cell4Play cell = getCell(x,y);
        ObservableList<Element> stackDepart = getStack(Player.getX(),Player.getY());
        ObservableList<Element> stackArrive = cell.getStack();


        boolean cellContainBoxAndCannotMove =false;

        if (cell.containsBox()){
            Box4Play box = (Box4Play) cell.getBox();
            /*TODO voir comment adapter la box4Play pour que ça fonctionne, je pense qu'il faut lui donner ses positions aussi.*/
           ArrayList<Integer> positionBox = (ArrayList<Integer>) box.move(direction);
           boolean nextCellCanMove = moveBox(positionBox.get(0), positionBox.get(1));

            cellContainBoxAndCannotMove = !nextCellCanMove;
        }
        boolean cannotMove= cell.containsWall() || cellContainBoxAndCannotMove;

        if (!cannotMove) {
            //TODO tout mettre dans le viewwModel et acceder au grid via ce dernier
            System.out.println("no souci pour avancer ");
           Optional<Element> p = stackDepart.stream().filter(item -> item instanceof Player4Play).findFirst();
            stackDepart.remove(p.get());
        }else {
            System.out.println("ty avance pas le S");
        }
    }

    public boolean moveBox(int x, int y){
        Cell4Play cell = getCell(x,y);

        if (cell.containsBox()|| cell.containsWall()){
            System.out.println("cannot move");
            return false;
        }else {
            System.out.println("can move");
            return true;
        }
    }


}
