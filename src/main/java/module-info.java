module sample.oop_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sample to javafx.fxml;
    exports sample;
    exports sample.objects;
    opens sample.objects to javafx.fxml;
    exports sample.objects.macro;
    opens sample.objects.macro to javafx.fxml;
    exports sample.objects.micro;
    opens sample.objects.micro to javafx.fxml;
    exports sample.dlg.newChangeUnit;
    opens sample.dlg.newChangeUnit to javafx.fxml;
    exports sample.dlg.settings;
    opens sample.dlg.settings to javafx.fxml;
    exports sample.dlg.helpWindow;
    opens sample.dlg.helpWindow to javafx.fxml;
    exports sample.dlg.chooseUnitToChange;
    opens sample.dlg.chooseUnitToChange to javafx.fxml;
    exports sample.dlg.requestsWindow;
    opens sample.dlg.requestsWindow to javafx.fxml;
}