package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import sample.objects.SeaOfThieves;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    private static final Random random = new Random(new Date().getTime());

    private static final SeaOfThieves world = new SeaOfThieves();
    private static final Operations operations = new Operations();

    private static final ScrollPane scrollPane = new ScrollPane(world.getRoot());
    private static final Scene scene = new Scene(scrollPane, SeaOfThieves.MAX_X, SeaOfThieves.MAX_Y);
//    private static double scrollX;
//    private static double scrollY;

    public static Random getRandom() {
        return random;
    }
    public static SeaOfThieves getWorld() {
        return world;
    }
    public static Operations getOperations() {
        return operations;
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
        getOperations().createStage(stage);
//---------------------------------------------------------
        world.getRoot().setOnMouseClicked(mouseEvent -> {
            try {
                getOperations().mouseLeftClick(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//---------------------------------------------------------
        world.getRoot().setOnScroll(getOperations()::handleEvent);
        world.getRoot().setOnMouseMoved(getOperations()::handleEvent);
//---------------------------------------------------------
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.DELETE && !world.getUnits().isEmpty()) {
                getOperations().deleteNewbies();
            }
            else if (keyCode == KeyCode.INSERT && !world.getUnits().isEmpty()) {
                getOperations().openCUTCD(stage);
            }
            else if (keyEvent.isControlDown() && keyCode == KeyCode.A) {
                getOperations().activateNewbies();
            }
            else if (keyCode == KeyCode.G) {
                operations.createNewUnit("BLUE", getOperations().getMouseX(), getOperations().getMouseY());
            }
            else if (keyCode == KeyCode.B) {
                operations.createNewUnit("RED", getOperations().getMouseX(), getOperations().getMouseY());
            }
            else if (keyCode == KeyCode.H) {
                getOperations().openHelpWindow(stage);
            }
            else if (keyCode == KeyCode.J) {
                getOperations().interact();
            }
            else if (keyEvent.isControlDown() && keyCode == KeyCode.V) {
                System.out.println("1");
                getOperations().copyPast();
            }
            else if (keyCode == KeyCode.ESCAPE) {
                getOperations().deleteActivationUnits();
            }
            else {
                getOperations().handleArrowKeys(keyCode);
            }
        });
//---------------------------------------------------------
        stage.setScene(scene);
        stage.show();
        getOperations().createStartMacro();
        getOperations().createStartNewbie();
    }
    //---------------------------------------------------------
    public static void main(String[] args) {
        launch();
    }
}