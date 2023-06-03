package sample.dlg.newChangeUnit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.Main;
import sample.objects.micro.Newbie;

import java.util.ArrayList;

import static sample.dlg.newChangeUnit.NewChangeUnit.*;

public class NewChangeUnitController {

    @FXML
    ComboBox<String> typeBox;

    @FXML
    TextField nameText;

    @FXML
    TextField healthText;

    @FXML
    TextField coinsText;

    @FXML
    ComboBox<String> teamBox;

    @FXML
    TextField xText;

    @FXML
    TextField yText;

    @FXML
    CheckBox activeCB;

    @FXML
    Button okButton;

    @FXML
    Button xButton;

    @FXML
    void initialize() {
        typeBox.getItems().add("Newbie");
        typeBox.getItems().add("Enjoyer");
        typeBox.getItems().add("Pro");
        teamBox.getItems().add("BAD");
        nameText.setPromptText(String.format("Ліміт - %d символів", Newbie.MAX_LENGTH_NAME));
        healthText.setPromptText(String.format("Від %d до %d", Newbie.MIN_HEALTH, Newbie.MAX_HEALTH));
        coinsText.setPromptText(String.format("Від %d до %d", Newbie.MIN_COINS, Newbie.MAX_COINS));
        teamBox.getItems().add("GOOD");
        teamBox.getItems().add("BAD");
        xText.setPromptText(String.format("Від %d до %d", Newbie.getMIN_UNIT().x, Newbie.getMAX_UNIT().x));
        xText.setText(Double.toString(getX()));
        yText.setPromptText(String.format("Від %d до %d", Newbie.getMIN_UNIT().y, Newbie.getMAX_UNIT().y));
        yText.setText(Double.toString(getY()));

        okButton.setOnAction(e -> {
            String cType = "";
            if (typeBox.getValue() != null) {
                cType = typeBox.getValue();
            }
            String sName = nameText.getText();
            String sHealth = healthText.getText();
            String sCoins = coinsText.getText();
            String cTeam = "";
            if (teamBox.getValue() != null) {
                cTeam = teamBox.getValue();
            }
            String sX = xText.getText();
            String sY = yText.getText();
            boolean action = activeCB.isSelected();
            if(getUnitIndex() == -1){
                Newbie.createNewUnit(cType, sName, sHealth, sCoins, cTeam, sX, sY, action);
            }
            else{
                Newbie.changeUnit(getUnitIndex(), cType, sName, sHealth, sCoins, cTeam, sX, sY, action);
            }
            NewChangeUnit.getWindow().close();
        });

        xButton.setOnAction(e -> {
            typeBox.setValue("");
            nameText.setText("");
            healthText.setText("");
            coinsText.setText("");
            teamBox.setValue("");
            xText.setText("");
            yText.setText("");
        });

        ArrayList<String> paramsToChange;
        if (!(getUnitIndex() == -1)) {
            paramsToChange = Main.getWorld().getParamsToChange(getUnitIndex());
            typeBox.setValue(paramsToChange.get(0));
            nameText.setText(paramsToChange.get(1));
            healthText.setText(paramsToChange.get(2));
            coinsText.setText(paramsToChange.get(3));
            teamBox.setValue(paramsToChange.get(4));
            xText.setText(paramsToChange.get(5));
            yText.setText(paramsToChange.get(6));
            activeCB.setSelected(Boolean.parseBoolean(paramsToChange.get(7)));
        }
    }
}
