package sample.dlg.newMonsters;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sample.Main;
import sample.dlg.chooseUnitToChange.CUTC;
import sample.dlg.newChangeUnit.NewChangeUnit;
import sample.dlg.settings.Settings;

import java.util.ArrayList;
import java.util.Arrays;

public class NewMonstersController {

    @FXML
    ComboBox<String> comboBox1;

    @FXML
    ComboBox<String> comboBox2;

    @FXML
    Button button1;

    @FXML
    Button button2;

    @FXML
    void initialize() {
        ArrayList<String> units = Main.getWorld().getUnitsNames();

        int count = 1;
        for (String s : units) {
            comboBox1.getItems().add(count + ". " + s);
            comboBox2.getItems().add(count++ + ". " + s);
        }

        button1.setOnAction(e -> {
            if (comboBox1.getValue() != null && comboBox2.getValue() != null && !comboBox1.getValue().equals("") && !comboBox2.getValue().equals("")) {
                String[] strChoice1 = comboBox1.getValue().split(". ");
                String[] strChoice2 = comboBox2.getValue().split(". ");
                if (!Arrays.equals(strChoice1, strChoice2)) {
                    Main.getWorld().updateMonsters();
                    Main.getWorld().getUnits().get(Integer.parseInt(strChoice1[0])-1).setMonsterTeam(1);
                    Main.getWorld().getUnits().get(Integer.parseInt(strChoice1[0])-1).setMonster(true);
                    Main.getWorld().getUnits().get(Integer.parseInt(strChoice2[0])-1).setMonsterTeam(2);
                    Main.getWorld().getUnits().get(Integer.parseInt(strChoice2[0])-1).setMonster(true);

                    NewMonsters.getWindow().close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Помилка");
                    alert.setContentText("Оберіть різні юніти");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Помилка");
                alert.setContentText("Заповніть усі поля");
                alert.showAndWait();
            }

        });

        button2.setOnAction(e -> {
            Main.getWorld().updateMonsters();
            NewMonsters.getWindow().close();
        });
    }
}
