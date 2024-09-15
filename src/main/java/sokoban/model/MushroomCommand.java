package sokoban.model;

import java.util.ArrayList;

public class MushroomCommand implements Command{
    private Grid4Play grid;
    private Mushroom mushroom;
    private ArrayList<int[]> boxPosition = new ArrayList<>();

    public MushroomCommand(Grid4Play grid, Mushroom mushroom){
        this.grid = grid;
        this.mushroom= mushroom;
    }
    @Override
    public boolean execute() {
        //grid.activeMushroom();
 //       ArrayList<int[]> boxPosition = new ArrayList<>();
        for (int i = 0; i < grid.getWidth(); i++){
            for (int j = 0; j < grid.getHeight(); j++){
                Cell4Play cell = grid.getCell(i,j);
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
            Cell4Play cell = grid.getCell(x,y);
            Box4Play box = cell.getBox();
            cell.removeElement(box);
            grid.addBoxFreeCase((Box4Play) box);
        }

        grid.generateMushroom();
        grid.setMushroomClicked();
        return true;
    }

    @Override
    public void undo() {
        ArrayList<int[]> currentPosition = new ArrayList<>();
        for (int i = 0; i < grid.getWidth(); i++){
            for (int j = 0; j < grid.getHeight(); j++){
                Cell4Play cell = grid.getCell(i,j);
                for (Element elem : cell.getStack()) {
                    if (elem instanceof Box4Play) {
                        int[] position = new int[2];
                        position[0] = i;
                        position[1] = j;
                        currentPosition.add(position);
                    }
                }
            }
        }
        for (int i = 0 ; i<currentPosition.size(); i++){
            int[] position = currentPosition.get(i);
            int x = position[0];
            int y = position[1];
            Cell4Play cell = grid.getCell(x,y);
            Box4Play box = cell.getBox();
            cell.removeElement(box);
            int newX = boxPosition.get(i)[0];
            int newY = boxPosition.get(i)[1];

            grid.changeBoxPosition( newX,newY,(Box4Play) box);

        }
    }

    public void redo(){
        undo();
    }

    @Override
    public boolean isMushroomClicked() {
        return false;
    }


    public void showMushroom(){
        Cell4Play cellMushroom = grid.getCell(mushroom.getX(), mushroom.getY());
        Mushroom mush = cellMushroom.getMushroom();
        if (mush == null )
            cellMushroom.addElement(mushroom);
        else
            cellMushroom.removeElement(mush);
    }

    public void clicOnMushroom(int x, int y){
        if (x == mushroom.getX() && y ==mushroom.getY())
            execute();
    }
}
