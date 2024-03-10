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
            for (int i = 0; i < gridViewModel.getHeight(); i++) {
                for (int j = 0; j < gridViewModel.getWidth(); j++) {
                    CellValue baseValue = gridViewModel.getGrid().getValue(j, i);
                    CellValue overlayValue = gridViewModel.getGrid().getOverlay(j, i);
                    System.out.println("Base: " + baseValue + ", Overlay: " + overlayValue);
                    char charToWrite = convertCellValueToXsbChar(baseValue, overlayValue);
                    System.out.println("result:"+charToWrite);
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

    private static char convertCellValueToXsbChar(CellValue baseValue, CellValue overlayValue) {
        if (overlayValue == CellValue.TARGET) {
            if (baseValue == CellValue.PLAYER) {
                return '+';
            } else if (baseValue == CellValue.BOX) {
                return '*';
            } else if (baseValue == CellValue.GROUND) {
                return '.';
            }
        }

        switch (baseValue) {
            case GROUND: return '-';
            case WALL: return '#';
            case BOX: return '$';
            case PLAYER: return '@';
            default: return '?';
        }
    }

}


