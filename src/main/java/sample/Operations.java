package sample;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import sample.dlg.chooseUnitToChange.CUTC;
import sample.dlg.helpWindow.HelpWindow;
import sample.dlg.newChangeUnit.NewChangeUnit;
import sample.dlg.requestsWindow.RequestsWindow;
import sample.dlg.settings.Settings;
import sample.objects.SeaOfThieves;
import sample.objects.macro.BaseBad;
import sample.objects.macro.BaseGood;
import sample.objects.macro.Macro;
import sample.objects.macro.TreasuresCastle;
import sample.objects.micro.Actions;
import sample.objects.micro.Enjoyer;
import sample.objects.micro.Newbie;
import sample.objects.micro.Pro;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static sample.Main.getRandom;

public class Operations {
    private double mouseX = -1;
    private double mouseY = -1;

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void createStage(Stage stage) {
        System.out.println("Створення вікна. . .");
        stage.setTitle("Sea of Thieves");
        Image icon = new Image(Objects.requireNonNull(Main.class.getResource("images/iconSOT.png")).toString());
        stage.getIcons().add(icon);
        stage.setWidth(SeaOfThieves.MAX_X);
        stage.setHeight(SeaOfThieves.MAX_Y);
        stage.setMaximized(true);
    }

    public void deleteUnits() {
        for (int i = 0; i < Main.getWorld().getUnits().size(); i++) {
            Newbie newbie = Main.getWorld().getUnits().get(i);
            if (newbie.isActive()) {
                Main.getWorld().deleteUnit(newbie);
                i--;
            }
        }
    }

    public void openCreateUnit() {
        new NewChangeUnit(0, 0, -1).display();
    }

    public void editUnit() {
        new CUTC().display();
    }

    public void activateUnits() {
        Main.getWorld().getUnits().stream().filter(n -> !n.isActive()).forEach(n -> {
            n.flipActivation();
            for (Macro macro : Main.getWorld().getMacros()) {
                macro.removeUnitIn(n);
                n.setProcessing(false);
            }
        });
    }

    public void createNewUnit() {
        String type = Newbie.TYPES[getRandom().nextInt(Newbie.TYPES.length)];

        switch (type) {
            case "Newbie" -> Main.getWorld().addNewUnit(new Newbie());
            case "Enjoyer" -> Main.getWorld().addNewUnit(new Enjoyer());
            case "Pro" -> Main.getWorld().addNewUnit(new Pro());
        }
    }

    public void createNewUnit(String team, double x, double y) {
        Newbie.createNewUnit("", "", "", "", team, Double.toString(x), Double.toString(y), false);
    }

    public void openHW() {
        new HelpWindow().display();
    }

    public void requests() {
        new RequestsWindow().display();
    }

    public void changeAuto() {
        Main.getWorld().flipChangeAuto();

        if (Main.getWorld().isGoBase())
            for (Newbie unit : Main.getWorld().getUnits()) {
                for (Macro macro : Main.getWorld().getMacros())
                    macro.getUnitsIn().remove(unit);
                Main.getWorld().askWorldplanning(unit, Actions.GOBASE );
            }
    }

    public void copyPaste() {
        ArrayList<Newbie> temp = new ArrayList<>();
        for (Newbie unit : Main.getWorld().getUnits()) {
            if (unit.isActive()) {
                try {
                    Newbie clone = (Newbie)unit.clone();
                    temp.add(clone);
                } catch (CloneNotSupportedException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Помилка кронування");
                    alert.showAndWait();
                }
            }
        }
        for (Newbie unit : temp) {
            Main.getWorld().addNewUnit(unit);
        }
    }

    public void deactivationUnits() {
        Main.getWorld().getUnits().stream()
                .filter(Newbie::isActive)
                .forEach(Newbie::flipActivation);
    }

    public void settings() {
        new Settings().display();
    }

    public void handleArrowKeys(KeyCode keyCode) {
        int speed = 15;
        int dx = 0;
        int dy = 0;
        int direction = 0;
        switch (keyCode) {
            case W -> dy = -speed;
            case S -> dy = speed;
            case A -> {
                dx = -speed;
                direction = 1;
            }
            case D -> {
                dx = speed;
                direction = -1;
            }
            default -> {
                return;
            }
        }
        int finalDx = dx;
        int finalDy = dy;
        int finalDirection = direction;
        Main.getWorld().getUnits().stream()
                .filter(Newbie::isActive)
                .forEach(newbie -> {
                    newbie.move(finalDx, finalDy, finalDirection);
                    newbie.setProcessing(false);
                });
    }

    public void createStartUnits() {
        for (int i = 0; i <= Math.random() * 10; i++) {
            createNewUnit();
        }
    }

    public void createStartMacro() {
        Main.getWorld().addNewMacro(new BaseGood());
        Main.getWorld().addNewMacro(new BaseBad());
        Main.getWorld().addNewMacro(new TreasuresCastle());
    }

    public void mouseLeftClick(MouseEvent mouseEvent) {
        Optional<Newbie> lastNewbie = Main.getWorld().getUnits().stream()
                .filter(n -> n.mouseIsOn(mouseEvent.getX(), mouseEvent.getY()))
                .reduce((n1, n2) -> n2);

        if (lastNewbie.isPresent()) {
            lastNewbie.get().flipActivation();
            for (Macro macro : Main.getWorld().getMacros()) {
                macro.removeUnitIn(lastNewbie.get());
                lastNewbie.get().setProcessing(false);
            }
        }

//        for (Macro macro : Main.getWorld().getMacros()) {
//            if (macro.mouseIsOn(mouseEvent.getX(), mouseEvent.getY())) {
//                for (int i = 0; i < Main.getWorld().getUnits().size(); i++) {
//                    Newbie unit = Main.getWorld().getUnits().get(i);
//                    if (unit.isActive()) {
//                        unit.setOrder(true);
//                        unit.setBigTarget(macro);
//                    }
//                }
//                return;
//            }
//        }
    }

    public void mouseMove(Event event) {
        if (event instanceof MouseEvent) {
            mouseX = ((MouseEvent) event).getX();
            mouseY = ((MouseEvent) event).getY();
        } else if (event instanceof ScrollEvent) {
            mouseX = ((ScrollEvent) event).getX();
            mouseY = ((ScrollEvent) event).getY();
        }
    }

    public void activePro() {
        for (Newbie unit : Main.getWorld().getUnits())
            if (unit instanceof Pro)
                unit.setActive(true);
    }
}
