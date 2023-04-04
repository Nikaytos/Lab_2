package sample.dlg;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.NewbieManager;
import sample.SeaOfThieves;

import java.util.ArrayList;
import java.util.Objects;

public class ChooseUnitToChangeDlg {
    public static void display(Stage parentWindow) {
//---------------------------------------------------------
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(parentWindow);
        window.setTitle("Оберіть об'єкт для зміни:");
        window.setResizable(false);
//---------------------------------------------------------
        ArrayList<String> units = NewbieManager.getNames();
//---------------------------------------------------------
        ComboBox<String> cBox = new ComboBox<>();
        cBox.setMaxWidth(410);
//---------------------------------------------------------
        int count=1;
        for( String s:units ) {
            cBox.getItems().add(count +" "+ s);
            count++;
        }
//---------------------------------------------------------
        Button okButton = new Button("OK");
        okButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px;");
        okButton.setOnAction(e -> {
            if (cBox.getValue() != null) {
                String[] strChoice = cBox.getValue().split(" ");
                NewChangeUnitDlg.display(-1, -1, Integer.parseInt(strChoice[0])-1, parentWindow);
            }
            window.close();
        });
//---------------------------------------------------------
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(cBox, okButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), new Insets(-5))));
//---------------------------------------------------------
        StackPane layout = new StackPane();
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().addAll(hbox);
        layout.setAlignment(Pos.CENTER);
//---------------------------------------------------------
        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.INSERT) || keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
        Image icon = new Image(Objects.requireNonNull(SeaOfThieves.class.getResource("iconCUTCD.png")).toString());
        window.getIcons().add(icon);
        window.setScene(scene);
        window.setWidth(550);
        window.setHeight(100);
        window.setX(parentWindow.getX() + (parentWindow.getWidth() - window.getWidth()) / 2);
        window.setY(parentWindow.getY() + (parentWindow.getHeight() - window.getHeight()) / 2);
        window.showAndWait();
    }
}