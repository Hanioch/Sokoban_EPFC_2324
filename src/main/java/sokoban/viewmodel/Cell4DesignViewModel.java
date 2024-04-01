package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import sokoban.model.*;

public class Cell4DesignViewModel extends CellViewModel {
    private Board4DesignViewModel board4DesignViewModel;
    private static double DEFAULT_SCALE = 0.5;
    private static double EPSILON = 1e-3;
    private  int line, col;
    private Board4Design board4Design;

    public ObservableList<Element> getStack() {
        return stack;
    }

    private  ObservableList<Element> stack;
    private  SimpleDoubleProperty scale = new SimpleDoubleProperty(DEFAULT_SCALE);
    private  BooleanBinding mayIncrementScale = scale.lessThan(1 - EPSILON);
    private  BooleanBinding mayDecrementScale = scale.greaterThan(0.1 + EPSILON);

    Cell4DesignViewModel(int line, int col, Board4Design board4Design, Board4DesignViewModel board4DesignViewModel) {
        this.line = line;
        this.col = col;
        this.board4Design = board4Design;
        this.board4DesignViewModel = board4DesignViewModel;
        stack = board4Design.getGrid().getCell(line, col).getStack();
    }

    public ReadOnlyListProperty<Element> valueProperty() {
        return board4Design.valueProperty(line, col);
    }
    public void play() {
        Element selectedElement = board4DesignViewModel.getSelectedElement();
        if (selectedElement instanceof Player4Design) {
            stack.remove((selectedElement));
        }
        board4Design.play(line, col, selectedElement);
    }

    public boolean isEmpty(){
        return board4Design.isEmpty(line, col);
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
            if (!(topElement instanceof Ground4Design)) {
                stack.remove(topElement);
                if (topElement instanceof Player4Design) {
                    ((Player4Design) topElement).removePlayer();
                }
                Grid4Design grid4Design = board4Design.getGrid();
                grid4Design.filledCellsCount.invalidate();
                grid4Design.invalidateBinding();

            }
        }
    }
}
