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
    exports sample.objects.Macro;
    opens sample.objects.Macro to javafx.fxml;
    exports sample.objects.Micro;
    opens sample.objects.Micro to javafx.fxml;
    exports sample.dlg.NewChangeUnitDlg;
    opens sample.dlg.NewChangeUnitDlg to javafx.fxml;
}