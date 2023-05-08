package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.objects.macro.Macro;
import sample.objects.micro.Newbie;
import sample.objects.SeaOfThieves;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {

    private static final Random random = new Random(new Date().getTime());

    private static final SeaOfThieves world = new SeaOfThieves();
    private static final Operations operations = new Operations();

    private static final ScrollPane scrollPane = new ScrollPane(world.getRoot());
    private static final Scene scene = new Scene(scrollPane, SeaOfThieves.MAX_X, SeaOfThieves.MAX_Y);

    private static boolean stayBase = false;
    public static void setStayBase(boolean stayBase) {
        Main.stayBase = stayBase;
    }
    public static boolean isStayBase() {
        return stayBase;
    }

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
            else if (mouseButton == MouseButton.SECONDARY)
                getOperations().mouseRightClick(mouseEvent);
            else if (mouseButton == MouseButton.MIDDLE && !stayBase)
                getOperations().mouseMiddleClick(mouseEvent);

        });

        world.getRoot().setOnScroll(getOperations()::mouseMove);
        world.getRoot().setOnMouseMoved(getOperations()::mouseMove);

        scene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.DELETE && !world.getUnits().isEmpty()) {
                getOperations().deleteNewbies();
            } else if (keyCode == KeyCode.INSERT && !world.getUnits().isEmpty()) {
                getOperations().openCUTC();
            } else if (keyEvent.isControlDown() && keyCode == KeyCode.A) {
                getOperations().activateNewbies();
            } else if (keyCode == KeyCode.G) {
                System.out.println("Створення юніта альянсу Добрих. . .");
                operations.createNewUnit("GOOD", getOperations().getMouseX(), getOperations().getMouseY());
            } else if (keyCode == KeyCode.B) {
                System.out.println("Створення юніта лагерю Поганих. . .");
                operations.createNewUnit("BAD", getOperations().getMouseX(), getOperations().getMouseY());
            } else if (keyCode == KeyCode.H) {
                getOperations().openHW();
            } else if (keyCode == KeyCode.J) {
                getOperations().moveToBase();
            } else if (keyEvent.isControlDown() && keyCode == KeyCode.V) {
                getOperations().copyPaste();
            } else if (keyCode == KeyCode.ESCAPE) {
                getOperations().deactivationUnits();
            } else if (keyEvent.isControlDown() && keyEvent.isAltDown() && keyCode == KeyCode.S) {
                getOperations().settings();
            } else {
                getOperations().handleArrowKeys(keyCode);
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < world.getUnits().size(); i++) {
                    Newbie unit = world.getUnits().get(i);
                    if (stayBase && !unit.isActive()) {
                        unit.moveToBase(null);
                        for (Macro macro : world.getMacros()) {
                            if (macro.getTeam().equals(unit.getUnitTeam())
                                    && unit.getUnitContainer().getBoundsInParent().intersects(macro.getMacroContainer().getBoundsInParent())) {
                                macro.addUnit(unit);
                                i--;
                                break;
                            }
                        }
                    }
                    if (unit.isOrder()) {
                        Macro macro = unit.getBigTarget();
                        unit.moveToBase(macro);
                        if (macro.getTeam().equals(unit.getUnitTeam())
                                && unit.getUnitContainer().getBoundsInParent().intersects(macro.getMacroContainer().getBoundsInParent())) {
                            unit.setOrder(false);
                            unit.setBigTarget(null);
                            unit.setActive(false);
                            macro.addUnit(unit);
                            break;
                        }
                    }
                }
            }
        };
        timer.start();

        stage.setScene(scene);
        stage.show();
        getOperations().createStartMacro();
        getOperations().createStartNewbie();

    }

    public static void main(String[] args) {
        launch();
    }
}