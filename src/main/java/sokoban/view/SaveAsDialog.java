package sokoban.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.Cell;
import sokoban.model.CellValue;
import sokoban.model.Grid;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.CellViewModel;
import sokoban.viewmodel.GridViewModel;

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
            GridViewModel gridViewModel = boardViewModel.getGridViewModel();
            Grid grid = gridViewModel.getGrid();

            for (int i = 0; i < gridViewModel.getWidth(); i++) {
                for (int j = 0; j < gridViewModel.getHeight(); j++) {
                    CellValue cellValue = grid.getValue(i, j);
                    writer.write(convertCellValueToXsbChar(cellValue));
                }
                writer.write("\n");
            }


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static char convertCellValueToXsbChar(CellValue value) {
        return switch (value) {
            case GROUND -> '-';
            case WALL -> '#';
            case TARGET -> '.';
            case BOX -> '$';
            case PLAYER -> '@';
            default -> '?';
        };
    }
}


