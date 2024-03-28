package sokoban.view;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.*;
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
            return loadBoardFromFile(boardViewModel, openFile);
        }
        return false;
    }
    private static boolean loadBoardFromFile(BoardViewModel boardViewModel, File file) {
        try (Scanner scanner = new Scanner(file)) {
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            int width = lines.stream().mapToInt(String::length).max().orElse(0);
            int height = lines.size();

            Grid newGrid = new Grid(width, height);

            for (int y = 0; y < lines.size(); y++) {
                String line = lines.get(y);
                for (int x = 0; x < line.length(); x++) {
                    char cellChar = line.charAt(x);
                    List<Element> elements = convertXsbCharToElements(cellChar, x, y);
                    newGrid.addElementsToCell(x, y, elements);
                }
            }
            boardViewModel.updateGrid(newGrid);
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

        switch (cellChar) {
            case ' ':
                elements.add(new Ground());
                break;
            case '#':
                elements.add(new Ground());
                elements.add(new Wall());
                break;
            case '.':
                elements.add(new Ground());
                elements.add(new Target());
                break;
            case '$':
                elements.add(new Ground());
                elements.add(new Box());
                break;
            case '@':
                Player player = new Player(x, y);
                elements.add(new Ground());
                elements.add(player);
                break;
            case '*':
                elements.add(new Ground());
                elements.add(new Box());
                elements.add(new Target());
                break;
            case '+':
                Player playerTarget = new Player(x, y);
                elements.add(new Ground());
                elements.add(playerTarget);
                elements.add(new Target());
                break;
        }
        return elements;
    }

}
