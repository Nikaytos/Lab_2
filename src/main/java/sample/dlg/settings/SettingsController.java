package sample.dlg.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import sample.Main;
import sample.Serialization;

import java.io.File;
import java.util.Optional;

public class SettingsController {

    @FXML
    Button okButton;

    @FXML
    Button saveButton;

    @FXML
    Button loadButton;

    @FXML
    void initialize() {
        okButton.setOnAction(e -> Settings.getWindow().close());

        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Виберіть місце для збереження");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML-збереження", "*.xml"),
                    new FileChooser.ExtensionFilter("TXT-збереження", "*.txt"),
                    new FileChooser.ExtensionFilter("Усі файли","*.*"));
            File file = fileChooser.showSaveDialog(Main.getScene().getWindow());
            if (file != null)
                Serialization.serializeNow(file);
        });

        loadButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Ви дійсно хочете це зробити?");
            alert.setContentText("Переконайтеся, що поточна гра збережена");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Виберіть файл");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML-збереження", "*.xml"),
                        new FileChooser.ExtensionFilter("TXT-збереження", "*.txt"),
                        new FileChooser.ExtensionFilter("Усі файли", "*.*"));
                File file = fileChooser.showOpenDialog(Main.getScene().getWindow());
                if (file != null)
                    Serialization.deserializeNow(file);
            }
        });
    }
}
