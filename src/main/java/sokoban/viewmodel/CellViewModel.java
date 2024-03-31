package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import sokoban.model.*;

public class CellViewModel {
    private BoardViewModel boardViewModel;
    private static double DEFAULT_SCALE = 0.5;
    private static double EPSILON = 1e-3;
    private  int line, col;
    private  Board board;

    public ObservableList<Element> getStack() {
        return stack;
    }

    private  ObservableList<Element> stack;
    private  SimpleDoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private  BooleanBinding mayIncrementScale = scale.lessThan(1 - EPSILON);
    private  BooleanBinding mayDecrementScale = scale.greaterThan(0.1 + EPSILON);

    CellViewModel(int line, int col, Board board, BoardViewModel boardViewModel) {
        this.line = line;
        this.col = col;
        this.board = board;
        this.boardViewModel = boardViewModel;
        stack = board.getGrid().getCell(line, col).getStack();
    }

    public ReadOnlyListProperty<Element> valueProperty() {
        return board.valueProperty(line, col);
    }
    public void play() {
        Element selectedElement = boardViewModel.getSelectedElement();
        if (selectedElement instanceof Player) {
            stack.remove((selectedElement));
        }
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
                if (topElement instanceof Player) {
                    ((Player) topElement).removePlayer();
                }
                Grid grid = board.getGrid();
                grid.filledCellsCount.invalidate();
                grid.invalidateBinding();

            }
        }
    }
}
