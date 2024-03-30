package sokoban.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.*;
import sokoban.viewmodel.BoardViewModel;


public class SaveAsDialog {

    public static boolean showSaveDialog(BoardViewModel boardViewModel) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers XSB (*.xsb)", "*.xsb");
        fileChooser.getExtensionFilters().add(filter);

        String userHomePath = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(userHomePath));

        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile != null) {
            return saveBoardToFile(boardViewModel, saveFile);
        }
        return false;
    }

    private static boolean saveBoardToFile(BoardViewModel boardViewModel, File saveFile) {
        try (FileWriter writer = new FileWriter(saveFile)) {
            Grid grid = boardViewModel.getBoard().getGrid();
            for (int i = 0; i < grid.getGridHeight(); ++i) {
                for (int j = 0; j < grid.getGridWidth(); ++j) {
                    Cell cell = grid.getCell(j, i);
                    char charToWrite = convertCellToXsbChar(cell);
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


    private static char convertCellToXsbChar(Cell cell) {
        ObservableList<Element> stack = cell.getStack();

        boolean hasPlayer = stack.stream().anyMatch(e -> e instanceof Player);
        boolean hasBox = stack.stream().anyMatch(e -> e instanceof Box);
        boolean hasTarget = stack.stream().anyMatch(e -> e instanceof Target);
        boolean hasWall = stack.stream().anyMatch(e -> e instanceof Wall);

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

}