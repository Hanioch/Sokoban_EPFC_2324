package sokoban.viewmodel;

import javafx.collections.ObservableList;
import sokoban.model.Element;

public class CellViewModel {
    protected ObservableList<Element> stack;
    public int line, col;
    public ObservableList<Element> getStack() {
        return stack;
    }
}
