package sokoban.viewmodel;

import sokoban.model.Board;
import sokoban.model.CellValue;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CellViewModel {
    private final int line, col;
    private final Board board;

    CellViewModel(int line, int col, Board board) {
        this.line = line;
        this.col = col;
        this.board = board;
    }
    public void play() {
        board.play(line, col);
    }
    public ReadOnlyObjectProperty<CellValue> valueProperty(){
        return board.valueProperty(line, col);
    }
    public boolean isEmpty(){
        return board.isEmpty(line, col);
    }
}
