package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.objects.SeaOfThieves;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static sample.Operations.*;

public class Main extends Application {

    private static final Random random = new Random(new Date().getTime());

    private static SeaOfThieves world = new SeaOfThieves();
    private static ScrollPane scrollPane = new ScrollPane(world.getRoot());
    private static Scene scene = new Scene(scrollPane, SeaOfThieves.MAX_X, SeaOfThieves.MAX_Y);
    private static double scrollX;
    private static double scrollY;

    public static Random getRandom() {
        return random;
    }

    public static SeaOfThieves getWorld() {
        return world;
    }

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
        world.getRoot().setOnMouseClicked(mouseEvent -> {
            try {
                mouseLeftClick(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//---------------------------------------------------------
        world.getRoot().setOnScroll(Operations::handleEvent);
        world.getRoot().setOnMouseMoved(Operations::handleEvent);
//---------------------------------------------------------
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.DELETE && !world.getUnits().isEmpty()) {
                Operations.deleteNewbies();
            }
            else if (keyCode == KeyCode.INSERT && !world.getUnits().isEmpty()) {
                Operations.openCUTCD(stage);
            }
            else if (keyEvent.isControlDown() && keyCode == KeyCode.A) {
                Operations.activateNewbies();
            }
            else if (keyCode == KeyCode.G) {
                Operations.createNewUnit("BLUE", Operations.getMouseX(), Operations.getMouseY());
            }
            else if (keyCode == KeyCode.B) {
                Operations.createNewUnit("RED", Operations.getMouseX(), Operations.getMouseY());
            }
            else if (keyCode == KeyCode.H) {
                Operations.openHelpWindow(stage);
            }
            else if (keyCode == KeyCode.J) {
                Operations.interact();
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
        Operations.createStartMacro();
        Operations.createStartNewbie();
    }
    //---------------------------------------------------------
    public static void main(String[] args) {
        launch();
    }
}