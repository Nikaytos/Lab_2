package sample.dlg;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Newbie;
import sample.NewbieManager;
import sample.SeaOfThieves;
import java.util.ArrayList;
import java.util.Objects;

public class NewChangeUnitDlg {
    public static void display(double x, double y, int unitIndex, Stage parentWindow) {
//---------------------------------------------------------
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initOwner(parentWindow);
        window.setResizable(false);
        window.setTitle("Введіть дані:");
//---------------------------------------------------------
        Label nameLabel = new Label("Name:");
        TextField nameText = new TextField();
        nameText.setPromptText(String.format("Ліміт - %d символів", Newbie.MAX_LENGTH_NAME));
        GridPane.setHgrow(nameText, Priority.ALWAYS);
//---------------------------------------------------------
        Label healthLabel = new Label("Health:");
        TextField healthText = new TextField();
        healthText.setPromptText(String.format("Від %d до %d", Newbie.MIN_HEALTH, Newbie.MAX_HEALTH));
        GridPane.setHgrow(healthText, Priority.ALWAYS);
//---------------------------------------------------------
        Label team = new Label("Team:");
        ComboBox<String> teamBox = new ComboBox<>();
        teamBox.getItems().add("GOOD");
        teamBox.getItems().add("BAD");
//---------------------------------------------------------
        Label xLabel = new Label("X:");
        TextField xText = new TextField();
        xText.setPromptText(String.format("Від %d до %d", Newbie.MIN_X_NEWBIE, Newbie.MAX_X_NEWBIE));
        xText.setText(Double.toString(x));
        GridPane.setHgrow(xText, Priority.ALWAYS);
//---------------------------------------------------------
        Label yLabel = new Label("Y:");
        TextField yText = new TextField();
        yText.setPromptText(String.format("Від %d до %d", Newbie.MIN_Y_NEWBIE, Newbie.MAX_Y_NEWBIE));
        yText.setText(Double.toString(y));
        GridPane.setHgrow(yText, Priority.ALWAYS);
//---------------------------------------------------------
        Button okButton = new Button("OK");
        okButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5 15 5 15; -fx-pref-height: 10px;");
        okButton.setOnAction(e -> {
            String sName = nameText.getText();
            String sHealth = healthText.getText();
            String cTeam = "";
            if (teamBox.getValue() != null) {
                if (teamBox.getValue().equals("GOOD")) {
                    cTeam = "BLUE";
                } else if (teamBox.getValue().equals("BAD")) {
                    cTeam = "RED";
                }
            }
            String sX = xText.getText();
            String sY = yText.getText();
            if(unitIndex == -1){
                Newbie.createNewUnit(sName, sHealth, cTeam, sX, sY);
            }
            else{
                Newbie.changeUnit(unitIndex, sName, sHealth, cTeam, sX, sY);
            }
            window.close();
        });
//---------------------------------------------------------
        Button xButton = new Button("X");
        xButton.setStyle("-fx-font-size: 12px; -fx-background-color: #EB5151; -fx-text-fill: white; -fx-border-radius: 5px; -fx-padding: 5 10 5 10; ");
        xButton.setOnAction(e -> {
            nameText.setText("");
            healthText.setText("");
            teamBox.setValue("");
            xText.setText("");
            yText.setText("");
        });
//---------------------------------------------------------
        ArrayList<String> paramsToChange;
        if (!(unitIndex == -1)) {
            paramsToChange = NewbieManager.getParamsToChange(unitIndex);
            nameText.setText(paramsToChange.get(0));
            healthText.setText(paramsToChange.get(1));
            teamBox.setValue(paramsToChange.get(2));
            xText.setText(paramsToChange.get(3));
            yText.setText(paramsToChange.get(4));
        }
//---------------------------------------------------------
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameText, 1, 0);
        gridPane.add(healthLabel, 0, 1);
        gridPane.add(healthText, 1, 1);
        gridPane.add(team, 0, 2);
        gridPane.add(teamBox, 1, 2);
        gridPane.add(xLabel, 0, 3);
        gridPane.add(xText, 1, 3);
        gridPane.add(yLabel, 0, 4);
        gridPane.add(yText, 1, 4);
        gridPane.add(xButton, 0, 5);
        gridPane.add(okButton, 1, 5);
        GridPane.setHalignment(xButton, HPos.CENTER);
        GridPane.setValignment(xButton, VPos.CENTER);
        GridPane.setHalignment(okButton, HPos.CENTER);
        GridPane.setValignment(okButton, VPos.BOTTOM);
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), new Insets(-8, -5, -8, -5))));
//---------------------------------------------------------
        VBox layout = new VBox(11);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().add(gridPane);
//---------------------------------------------------------
        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                window.close();
            }
        });
        Image icon = new Image(Objects.requireNonNull(SeaOfThieves.class.getResource("iconNCUD.png")).toString());
        window.getIcons().add(icon);
        window.setScene(scene);
        window.setWidth(240);
        window.setHeight(290);
        window.setX(parentWindow.getX() + (parentWindow.getWidth() - window.getWidth()) / 2);
        window.setY(parentWindow.getY() + (parentWindow.getHeight() - window.getHeight()) / 2);
        window.showAndWait();
    }
}
