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
import sample.dlg.macroWindow.MacroWindow;
import sample.dlg.newChangeUnit.NewChangeUnit;
import sample.dlg.requestsWindow.RequestsWindow;
import sample.dlg.settings.Settings;
import sample.objects.SeaOfThieves;
import sample.objects.macro.BaseBad;
import sample.objects.macro.BaseGood;
import sample.objects.macro.Macro;
import sample.objects.macro.TreasuresCastle;
import sample.objects.micro.Newbie;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

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

    public void deleteNewbies() {
        System.out.println("Видалення всіх виділенних юнітів. . .");
        for (int i = 0; i < Main.getWorld().getUnits().size(); i++) {
            Newbie newbie = Main.getWorld().getUnits().get(i);
            if (newbie.isActive()) {
                Main.getWorld().deleteUnit(newbie);
                i--;
            }
        }
    }

    public void openCUTC() {
        System.out.println("Відкриття меню зміни юнітів. . .");
        new CUTC().display();
    }

    public void activateNewbies() {
        System.out.println("Виділення всіх юнітів на карті. . .");
        Main.getWorld().getUnits().stream().filter(n -> !n.isActive()).forEach(Newbie::flipActivation);
    }

    public void createNewUnit() {
        Main.getWorld().addNewUnit(new Newbie());
    }

    public void createNewUnit(String team, double x, double y) {
        Newbie.createNewUnit("", "", team, Double.toString(x), Double.toString(y), false);
    }

    public void openHW() {
        System.out.println("Відкриття довідки. . .");
        new HelpWindow().display();
    }

    public void moveToBase() {
        Main.setStayBase(!Main.isStayBase());
        if (!Main.isStayBase()) {
            System.out.println("Вигнання юнітів з баз. . .");
            for (Macro macro : Main.getWorld().getMacros())
                if (!macro.getType().equals("TreasuresCastle"))
                    for (int j = 0; j < macro.getUnitsIn().size(); j++) {
                        Newbie unitIn = macro.getUnitsIn().get(j);
                        unitIn.setInMacro("null");
                        macro.removeUnit(unitIn);
                        j--;
                    }
        } else System.out.println("Юніти йдуть до своєї бази. . .");
    }

    public void requests() {
        new RequestsWindow().display();
    }

    public void copyPaste() {
        System.out.println("Клонування юніта. . .");
        ArrayList<Newbie> temp = new ArrayList<>();
        for (Newbie unit : Main.getWorld().getUnits()) {
            if (unit.isActive()) {
                try {
                    Newbie clone = unit.clone();
                    temp.add(clone);
                }
                catch (CloneNotSupportedException e) {
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
        System.out.println("Деактивація юнітів. . .");
        Main.getWorld().getUnits().stream()
                .filter(Newbie::isActive)
                .forEach(Newbie::flipActivation);
    }

    public void settings() {
        System.out.println("Відкриття налаштувань. . .");
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
                    newbie.setOrder(false);
                    newbie.setBigTarget(null);
                });
    }

    public void createStartNewbie() {
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
            System.out.println("Виділення юніта. . .");
            lastNewbie.get().flipActivation();
            return;
        }

        for (Macro macro : Main.getWorld().getMacros()) {
            if (macro.mouseIsOn(mouseEvent.getX(), mouseEvent.getY())) {
                boolean flg = false;
                for (Newbie unit : Main.getWorld().getUnits()) {
                    if (unit.isActive()
                            && macro.getTeam().equals(unit.getUnitTeam())) {
                        unit.setOrder(true);
                        unit.setBigTarget(macro);
                        flg = true;
                    }
                }
                if (flg) System.out.println("Рух виділених юнітів до макрооб'єкту. . .");
                return;
            }
        }

        System.out.println("Створення юніта. . .");
        new NewChangeUnit(mouseEvent.getX(), mouseEvent.getY(), -1).display();
    }

    public void mouseRightClick(MouseEvent mouseEvent) {
        if (!Main.isStayBase()) {
            Main.getWorld().getMacros().stream()
                    .filter(macro -> macro.mouseIsOn(mouseEvent.getX(), mouseEvent.getY()) && !macro.getUnitsIn().isEmpty())
                    .findFirst()
                    .ifPresent(macro -> new MacroWindow(macro).display());
        }
    }

    public void mouseMiddleClick(MouseEvent mouseEvent) {
        Main.getWorld().getMacros().stream()
                .filter(macro -> macro.mouseIsOn(mouseEvent.getX(), mouseEvent.getY()))
                .findFirst()
                .ifPresent(macro -> {
                    for (int i = 0; i < macro.getUnitsIn().size(); i++) {
                        Newbie unit = macro.getUnitsIn().get(i);
                        unit.setInMacro("null");
                        macro.removeUnit(unit);
                        i--;
                    }
                });
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
}
