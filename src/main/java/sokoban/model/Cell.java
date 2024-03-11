package sokoban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Cell {
    private final ObservableList<CellValue> values = FXCollections.observableArrayList();

    public ObservableList<CellValue> getValues() {
        return values;
    }



    void addValue(CellValue value){
        switch (value){
            case GROUND,WALL :
                values.removeAll();
                values.add(value);
                break;
            case BOX,PLAYER:
                if (values.size()>1 || containsWall() ){
                    values.removeAll();
                    values.add(CellValue.GROUND);
                    values.add(value);
                }else{
                    values.add(value);
                }
                break;
            case TARGET:
                if (containsWall()){
                    values.removeAll();
                    values.add(CellValue.GROUND);
                    values.add(value);
                }else{
                    values.add(value);
                }
                break;
        }
    }

    void removeValue(CellValue value){
        switch (value){
            case WALL:
                values.remove(0);
                values.add(CellValue.GROUND);
                break;
            case BOX, PLAYER:
                values.remove(1);
                break;
            case TARGET:
                values.remove(2);
                break;
        }
    }

    boolean containsWall(){
        return values.contains(CellValue.WALL);
    }

    boolean isEmpty(){
        return values.size() == 1 && values.get(0) == CellValue.GROUND;
    }
/*
    ReadOnlyObjectProperty<CellValue> valueProperty(){
        return values;
    }*/
}
