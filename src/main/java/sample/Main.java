package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.objects.NewbieManager;
import sample.objects.SeaOfThieves;

import java.awt.*;
import java.io.IOException;

import static sample.Operations.*;

public class Main extends Application {
    //---------------------------------------------------------
    private static SeaOfThieves world = new SeaOfThieves();
    private static ScrollPane scrollPane = new ScrollPane(world.getRoot());
    private static Scene scene = new Scene(scrollPane, SeaOfThieves.MAX_X, SeaOfThieves.MAX_Y);
    private static double scrollX;
    private static double scrollY;
    //---------------------------------------------------------
    @Override
    public void start(Stage stage) {
//---------------------------------------------------------
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
//---------------------------------------------------------
//        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds>
//                                        observable, Bounds oldBounds,
//                                Bounds bounds) {
//                double scrollWidth;
//                double scrollHeight;
//                scrollX = -1 * (int) bounds.getMinX();
//                scrollWidth = -1 * (int) bounds.getMinX() + (int) bounds.getWidth();
//                scrollY = -1 * (int) bounds.getMinY();
//                scrollHeight = -1 * (int) bounds.getMinY() + bounds.getHeight();
//
//                SeaOfThieves.getBg().setLayoutX(-scrollX);
//                SeaOfThieves.getBg().setLayoutY(-scrollY);
//                SeaOfThieves.getBg().setFitWidth(SeaOfThieves.MAX_X-scrollX);
//                SeaOfThieves.getBg().setFitHeight(SeaOfThieves.MAX_Y-scrollY);
//            }
//        });
//---------------------------------------------------------
        Operations.createStage(stage);
//---------------------------------------------------------
        SeaOfThieves.getRoot().setOnMouseClicked(mouseEvent -> {
            try {
                mouseLeftClick(mouseEvent, stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//---------------------------------------------------------
        SeaOfThieves.getRoot().setOnScroll(Operations::handleEvent);
        SeaOfThieves.getRoot().setOnMouseMoved(Operations::handleEvent);
//---------------------------------------------------------
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.DELETE && !NewbieManager.newbies.isEmpty()) {
                Operations.deleteNewbies();
            }
            else if (keyCode == KeyCode.INSERT && !NewbieManager.newbies.isEmpty()) {
                Operations.openCUTCD(stage);
            }
            else if (keyEvent.isControlDown() && keyCode == KeyCode.A) {
                Operations.activateNewbies();
            }
            else if (keyCode == KeyCode.G) {
                Operations.createNewUnit("BLUE", Operations.mouseX, Operations.mouseY);
            }
            else if (keyCode == KeyCode.B) {
                Operations.createNewUnit("RED", Operations.mouseX, Operations.mouseY);
            }
            else if (keyCode == KeyCode.H) {
                Operations.openHelpWindow(stage);
            }
            else if (keyCode == KeyCode.ESCAPE) {
                Operations.deleteActivationUnits();
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