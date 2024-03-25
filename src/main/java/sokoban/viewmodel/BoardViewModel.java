package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import sokoban.model.Board;
import sokoban.model.Element;
import sokoban.model.Grid;
import javafx.beans.binding.LongBinding;

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

    public BooleanBinding isCharacterMissed(){
 //       System.out.println("check viewModel" + board.isCharacterMissed().get());

        return board.isCharacterMissed();
    }
    public BooleanBinding isTargetMissed(){
        return board.isTargetMissed();
    } public BooleanBinding isBoxMissed(){
        return board.isBoxMissed();
    } public BooleanBinding isSameNumberOfBoxAndTarget(){
        return board.isSameNumberOfBoxAndTarget();
    }
}
