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
    private static final Scene scene = new Scene(scrollPane, SeaOfThieves.MAX_X, SeaOfThieves.MAX_Y);

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
                operations.createNewUnit("GOOD", getOperations().getMouseX(), getOperations().getMouseY());
            } else if (keyCode == KeyCode.B) {
                System.out.println("Створення юніта лагерю Поганих. . .");
                operations.createNewUnit("BAD", getOperations().getMouseX(), getOperations().getMouseY());
            } else if (keyCode == KeyCode.H) {
                getOperations().openHW();
            } else if (keyCode == KeyCode.O) {
                getOperations().requests();
            } else if (keyEvent.isControlDown() && keyCode == KeyCode.V) {
                getOperations().copyPaste();
            } else if (keyCode == KeyCode.ESCAPE) {
                getOperations().deactivationUnits();
            } else if (keyEvent.isControlDown() && keyEvent.isAltDown() && keyCode == KeyCode.S) {
                getOperations().settings();
            } else if (keyCode == KeyCode.Z && !world.getUnits().isEmpty()) {
                getOperations().editUnit();
            } else if (keyCode == KeyCode.W || keyCode == KeyCode.A || keyCode == KeyCode.S || keyCode == KeyCode.D) {
                getOperations().handleArrowKeys(keyCode);
            }
        });

        stage.setScene(scene);
        stage.show();
        getOperations().createStartMacro();
        getOperations().createStartNewbie();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                world.lifeCycle();
            }
        };
        timer.start();

    }

    public static void main(String[] args) {
        launch();
    }
}