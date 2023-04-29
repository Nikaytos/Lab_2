package sample.dlg;

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

public class NewChangeUnitDlg {

    private static Stage window;
    private static double x;
    private static double y;
    private static int unitIndex;


    public NewChangeUnitDlg(double x, double y, int unitIndex){
        window = new Stage();
        window.initModality(APPLICATION_MODAL);
        window.setResizable(false);

        NewChangeUnitDlg.x = x;
        NewChangeUnitDlg.y = y;
        NewChangeUnitDlg.unitIndex = unitIndex;
    }

    public static void display() throws IOException {
        window.setTitle("Створення нового персонажа");
        Parent alert = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("fxml/NewChangeUnitDlg.fxml")));
        Scene scene = new Scene(alert);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
        window.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("images/iconNCUD.png")).toString()));
        window.setScene(scene);
        window.showAndWait();
    }

    public static double getX() {
        return x;
    }

    public static double getY() {
        return y;
    }

    public static int getUnitIndex() {
        return unitIndex;
    }

    public static Stage getWindow() {
        return window;
    }
}
