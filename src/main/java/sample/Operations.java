package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.dlg.ChooseUnitToChangeDlg;
import sample.dlg.HelpWindow;
import sample.dlg.NewChangeUnitDlg;

import java.util.Objects;

public class Operations {
//---------------------------------------------------------
    public static int MAX_X;
    public static int MIN_X = 5;
    public static int MAX_Y;
    public static int MIN_Y = 5;
//---------------------------------------------------------
    static Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    static int width = (int) visualBounds.getMaxX();
    static double taskbarSize = (Screen.getPrimary().getBounds().getMaxY() - visualBounds.getMaxY())*1.5;
    static int height = (int) (Screen.getPrimary().getBounds().getMaxY() - taskbarSize);
//---------------------------------------------------------
    static double mouseX = -1;
    static double mouseY = -1;
//---------------------------------------------------------
    static ImageView bg;
//---------------------------------------------------------
    static Newbie lastActive;
//---------------------------------------------------------
    public static void setMaxXY(int maxX, int maxY) {
            MAX_X = maxX - 5;
            MAX_Y = maxY - 5;
    }
//---------------------------------------------------------
    public static void createStage(Stage stage) {
        stage.setTitle("Sea of Thieves");
        Image icon = new Image(Objects.requireNonNull(SeaOfThieves.class.getResource("iconSOT.png")).toString());
        stage.getIcons().add(icon);
        stage.setWidth(Operations.MAX_X);
        stage.setHeight(Operations.MAX_Y);
        stage.setMaximized(true);
    }
//---------------------------------------------------------
    public static void createBackgroundImage() {
        Image mapImage = new Image(Objects.requireNonNull(SeaOfThieves.class.getResource("bg.jpeg")).toString());
        bg = new ImageView(mapImage);
        bg.setFitWidth(MAX_X);
        bg.setFitHeight(MAX_Y);
        bg.setPreserveRatio(false);
        bg.setSmooth(true);
        SeaOfThieves.group.getChildren().add(bg);
    }
//---------------------------------------------------------
    public static void deleteNewbies() {
        int activeCount = Math.toIntExact(NewbieManager.newbies.stream()
                .filter(Newbie::isActive)
                .count());
        if (activeCount == NewbieManager.newbies.size()) {
            NewbieManager.newbies.clear();
            SeaOfThieves.group.getChildren().clear();
            SeaOfThieves.group.getChildren().add(bg);
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
        NewbieManager.newbies.stream().filter(n -> !n.isActive()).forEach(n -> { n.flipActivation(); lastActive = n; });
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
        Newbie activeNewbie = lastActive;
        if (activeNewbie == null || NewbieManager.newbies.isEmpty()) {
            return;
        }
        double dx = 0.0;
        double dy = 0.0;
        int direction = 0;
        switch (keyCode) {
            case UP, W -> dy = -Newbie.SPEED;
            case DOWN, S -> dy = Newbie.SPEED;
            case LEFT, A -> {
                dx = -Newbie.SPEED;
                direction = 1;
            }
            case RIGHT, D -> {
                dx = Newbie.SPEED;
                direction = -1;
            }
            default -> {
                return;
            }
        }
        if (!activeNewbie.isActive()) {
            return;
        }
        NewbieManager.newbies.stream().filter(Newbie::isActive).forEach(Newbie::flipActivation);
        activeNewbie.flipActivation();
        activeNewbie.move(dx, dy, direction);
    }
//---------------------------------------------------------
    public static void createStartNewbie() {
        for (int i = 0; i <= Math.random() * 10; i++) {
            createNewUnit();
        }
    }
//---------------------------------------------------------
    public static void mouseLeftClick(MouseEvent mouseEvent, Stage stage) {
        boolean newbieActivated = NewbieManager.newbies.stream()
                .filter(n -> n.mouseActivate(mouseEvent.getX(), mouseEvent.getY()))
                .peek(n -> { if (n.isActive()) Operations.lastActive = n; })
                .findFirst().isPresent();
        if (!newbieActivated) {
            NewChangeUnitDlg.display(mouseEvent.getX(), mouseEvent.getY(), -1, stage);
            System.out.println("Got control back!");
        }
    }
//---------------------------------------------------------
    public static void mouseRightClick() {
        NewbieManager.newbies.stream()
                .filter(Newbie::isActive)
                .forEach(Newbie::flipActivation);
    }
//---------------------------------------------------------
    public static void mouseMove(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }
}
