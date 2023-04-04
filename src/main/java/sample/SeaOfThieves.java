package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.dlg.NewChangeUnitDlg;
import java.awt.*;

public class SeaOfThieves extends Application { 
//---------------------------------------------------------
    public static Group group = new Group();
//---------------------------------------------------------
    @Override
    public void start(Stage stage) {
//---------------------------------------------------------
        Operations.createBackgroundImage();
//---------------------------------------------------------
        Operations.setMaxXY(Operations.width, Operations.height);
        stage.xProperty().addListener((observable, oldValue, newValue) -> {
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
            if (device != null) {
                Operations.visualBounds = Screen.getPrimary().getVisualBounds();
                Operations.taskbarSize = (Screen.getPrimary().getBounds().getMaxY() - Operations.visualBounds.getMaxY())*1.5;
                Operations.width = (int) (Operations.visualBounds.getMaxX());
                Operations.height = (int) ((Screen.getPrimary().getBounds().getMaxY() - Operations.taskbarSize));
                Operations.setMaxXY(Operations.width, Operations.height);
                Operations.bg.setFitWidth(Operations.MAX_X+5);
                Operations.bg.setFitHeight(Operations.MAX_Y+5);
            }
        });
//---------------------------------------------------------
        Operations.createStage(stage);
//---------------------------------------------------------
        Scene scene = new Scene(group);
//---------------------------------------------------------
        scene.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                boolean newbieActivated = NewbieManager.newbies.stream()
                        .filter(n -> n.mouseActivate(mouseEvent.getX(), mouseEvent.getY()))
                        .peek(n -> { if (n.isActive()) Operations.lastActive = n; })
                        .findFirst().isPresent();
                if (!newbieActivated) {
                    NewChangeUnitDlg.display(mouseEvent.getX(), mouseEvent.getY(), -1, stage);
                    System.out.println("Got control back!");
                }
            }
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                NewbieManager.newbies.stream()
                        .filter(Newbie::isActive)
                        .forEach(Newbie::flipActivation);
            }
        });
//---------------------------------------------------------
        scene.setOnMouseMoved(event -> {
            Operations.mouseX = event.getX();
            Operations.mouseY = event.getY();
        });
//---------------------------------------------------------
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.DELETE && !NewbieManager.newbies.isEmpty()) {
                Operations.deleteNewbies();
            }
            else if (keyCode == KeyCode.INSERT && !NewbieManager.newbies.isEmpty()) {
                Operations.openChooseUnitToChangeDlg(stage);
            }
            else if (keyEvent.isControlDown() && keyCode == KeyCode.A) {
                Operations.activateNewbies();
            }
            else if (keyCode == KeyCode.B) {
                Operations.createNewUnit("BLUE", Operations.mouseX, Operations.mouseY);
            }
            else if (keyCode == KeyCode.R) {
                Operations.createNewUnit("RED", Operations.mouseX, Operations.mouseY);
            }
            else if (keyCode == KeyCode.H) {
                Operations.openHelpWindow(stage);
            }
            else {
                Operations.handleArrowKeys(keyCode);
            }
        });
//---------------------------------------------------------
        stage.setScene(scene);
        stage.show();
        Operations.createStartNewbie();
    }
//---------------------------------------------------------
    public static void main(String[] args) {
        launch();
    }
}