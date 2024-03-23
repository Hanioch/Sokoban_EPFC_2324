package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sokoban.model.Board;
import javafx.beans.property.ReadOnlyObjectProperty;
import sokoban.model.Element;
import sokoban.model.Grid;
import sokoban.model.Ground;

public class CellViewModel {
    private BoardViewModel boardViewModel;
    private static final double DEFAULT_SCALE = 0.5;
    private static final double EPSILON = 1e-3;
    private final int line, col;
    private final Board board;

    public ObservableList<Element> getStack() {
        return stack;
    }

    private final ObservableList<Element> stack;

    private final SimpleDoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private final BooleanBinding mayIncrementScale = scale.lessThan(1 - EPSILON);
    private final BooleanBinding mayDecrementScale = scale.greaterThan(0.1 + EPSILON);

    CellViewModel(int line, int col, Board board, BoardViewModel boardViewModel) {
        this.line = line;
        this.col = col;
        this.board = board;
        this.boardViewModel = boardViewModel;
        stack = board.getGrid().getCell(line, col).getStack();
    }
    public void play() {
        Element selectedElement = boardViewModel.getSelectedElement();

            board.play(line, col, selectedElement);
    }


    public boolean isEmpty(){
        return board.isEmpty(line, col);
    }

    public SimpleDoubleProperty scaleProperty() {
        return scale;
    }

    public BooleanBinding mayIncrementScaleProperty() {
        return mayIncrementScale;
    }

    public BooleanBinding mayDecrementScaleProperty() {
        return mayDecrementScale;
    }

    public void incrementScale() {
        scale.set(Math.min(1, scale.get() + 0.1));
    }

    public void decrementScale() {
        scale.set(Math.max(0.1, scale.get() - 0.1));
    }

    public void resetScale() {
        scale.set(DEFAULT_SCALE);
    }
    public void removeTopElement() {
        if (!stack.isEmpty()) {
            Element topElement = stack.get(stack.size() - 1);
            if (!(topElement instanceof Ground)) {
                stack.remove(topElement);
                board.getGrid().filledCellsCount.invalidate();
            }
        }
    }
}
