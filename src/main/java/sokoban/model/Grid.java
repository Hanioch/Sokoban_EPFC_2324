package sokoban.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public abstract class Grid {
    public int width;
    public int height;
    public IntegerProperty widthProperty ;
    public IntegerProperty heightProperty ;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        widthProperty = new SimpleIntegerProperty(width);
        heightProperty = new SimpleIntegerProperty(height);
    }
    public void setWidth(int width) {
        widthProperty.set(width);
    }
    public void setHeight(int height) {
        heightProperty.set(height);
    }

    public  int getWidth() {
        return widthProperty.get();
    }
    public  int getGridWidth(){
        return widthProperty.get();
    }
    public  int getGridHeight(){
        return heightProperty.get();
    }

    public IntegerProperty widthProperty() {
        return widthProperty;
    }

    public  int getHeight() {
        return heightProperty.get();
    }

    public IntegerProperty heightProperty() {
        return heightProperty;
    }

    public int getArea() {
        return widthProperty.get() * heightProperty.get();
    }
}
