package sample.dlg.requestsWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.util.Objects;

import static javafx.stage.Modality.APPLICATION_MODAL;

public class RequestsWindow {
    private static Stage window;

    public RequestsWindow() {
        window = new Stage();
        window.initModality(APPLICATION_MODAL);
        window.setResizable(false);

    }

    public static void display() {

        window.setTitle("Ваш запит:");
        Parent alert;
        try {
            alert = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("fxml/requestsWindow.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(alert, 542, 160);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.O) || keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
        window.setScene(scene);
        window.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("images/iconRW.png")).toString()));
        window.show();
    }

    public static Stage getWindow() {
        return window;
    }
}
