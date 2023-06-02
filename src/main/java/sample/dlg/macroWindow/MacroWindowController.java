package sample.dlg.macroWindow;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import sample.objects.micro.Newbie;

import java.util.ArrayList;

public class MacroWindowController {

    @FXML
    ChoiceBox<String> cBox;

    @FXML
    Button okButton;

    @FXML
    void initialize() {
        ArrayList<String> units = MacroWindow.getMacro().getNames();

        int count=1;
        for( String s:units ) {
            cBox.getItems().add(count++ +". "+ s);
        }

        okButton.setOnAction(e -> {
            if (cBox.getValue() != null) {
                String[] strChoice = cBox.getValue().split(". ");
                Newbie unit = MacroWindow.getMacro().getUnitsIn().get(Integer.parseInt(strChoice[0])-1);
                unit.setInMacro("null");
                MacroWindow.getMacro().removeUnitIn(unit);
            }
            MacroWindow.getWindow().close();
        });
    }
}
