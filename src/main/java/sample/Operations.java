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
import sample.objects.Newbie;
import sample.objects.NewbieManager;
import sample.objects.SeaOfThieves;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class Operations {
    //---------------------------------------------------------
    static double mouseX = -1;
    static double mouseY = -1;
    //---------------------------------------------------------
    public static void createStage(Stage stage) {
        stage.setTitle("Sea of Thieves");
        Image icon = new Image(Objects.requireNonNull(Main.class.getResource("images/iconSOT.png")).toString());
        stage.getIcons().add(icon);
        stage.setWidth(SeaOfThieves.MAX_X);
        stage.setHeight(SeaOfThieves.MAX_Y);
        stage.setMaximized(true);
    }
    //---------------------------------------------------------
    public static void deleteNewbies() {
        int activeCount = Math.toIntExact(NewbieManager.newbies.stream()
                .filter(Newbie::isActive)
                .count());
        if (activeCount == NewbieManager.newbies.size()) {
            NewbieManager.newbies.clear();
            SeaOfThieves.getRoot().getChildren().clear();
            SeaOfThieves.getRoot().getChildren().add(SeaOfThieves.getBg());
            System.out.println("Живих не залишилося. . .");
        } else {
            NewbieManager.newbies.stream()
                    .filter(Newbie::isActive)
                    .forEach(Newbie::delete);
            NewbieManager.newbies.removeIf(Newbie::isActive);
        }
    }
    //---------------------------------------------------------
    public static void openCUTCD(Stage stage) {
        ChooseUnitToChangeDlg.display(stage);
        System.out.println("Got control back!");
    }
    //---------------------------------------------------------
    public static void activateNewbies() {
        NewbieManager.newbies.stream().filter(n -> !n.isActive()).forEach(Newbie::flipActivation);
    }
    //---------------------------------------------------------
    public static void createNewUnit() {
        NewbieManager.newbies.add(new Newbie());
    }
    //---------------------------------------------------------
    public static void createNewUnit(String team, double x, double y) {
        Newbie.createNewUnit("", "", team, Double.toString(x), Double.toString(y));
    }
    //---------------------------------------------------------
    public static void openHelpWindow(Stage stage) {
        HelpWindow.display(stage);
        System.out.println("Got control back!");
    }
    //---------------------------------------------------------
    public static void handleArrowKeys(KeyCode keyCode) {
        double dx = 0.0;
        double dy = 0.0;
        int direction = 0;
        switch (keyCode) {
            case W -> dy = -Newbie.SPEED;
            case S -> dy = Newbie.SPEED;
            case A -> {
                dx = -Newbie.SPEED;
                direction = 1;
            }
            case D -> {
                dx = Newbie.SPEED;
                direction = -1;
            }
            default -> {
                return;
            }
        }
        double finalDx = dx;
        double finalDy = dy;
        int finalDirection = direction;
        NewbieManager.newbies.stream()
                .filter(Newbie::isActive)
                .forEach(newbie -> newbie.move(finalDx, finalDy, finalDirection));
    }
    //---------------------------------------------------------
    public static void createStartNewbie() {
        for (int i = 0; i <= Math.random() * 10; i++) {
            createNewUnit();
        }
    }
    //---------------------------------------------------------
    public static void mouseLeftClick(MouseEvent mouseEvent, Stage stage) throws IOException {
        Optional<Newbie> lastNewbie = NewbieManager.newbies.stream()
                .filter(n -> n.mouseIsActive(mouseEvent.getX(), mouseEvent.getY()))
                .reduce((n1, n2) -> n2);
        if (lastNewbie.isPresent()) {
            lastNewbie.get().flipActivation();
        } else {
            new NewChangeUnitDlg(mouseEvent.getX(), mouseEvent.getY(), -1).display();
            System.out.println("Got control back!");
        }

    }
    //---------------------------------------------------------
    public static void deleteActivationUnits() {
        NewbieManager.newbies.stream()
                .filter(Newbie::isActive)
                .forEach(Newbie::flipActivation);
    }
    //---------------------------------------------------------
    public static void handleEvent(Event event) {
        if (event instanceof MouseEvent) {
            mouseX = ((MouseEvent) event).getX();
            mouseY = ((MouseEvent) event).getY();
        } else if (event instanceof ScrollEvent) {
            mouseX = ((ScrollEvent) event).getX();
            mouseY = ((ScrollEvent) event).getY();
        }
    }
}
