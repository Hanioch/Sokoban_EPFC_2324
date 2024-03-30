package sokoban.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Board;
import sokoban.model.Element;
import sokoban.model.Grid;
import javafx.beans.binding.LongBinding;

public class BoardViewModel {
    private  GridViewModel gridViewModel;
    private  Board board;
    private  ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();

    public void setSelectedElement(Element element) {
        selectedElement.set(element);
    }

    public Element getSelectedElement() {
        return selectedElement.get();
    }
    public BoardViewModel(Board board) {
        this.board = board;
        gridViewModel = new GridViewModel(board, this);
    }
    public void createNewGrid(int width, int height) {
        board.resetGrid(width, height);

    }
    public void updateGrid(Grid newGrid) {
        this.board.setGrid(newGrid);
        this.isModifiedProperty().set(false);
    }
    public int gridWidth(){
        return board.getGrid().getWidth();
    }
    public BooleanProperty isModifiedProperty() {
        return board.isModifiedProperty();
    }
    public int gridHeight(){
        return board.getGrid().getHeight();
    }
    public GridViewModel getGridViewModel(){
        return gridViewModel;
    }
    public LongBinding filledCellsCountProperty() {
        return board.filledCellsCountProperty();
    }
    public int maxFilledCells() {
        return this.board.maxFilledCells();
    }
    public Board getBoard(){
        return this.board;
    }
}
