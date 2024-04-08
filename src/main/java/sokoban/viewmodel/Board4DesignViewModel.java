package sokoban.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sokoban.model.*;
import javafx.beans.binding.LongBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board4DesignViewModel extends BoardViewModel{
    private Grid4DesignViewModel gridViewModel;
    private Board4Design board;
    private  ObjectProperty<Element> selectedElement = new SimpleObjectProperty<>();

    public void setSelectedElement(Element element) {
        selectedElement.set(element);
    }

    public Element getSelectedElement() {
        return selectedElement.get();
    }

    public Board4DesignViewModel(Board4Design board4Design) {
        this.board = board4Design;
        gridViewModel = new Grid4DesignViewModel(board4Design, this);
    }
    public void createNewGrid(int width, int height) {
        board.resetGrid(width, height);
    }
    public void updateGrid(Grid4Design newGrid4Design) {
        this.board.setGrid(newGrid4Design);
        this.isModifiedProperty().set(false);
    }

    public BooleanProperty isModifiedProperty() {
        return board.isModifiedProperty();
    }

    public Grid4DesignViewModel getGridViewModel(){
        return gridViewModel;
    }
    public LongBinding filledCellsCountProperty() {
        return board.filledCellsCountProperty();
    }
    public int maxFilledCells() {
        return this.board.maxFilledCells();
    }
    public Board4Design getBoard(){
        return board;
    }

    public BooleanBinding isCharacterMissed(){
        return board.isCharMissed();
    }
    public BooleanBinding isTargetMissed(){
        return board.isTargetMissed();
    }
    public BooleanBinding isBoxMissed(){
        return board.isBoxMissed();
    }
    public BooleanBinding isSameNumberOfBoxAndTarget(){
        return board.isSameNumberOfBoxAndTarget();
    }
    public BooleanBinding isAnError(){
        return board.isAnError();
    }
    public int gridWidth(){
        return board.getGrid().getWidth();
    }
    public int gridHeight(){
        return board.getGrid().getHeight();
    }

    public void clearGrid() {
        this.board.clearGrid();
    }
    public void createRandomGrid() {
        clearGrid();
        this.board.createRandomGrid();
    }

    public BooleanBinding isEmpty() {
        this.getBoard().getGrid().invalidateBinding();
        return this.board.gridIsEmpty();
    }
    public boolean saveBoardToFile(File saveFile) {
        try (FileWriter writer = new FileWriter(saveFile)) {
            Grid4Design grid4Design = this.getBoard().getGrid();
            for (int i = 0; i < grid4Design.getGridHeight(); ++i) {
                for (int j = 0; j < grid4Design.getGridWidth(); ++j) {
                    Cell4Design cell4Design = grid4Design.getCell(j, i);
                    char charToWrite = convertCellToXsbChar(cell4Design);
                    writer.write(charToWrite);
                }
                writer.write("\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static char convertCellToXsbChar(Cell4Design cell4Design) {
        ObservableList<Element> stack = cell4Design.getStack();

        boolean hasPlayer = stack.stream().anyMatch(e -> e instanceof Player4Design);
        boolean hasBox = stack.stream().anyMatch(e -> e instanceof Box4Design);
        boolean hasTarget = stack.stream().anyMatch(e -> e instanceof Target4Design);
        boolean hasWall = stack.stream().anyMatch(e -> e instanceof Wall4Design);

        if (hasPlayer && hasTarget) {
            return '+';
        }

        else if (hasBox && hasTarget) {
            return '*';
        }

        else if (hasTarget) {
            return '.';
        }
        else if (hasWall) {
            return '#';
        }
        else if (hasPlayer) {
            return '@';
        }
        else if (hasBox) {
            return '$';
        }
        else {
            return ' ';
        }
    }
    public boolean loadBoardFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            int width = lines.stream().mapToInt(String::length).max().orElse(0);
            int height = lines.size();

            Grid4Design newGrid4Design = new Grid4Design(width, height);

            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    char cellChar = line.charAt(x);
                    List<Element> elements = convertXsbCharToElements(cellChar, x, y);
                    newGrid4Design.addElementsToCell(x, y, elements);
                }
            }
            updateGrid(newGrid4Design);
            return true;
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File Not Found");
            alert.setContentText("The file could not be found.");
            alert.showAndWait();
            return false;
        }
    }
    private static List<Element> convertXsbCharToElements(char cellChar, int x, int y) {
        List<Element> elements = new ArrayList<>();

        elements.add(new Ground4Design());
        switch (cellChar) {
            case '#':
                elements.add(new Wall4Design());
                break;
            case '.':
                elements.add(new Target4Design());
                break;
            case '$':
                elements.add(new Box4Design());
                break;
            case '@':
                Player4Design player4Design = new Player4Design(x, y);
                elements.add(player4Design);
                break;
            case '*':
                elements.add(new Box4Design());
                elements.add(new Target4Design());
                break;
            case '+':
                Player4Design player4DesignTarget = new Player4Design(x, y);
                elements.add(player4DesignTarget);
                elements.add(new Target4Design());
                break;
        }
        return elements;
    }
}
