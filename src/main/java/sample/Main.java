package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.objects.SeaOfThieves;

import java.util.Date;
import java.util.Random;

public class Main extends Application {

    private static final Random random = new Random(new Date().getTime());

    private static final SeaOfThieves world = new SeaOfThieves();
    private static final Operations operations = new Operations();

    private static final ScrollPane scrollPane = new ScrollPane(world.getRoot());
    private static final Scene scene = new Scene(scrollPane, 1920, 1080);

    private static int scrollX;
    private static int scrollY;

    public static Random getRandom() {
        return random;
    }
    public static SeaOfThieves getWorld() {
        return world;
    }
    public static Operations getOperations() {
        return operations;
    }
    public static Scene getScene() {
        return scene;
    }

    public static int getScrollX() {
        return scrollX;
    }

    public static int getScrollY() {
        return scrollY;
    }

    @Override
    public void start(Stage stage) {

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        getOperations().createStage(stage);

        world.getRoot().setOnMouseClicked(mouseEvent -> {
            MouseButton mouseButton = mouseEvent.getButton();
            if (mouseButton == MouseButton.PRIMARY)
                getOperations().mouseLeftClick(mouseEvent);

        });

        world.getRoot().setOnScroll(getOperations()::mouseMove);
        world.getRoot().setOnMouseMoved(getOperations()::mouseMove);

        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.DELETE && !world.getUnits().isEmpty()) {
                getOperations().deleteUnits();
            } else if (keyCode == KeyCode.INSERT) {
                getOperations().openCreateUnit();
            } else if (keyEvent.isControlDown() && keyCode == KeyCode.A) {
                getOperations().activateUnits();
            } else if (keyCode == KeyCode.G) {
                System.out.println("Створення юніта альянсу Добрих. . .");
                getOperations().createNewUnit("GOOD", getOperations().getMouseX(), getOperations().getMouseY());
            } else if (keyCode == KeyCode.B) {
                System.out.println("Створення юніта лагерю Поганих. . .");
                getOperations().createNewUnit("BAD", getOperations().getMouseX(), getOperations().getMouseY());
            } else if (keyCode == KeyCode.H) {
                getOperations().openHW();
            } else if (keyCode == KeyCode.O) {
                getOperations().requests();
            } else if (keyCode == KeyCode.K) {
                getOperations().changeAuto();
            } else if (keyEvent.isControlDown() && keyCode == KeyCode.V) {
                getOperations().copyPaste();
            } else if (keyCode == KeyCode.ESCAPE) {
                getOperations().deactivationUnits();
            } else if (keyEvent.isControlDown() && keyEvent.isAltDown() && keyCode == KeyCode.S) {
                getOperations().settings();
            } else if (keyCode == KeyCode.Z && !world.getUnits().isEmpty()) {
                getOperations().editUnit();
            } else if (keyCode == KeyCode.ENTER) {
                getOperations().activeUnitSayHello();
            } else if (keyCode == KeyCode.DIGIT0) {
                getOperations().activePro();
            } else if (keyCode == KeyCode.W || keyCode == KeyCode.A || keyCode == KeyCode.S || keyCode == KeyCode.D) {
                getOperations().handleArrowKeys(keyCode);
            }
        });

        scrollPane.viewportBoundsProperty().addListener((observable, oldBounds, bounds) -> {

            Main.scrollX = -1 * (int) bounds.getMinX();
            Main.scrollY = -1 * (int) bounds.getMinY();

            world.updateChordINFO();

//            if (world.getShips().size() == 0){
//                currentStatusINFO();
//            }

//            world.getMiniMap().getPane().setLayoutX(scrollX + 1310);
//            world.getMiniMap().getPane().setLayoutY(scrollY + scene.getHeight() - world.getMiniMap().getPane().getHeight() - 650);
//            world.getMiniMap().getMapArea().setLayoutX(scrollX*MiniMap.getSCALE());
//            world.getMiniMap().getMapArea().setLayoutY(scrollY*MiniMap.getSCALE());
        });

        stage.setScene(scene);
        stage.show();
        getOperations().createStartMacro();
        getOperations().createStartUnits();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                world.lifeCycle();

                world.infoOnTop();

                world.currentStatusINFO();
            }
        };
        timer.start();

    }

    public static void main(String[] args) {
        launch();
    }
}