module sample.oop_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sample to javafx.fxml;
    exports sample;
    exports sample.dlg;
    opens sample.dlg to javafx.fxml;
    exports sample.objects;
    opens sample.objects to javafx.fxml;
}