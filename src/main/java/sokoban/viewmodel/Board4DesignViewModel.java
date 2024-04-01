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
    private Grid4DesignViewModel grid4DesignViewModel;
    private Board4Design board4Design;
    private  ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();

    public void setSelectedElement(Element element) {
        selectedElement.set(element);
    }

    public Element getSelectedElement() {
        return selectedElement.get();
    }

    public Board4DesignViewModel(Board4Design board4Design) {
        this.board4Design = board4Design;
        grid4DesignViewModel = new Grid4DesignViewModel(board4Design, this);
    }
    public void createNewGrid(int width, int height) {
        board4Design.resetGrid(width, height);

    }
    public void updateGrid(Grid4Design newGrid4Design) {
        this.board4Design.setGrid(newGrid4Design);
        this.isModifiedProperty().set(false);
    }
    public int gridWidth(){
        return board4Design.getGrid().getWidth();
    }
    public BooleanProperty isModifiedProperty() {
        return board4Design.isModifiedProperty();
    }
    public int gridHeight(){
        return board4Design.getGrid().getHeight();
    }
    public Grid4DesignViewModel getGridViewModel(){
        return grid4DesignViewModel;
    }
    public LongBinding filledCellsCountProperty() {
        return board4Design.filledCellsCountProperty();
    }
    public int maxFilledCells() {
        return this.board4Design.maxFilledCells();
    }
    public Board4Design getBoard(){
        return this.board4Design;
    }

    public BooleanBinding isCharacterMissed(){
        return board4Design.isCharMissed();
    }
    public BooleanBinding isTargetMissed(){
        return board4Design.isTargetMissed();
    }
    public BooleanBinding isBoxMissed(){
        return board4Design.isBoxMissed();
    }
    public BooleanBinding isSameNumberOfBoxAndTarget(){
        return board4Design.isSameNumberOfBoxAndTarget();
    }
    public BooleanBinding isAnError(){
        return board4Design.isAnError();
    }
}
