package sokoban.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Board;
import sokoban.model.Element;
import sokoban.model.Grid;
import javafx.beans.binding.LongBinding;
import sokoban.model.Board;

public class BoardViewModel {
    private final GridViewModel gridViewModel;
    private final Board board;
    private final ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();

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

    public static int gridWidth(){
        return Grid.getGridWidth();
    }
    public static int gridHeight(){
        return Grid.getGridHeight();
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
