package sample;

import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import sample.dlg.ChooseUnitToChangeDlg;
import sample.dlg.HelpWindow;
import sample.dlg.NewChangeUnitDlg;
import sample.objects.Macro.BaseBad;
import sample.objects.Macro.BaseGood;
import sample.objects.Macro.Macro;
import sample.objects.Macro.TreasuresCastle;
import sample.objects.Micro.Newbie;
import sample.objects.SeaOfThieves;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class Operations {
    private static double mouseX = -1;
    private static double mouseY = -1;

    public static void createStage(Stage stage) {
        stage.setTitle("Sea of Thieves");
        Image icon = new Image(Objects.requireNonNull(Main.class.getResource("images/iconSOT.png")).toString());
        stage.getIcons().add(icon);
        stage.setWidth(SeaOfThieves.MAX_X);
        stage.setHeight(SeaOfThieves.MAX_Y);
        stage.setMaximized(true);
    }

    public static void deleteNewbies() {
        for (int i = 0; i < Main.getWorld().getUnits().size(); i++) {
            Newbie newbie = Main.getWorld().getUnits().get(i);
            if (newbie.isActive()) {
                Main.getWorld().deleteUnit(newbie);
                i--;
            }
        }
    }

    public static void openCUTCD(Stage stage) {
        ChooseUnitToChangeDlg.display(stage);
        System.out.println("Got control back!");
    }

    public static void activateNewbies() {
        Main.getWorld().getUnits().stream().filter(n -> !n.isActive()).forEach(Newbie::flipActivation);
    }

    public static void createNewUnit() {
        Main.getWorld().addNewUnit(new Newbie());
    }

    public static void createNewUnit(String team, double x, double y) {
        Newbie.createNewUnit("", "", team, Double.toString(x), Double.toString(y));
    }

    public static void openHelpWindow(Stage stage) {
        HelpWindow.display(stage);
        System.out.println("Got control back!");
    }

    public static void handleArrowKeys(KeyCode keyCode) {
        double dx = 0.0;
        double dy = 0.0;
        int direction = 0;
        switch (keyCode) {
            case W -> dy = -10;
            case S -> dy = 10;
            case A -> {
                dx = -10;
                direction = 1;
            }
            case D -> {
                dx = 10;
                direction = -1;
            }
            default -> {
                return;
            }
        }
        double finalDx = dx;
        double finalDy = dy;
        int finalDirection = direction;
        Main.getWorld().getUnits().stream()
                .filter(Newbie::isActive)
                .forEach(newbie -> newbie.move(finalDx, finalDy, finalDirection));
    }

    public static void createStartNewbie() {
        for (int i = 0; i <= Math.random() * 10; i++) {
            createNewUnit();
        }
    }

    public static void createStartMacro() {
        Main.getWorld().addNewMacro(new BaseGood());
        Main.getWorld().addNewMacro(new BaseBad());
        Main.getWorld().addNewMacro(new TreasuresCastle());
    }

    public static void mouseLeftClick(MouseEvent mouseEvent) throws IOException {
        Optional<Newbie> lastNewbie = Main.getWorld().getUnits().stream()
                .filter(n -> n.mouseIsActive(mouseEvent.getX(), mouseEvent.getY()))
                .reduce((n1, n2) -> n2);
        if (lastNewbie.isPresent()) {
            lastNewbie.get().flipActivation();
        }
        else {
            new NewChangeUnitDlg(mouseEvent.getX(), mouseEvent.getY(), -1).display();
            System.out.println("Got control back!");
        }
    }

    public static void deleteActivationUnits() {
        Main.getWorld().getUnits().stream()
                .filter(Newbie::isActive)
                .forEach(Newbie::flipActivation);
    }

    public static void handleEvent(Event event) {
        if (event instanceof MouseEvent) {
            mouseX = ((MouseEvent) event).getX();
            mouseY = ((MouseEvent) event).getY();
        } else if (event instanceof ScrollEvent) {
            mouseX = ((ScrollEvent) event).getX();
            mouseY = ((ScrollEvent) event).getY();
        }
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static void interact() {
        for (Newbie newbie : Main.getWorld().getUnits()) {
            if (newbie.isActive()) {
                for (Macro macro : Main.getWorld().getMacros()) {
                    macro.interact(newbie);
                }
            }
        }
    }
}
