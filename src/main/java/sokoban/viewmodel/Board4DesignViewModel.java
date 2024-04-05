package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Board4Design;
import sokoban.model.Element;
import sokoban.model.Grid4Design;
import javafx.beans.binding.LongBinding;

public class Board4DesignViewModel extends BoardViewModel{
    private Grid4DesignViewModel gridViewModel;
    private Board4Design board;
    private  ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();

    public void setSelectedElement(Element element) {
        selectedElement.set(element);
    }

    public Element getSelectedElement() {
        return selectedElement.get();
    }

    public Board4DesignViewModel(Board4Design board4Design) {
        this.board = board4Design;
        gridViewModel = new Grid4DesignViewModel(board4Design, this);
    }
    public void createNewGrid(int width, int height) {
        board.resetGrid(width, height);
    }
    public void updateGrid(Grid4Design newGrid4Design) {
        this.board.setGrid(newGrid4Design);
        this.isModifiedProperty().set(false);
    }

    public BooleanProperty isModifiedProperty() {
        return board.isModifiedProperty();
    }

    public Grid4DesignViewModel getGridViewModel(){
        return gridViewModel;
    }
    public LongBinding filledCellsCountProperty() {
        return board.filledCellsCountProperty();
    }
    public int maxFilledCells() {
        return this.board.maxFilledCells();
    }
    public Board4Design getBoard(){
        return this.board;
    }

    public BooleanBinding isCharacterMissed(){
        return board.isCharMissed();
    }
    public BooleanBinding isTargetMissed(){
        return board.isTargetMissed();
    }
    public BooleanBinding isBoxMissed(){
        return board.isBoxMissed();
    }
    public BooleanBinding isSameNumberOfBoxAndTarget(){
        return board.isSameNumberOfBoxAndTarget();
    }
    public BooleanBinding isAnError(){
        return board.isAnError();
    }
    public int gridWidth(){
        return board.getGrid().getWidth();
    }
    public int gridHeight(){
        return board.getGrid().getHeight();
    }

    public void clearGrid() {
        this.board.clearGrid();
    }
}
