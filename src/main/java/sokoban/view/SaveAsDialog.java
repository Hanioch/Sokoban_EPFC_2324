package sokoban.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.*;
import sokoban.viewmodel.Board4DesignViewModel;


public class SaveAsDialog {

    public static boolean showSaveDialog(Board4DesignViewModel board4DesignViewModel) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers XSB (*.xsb)", "*.xsb");
        fileChooser.getExtensionFilters().add(filter);

        String userHomePath = System.getProperty("user.home");
        fileChooser.setInitialDirectory(new File(userHomePath));

        File saveFile = fileChooser.showSaveDialog(stage);
        if (saveFile != null) {
            return saveBoardToFile(board4DesignViewModel, saveFile);
        }
        return false;
    }

    private static boolean saveBoardToFile(Board4DesignViewModel board4DesignViewModel, File saveFile) {
        try (FileWriter writer = new FileWriter(saveFile)) {
            Grid4Design grid4Design = board4DesignViewModel.getBoard().getGrid();
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

}