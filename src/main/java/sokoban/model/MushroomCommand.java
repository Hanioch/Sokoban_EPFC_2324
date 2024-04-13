package sokoban.model;

public class MushroomCommand implements Command{
    private Grid4Play grid;
    private Mushroom mushroom;

    public MushroomCommand(Grid4Play grid, Mushroom mushroom){
        this.grid = grid;
        this.mushroom= mushroom;
    }
    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void undo() {}


    public void showMushroom(){
        Cell4Play cellMushroom = grid.getCell(mushroom.getX(), mushroom.getY());
        Mushroom mush = cellMushroom.getMushroom();
        if (mush == null )
            cellMushroom.addElement(mushroom);
        else
            cellMushroom.removeElement(mush);
    }
}
