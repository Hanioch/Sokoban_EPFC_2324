package sokoban.view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.model.CellValue;
import sokoban.model.Grid;
import sokoban.viewmodel.BoardViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OpenDialog {
    public static boolean openBoardFromFile(BoardViewModel boardViewModel, BoardView boardView) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers XSB (*.xsb)", "*.xsb");
        fileChooser.getExtensionFilters().add(filter);

        File openFile = fileChooser.showOpenDialog(stage);
        if (openFile != null) {
            return loadBoardFromFile(boardViewModel, openFile,boardView);
        }
        return false;
    }

    private static boolean loadBoardFromFile(BoardViewModel boardViewModel, File openFile, BoardView boardView) {
        try (Scanner scanner = new Scanner(openFile)) {

            int width = 0;
            int height = 0;
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
                width = Math.max(width, line.length());
                height++;
            }

            Board newBoard = new Board(width, height);
            int rowIndex = 0;
            for (String line : lines) {
                for (int columnIndex = 0; columnIndex < line.length(); columnIndex++) {
                    char cellChar = line.charAt(columnIndex);
                    CellValue cellValue = convertXsbCharToCellValue(cellChar);
                    System.out.println(cellValue);
                    newBoard.getGrid().setValue(columnIndex, rowIndex, cellValue);
                }
                rowIndex++;
            }

            BoardViewModel newBoardViewModel = new BoardViewModel(newBoard);

            boardView.updateBoardView(newBoardViewModel);
            boardViewModel.isModifiedProperty().set(false);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static CellValue convertXsbCharToCellValue(char cellChar) {
        return switch (cellChar) {
            case '-' -> CellValue.GROUND;
            case '#' -> CellValue.WALL;
            case '.' -> CellValue.TARGET;
            case '$' -> CellValue.BOX;
            case '@' -> CellValue.PLAYER;
            default -> CellValue.GROUND;
        };
    }
}
