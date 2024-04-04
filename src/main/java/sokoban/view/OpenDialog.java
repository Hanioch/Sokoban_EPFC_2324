package sokoban.view;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sokoban.model.*;
import sokoban.viewmodel.Board4DesignViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OpenDialog {
    public static boolean openBoardFromFile(Board4DesignViewModel board4DesignViewModel) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fichiers XSB (*.xsb)", "*.xsb");
        fileChooser.getExtensionFilters().add(filter);

        File openFile = fileChooser.showOpenDialog(stage);
        if (openFile != null) {
            return loadBoardFromFile(board4DesignViewModel, openFile);
        }
        return false;
    }
    private static boolean loadBoardFromFile(Board4DesignViewModel board4DesignViewModel, File file) {
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
            board4DesignViewModel.updateGrid(newGrid4Design);
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
                elements.add(new Ground4Design());
                break;
            case '#':
                elements.add(new Ground4Design());
                elements.add(new Wall4Design());
                break;
            case '.':
                elements.add(new Ground4Design());
                elements.add(new Target4Design());
                break;
            case '$':
                elements.add(new Ground4Design());
                elements.add(new Box4Design());
                break;
            case '@':
                Player4Design player4Design = new Player4Design(x, y);
                elements.add(new Ground4Design());
                elements.add(player4Design);
                break;
            case '*':
                elements.add(new Ground4Design());
                elements.add(new Box4Design());
                elements.add(new Target4Design());
                break;
            case '+':
                Player4Design player4DesignTarget = new Player4Design(x, y);
                elements.add(new Ground4Design());
                elements.add(player4DesignTarget);
                elements.add(new Target4Design());
                break;
        }
        return elements;
    }

}
