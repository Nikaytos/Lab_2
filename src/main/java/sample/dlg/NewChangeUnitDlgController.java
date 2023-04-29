package sample.dlg;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.objects.Newbie;
import sample.objects.NewbieManager;

import java.util.ArrayList;

import static sample.dlg.NewChangeUnitDlg.*;

public class NewChangeUnitDlgController {

    @FXML
    TextField nameText;

    @FXML
    TextField healthText;

    @FXML
    ComboBox<String> teamBox;

    @FXML
    TextField xText;

    @FXML
    TextField yText;

    @FXML
    Button okButton;

    @FXML
    Button xButton;

    @FXML
    void initialize() {
        nameText.setPromptText(String.format("Ліміт - %d символів", Newbie.MAX_LENGTH_NAME));
        healthText.setPromptText(String.format("Від %d до %d", Newbie.MIN_HEALTH, Newbie.MAX_HEALTH));
        teamBox.getItems().add("GOOD");
        teamBox.getItems().add("BAD");
        xText.setPromptText(String.format("Від %d до %d", Newbie.MIN_X_NEWBIE, Newbie.MAX_X_NEWBIE));
        xText.setText(Double.toString(getX()));
        yText.setPromptText(String.format("Від %d до %d", Newbie.MIN_Y_NEWBIE, Newbie.MAX_Y_NEWBIE));
        yText.setText(Double.toString(getY()));

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
            if(getUnitIndex() == -1){
                Newbie.createNewUnit(sName, sHealth, cTeam, sX, sY);
            }
            else{
                Newbie.changeUnit(getUnitIndex(), sName, sHealth, cTeam, sX, sY);
            }
            NewChangeUnitDlg.getWindow().close();
        });

        xButton.setOnAction(e -> {
            nameText.setText("");
            healthText.setText("");
            teamBox.setValue("");
            xText.setText("");
            yText.setText("");
        });

        ArrayList<String> paramsToChange;
        if (!(getUnitIndex() == -1)) {
            paramsToChange = NewbieManager.getParamsToChange(getUnitIndex());
            nameText.setText(paramsToChange.get(0));
            healthText.setText(paramsToChange.get(1));
            teamBox.setValue(paramsToChange.get(2));
            xText.setText(paramsToChange.get(3));
            yText.setText(paramsToChange.get(4));
        }



    }
}
