package sample;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import sample.dlg.ChooseUnitToChangeDlg;
import sample.dlg.HelpWindow;
import sample.dlg.NewChangeUnitDlg.NewChangeUnitDlg;
import sample.objects.Macro.BaseBad;
import sample.objects.Macro.BaseGood;
import sample.objects.Macro.Macro;
import sample.objects.Macro.TreasuresCastle;
import sample.objects.Micro.Newbie;
import sample.objects.SeaOfThieves;
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
        stage.setTitle("Sea of Thieves");
        Image icon = new Image(Objects.requireNonNull(Main.class.getResource("images/iconSOT.png")).toString());
        stage.getIcons().add(icon);
        stage.setWidth(SeaOfThieves.MAX_X);
        stage.setHeight(SeaOfThieves.MAX_Y);
        stage.setMaximized(true);
    }

    public void deleteNewbies() {
        for (int i = 0; i < Main.getWorld().getUnits().size(); i++) {
            Newbie newbie = Main.getWorld().getUnits().get(i);
            if (newbie.isActive()) {
                Main.getWorld().deleteUnit(newbie);
                i--;
            }
        }
    }

    public void openCUTCD(Stage stage) {
        ChooseUnitToChangeDlg.display(stage);
        System.out.println("Got control back!");
    }

    public void activateNewbies() {
        Main.getWorld().getUnits().stream().filter(n -> !n.isActive()).forEach(Newbie::flipActivation);
    }

    public void createNewUnit() {
        Main.getWorld().addNewUnit(new Newbie());
    }

    public void createNewUnit(String team, double x, double y) {
        Newbie.createNewUnit("", "", team, Double.toString(x), Double.toString(y), false);
    }

    public void openHelpWindow(Stage stage) {
        HelpWindow.display(stage);
        System.out.println("Got control back!");
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
                .forEach(newbie -> newbie.move(finalDx, finalDy, finalDirection));
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
            lastNewbie.get().flipActivation();
            return;
        }

        for (Macro macro : Main.getWorld().getMacros()) {
            if (macro.mouseIsOn(mouseEvent.getX(), mouseEvent.getY())) {
                for (Newbie unit : Main.getWorld().getUnits()) {
                    if (unit.isActive()
                            && macro.getTeam().equals(unit.getUnitTeam())) {
                        unit.setOrder(true);
                        unit.setBigTarget(macro);
                    }
                }
                return;
            }
        }

        new NewChangeUnitDlg(mouseEvent.getX(), mouseEvent.getY(), -1).display();
        System.out.println("Got control back!");
    }

    public void mouseRightClick(MouseEvent mouseEvent) {
        Main.getWorld().getMacros().stream()
                .filter(macro -> macro.mouseIsOn(mouseEvent.getX(), mouseEvent.getY()))
                .findFirst()
                .ifPresent(macro -> {
                    for (int i = 0; i < macro.getUnitsIn().size(); i++) {
                        Newbie unit = macro.getUnitsIn().get(i);
                        macro.removeUnit(unit);
                        i--;
                    }
                });
    }

    public void deleteActivationUnits() {
        Main.getWorld().getUnits().stream()
                .filter(Newbie::isActive)
                .forEach(Newbie::flipActivation);
    }

    public void handleEvent(Event event) {
        if (event instanceof MouseEvent) {
            mouseX = ((MouseEvent) event).getX();
            mouseY = ((MouseEvent) event).getY();
        } else if (event instanceof ScrollEvent) {
            mouseX = ((ScrollEvent) event).getX();
            mouseY = ((ScrollEvent) event).getY();
        }
    }

    public void copyPast() {
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
}
