package sample.dlg;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.util.Objects;

public class HelpWindow {
    public static void display(Stage parentWindow) {
//---------------------------------------------------------
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Довідка");
        Image icon = new Image(Objects.requireNonNull(Main.class.getResource("images/iconHW.png")).toString());
        window.getIcons().add(icon);
        window.setResizable(false);
//---------------------------------------------------------
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
//---------------------------------------------------------
        Scene scene = new Scene(root, 400, 350);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.H) || keyEvent.getCode().equals(KeyCode.ESCAPE)) window.close();
        });
//---------------------------------------------------------
        Label titleLabel = new Label("Список клавіш та їх опис:");
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.WHITE);
//---------------------------------------------------------
        StackPane titlePane = new StackPane(titleLabel);
        titlePane.setStyle("-fx-background-color: #336699; -fx-padding: 5px;");
        root.getChildren().add(titlePane);
//---------------------------------------------------------
        GridPane keysGrid = new GridPane();
        keysGrid.setHgap(10);
        keysGrid.setVgap(5);
        keysGrid.setAlignment(Pos.CENTER);
//---------------------------------------------------------
        addKeyDescription(keysGrid, 0, "W або ↑", "Рух вгору");
        addKeyDescription(keysGrid, 1, "S або ↓", "Рух вниз");
        addKeyDescription(keysGrid, 2, "A або ←", "Рух вліво");
        addKeyDescription(keysGrid, 3, "D або →", "Рух вправо");
        addKeyDescription(keysGrid, 4, "ЛКМ", "Створення або виділення об'єкта");
        addKeyDescription(keysGrid, 5, "ПКМ", "Видалити виділення всіх об'єктів");
        addKeyDescription(keysGrid, 6, "G", "Добавити юніт альянсу Хороших");
        addKeyDescription(keysGrid, 7, "B", "Добавити юніт лагерю Поганих");
        addKeyDescription(keysGrid, 8, "CTRL+A", "Виділити всі об'єкти");
        addKeyDescription(keysGrid, 9, "INS", "Змінити об'єкти");
        addKeyDescription(keysGrid, 10, "DEL", "Видалити об'єкти");
//---------------------------------------------------------
        StackPane keysPane = new StackPane(keysGrid);
        keysPane.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px;");
        root.getChildren().add(keysPane);
//---------------------------------------------------------
        window.setScene(scene);
        window.setWidth(400);
        window.setHeight(380);
        window.setX(parentWindow.getX() + (parentWindow.getWidth() - window.getWidth()) / 2);
        window.setY(parentWindow.getY() + (parentWindow.getHeight() - window.getHeight()) / 2);
        window.showAndWait();
    }
//---------------------------------------------------------
    private static void addKeyDescription(GridPane grid, int row, String key, String description) {
        Label keyLabel = new Label(key);
        Label descriptionLabel = new Label(description);
        keyLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        descriptionLabel.setFont(Font.font("Verdana", 12));
        grid.addRow(row, keyLabel, descriptionLabel);
        GridPane.setHalignment(keyLabel, HPos.RIGHT);
    }
}
