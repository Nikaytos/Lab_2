package sample.dlg.chooseUnitToChange;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import sample.Main;
import sample.dlg.newChangeUnit.NewChangeUnit;

import java.util.ArrayList;

public class CUTCController {

    @FXML
    ComboBox<String> cBox;

    @FXML
    Button okButton;

    @FXML
    void initialize() {
        ArrayList<String> units = Main.getWorld().getUnitsNames();

        int count=1;
        for( String s:units ) {
            cBox.getItems().add(count++ +". "+ s);
        }

        okButton.setOnAction(e -> {
            if (cBox.getValue() != null) {
                String[] strChoice = cBox.getValue().split(". ");
                new NewChangeUnit(-1, -1, Integer.parseInt(strChoice[0])-1).display();
            }
            CUTC.getWindow().close();
        });
    }
}
