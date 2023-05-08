package sample.dlg.macroWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.Main;
import sample.objects.macro.Macro;

import java.io.IOException;
import java.util.Objects;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class MacroWindow {
    private static Stage window;
    private static Macro macro;

    public MacroWindow(Macro macro) {
        window = new Stage();
        window.initModality(APPLICATION_MODAL);
        window.setResizable(false);

        MacroWindow.macro = macro;
    }

    public static void display() {

        window.setTitle("Виженіть юніт:");
        Parent alert;
        try {
            alert = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("fxml/macroWindow.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(alert);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
        window.setScene(scene);
        window.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("images/iconMW.jpg")).toString()));
        window.show();
    }

    public static Stage getWindow() {
        return window;
    }

    public static Macro getMacro() {
        return macro;
    }
}
