package sokoban.view;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.Dimension;
import javafx.geometry.Dimension2D;
import java.util.Optional;

public class NewGameDialog extends Dialog<Dimension> {

    private final TextField widthField = new TextField("15");
    private final TextField heightField = new TextField("10");
    private final Label widthErrorLabel = new Label();
    private final Label heightErrorLabel = new Label();
    private final StringProperty widthTextProperty = new SimpleStringProperty();
    private final StringProperty heightTextProperty = new SimpleStringProperty();

    public NewGameDialog() {
        setTitle("Sokoban");
        setHeaderText("Give new game dimensions");

        setupForm();
        setupBindings();
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    int width = Integer.parseInt(widthField.getText());
                    int height = Integer.parseInt(heightField.getText());
                    return new Dimension(width, height);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });
    }

    private void setupForm() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(new Label("Width"), 0, 0);
        grid.add(widthField, 1, 0);
        grid.add(widthErrorLabel, 1, 1);
        grid.add(new Label("Height"), 0, 2);
        grid.add(heightField, 1, 2);
        grid.add(heightErrorLabel, 1, 3);

        VBox vbox = new VBox(grid);
        vbox.setPadding(new Insets(10, 10, 0, 10));

        getDialogPane().setContent(vbox);

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        widthErrorLabel.setTextFill(Color.RED);
        heightErrorLabel.setTextFill(Color.RED);
    }

    private void setupBindings() {
        widthTextProperty.bind(widthField.textProperty());
        heightTextProperty.bind(heightField.textProperty());

        widthErrorLabel.textProperty().bind(Bindings.createStringBinding(() ->
                validateDimension(widthTextProperty.get(), "Width"), widthTextProperty));

        heightErrorLabel.textProperty().bind(Bindings.createStringBinding(() ->
                validateDimension(heightTextProperty.get(), "Height"), heightTextProperty));

        final Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.disableProperty().bind(
                widthErrorLabel.textProperty().isNotEmpty().or(
                        heightErrorLabel.textProperty().isNotEmpty()));
    }

    private String validateDimension(String input, String dimensionName) {
        try {
            int value = Integer.parseInt(input);
            if (value < 10) {
                return dimensionName + " must be at least 10.";
            } else if (value > 50) {
                return dimensionName + " must be at most 50.";
            }
        } catch (NumberFormatException e) {
            return dimensionName + " must be a number.";
        }
        return "";
    }
    public Dimension showDimension() {
        Optional<Dimension> result = this.showAndWait();
        return result.orElse(null);
    }
}

