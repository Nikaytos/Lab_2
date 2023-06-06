package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import sample.objects.SeaOfThieves;
import sample.objects.macro.Macro;
import sample.objects.micro.Newbie;

import java.util.HashMap;

public class MiniMap {
    final static private double SCALE = 0.1;
    private Pane pane;
    private HashMap<Newbie, ImageView> unitsMap;
    private HashMap<Macro, ImageView> macrosMap;
    private Rectangle mainArea;
    private Label label;

    public MiniMap() {
        this.pane = new Pane();
        this.pane.setMinWidth(SeaOfThieves.MAX_X * MiniMap.SCALE);
        this.pane.setMinHeight(SeaOfThieves.MAX_Y * MiniMap.SCALE);
        unitsMap = new HashMap<>();
        macrosMap = new HashMap<>();

        Rectangle rectangle = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        label = new Label("Map");
        label.setFont(new Font("Segoe UI Black", 16));
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(rectangle.getWidth());
        label.setLayoutX(pane.getLayoutX());

        mainArea = new Rectangle(0, 0, SeaOfThieves.MAX_X * MiniMap.SCALE, SeaOfThieves.MAX_Y * MiniMap.SCALE);
        mainArea.setFill(Color.TRANSPARENT);
        mainArea.setStrokeWidth(2);
        mainArea.setStroke(Color.YELLOW);
        this.pane.getChildren().addAll(rectangle, label, mainArea);

        this.pane.setOnMousePressed(event -> moveTo(event.getX(), event.getY()));
    }

    public void moveTo(double x, double y) {
        if (x < mainArea.getWidth() / 2) {
            Main.getScrollPane().setHvalue(0);
        } else if (x > pane.getWidth() - mainArea.getWidth() / 2) {
            Main.getScrollPane().setHvalue(1);
        } else Main.getScrollPane().setHvalue(x / pane.getWidth());

        if (y <= mainArea.getHeight() / 2) {
            Main.getScrollPane().setVvalue(0);
        } else if (y >= pane.getHeight() - mainArea.getHeight() / 2) {
            Main.getScrollPane().setVvalue(1);
        } else Main.getScrollPane().setVvalue(y / pane.getHeight());
    }

    public Pane getPane() {
        return pane;
    }

    public Rectangle getMainArea() {
        return mainArea;
    }

    public static double getSCALE() {
        return SCALE;
    }

    public void addUnit(Newbie unit) {
        ImageView imageView = new ImageView(unit.getUnitImage().getImage());
        imageView.setLayoutX(unit.getX() * SCALE);
        imageView.setLayoutY(unit.getY() * SCALE);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(190 * SCALE);
        DropShadow shadow = new DropShadow();
        shadow.setColor(unit.getShadow().getColor());
        shadow.setRadius(unit.getShadow().getRadius() * SCALE);
        shadow.setSpread(unit.getShadow().getSpread() * SCALE);
        imageView.setEffect(shadow);
        DropShadow shadowActive = new DropShadow();
        shadowActive.setColor(Color.GREENYELLOW);
        shadowActive.setRadius(unit.getShadowActive().getRadius() * SCALE);
        shadowActive.setSpread(unit.getShadowActive().getSpread() * SCALE);
        shadowActive.setInput(imageView.getEffect());
        if (unit.isActive()) imageView.setEffect(shadowActive);
        unitsMap.put(unit, imageView);
        pane.getChildren().add(imageView);
    }

    public void deleteUnit(Newbie unit) {
        pane.getChildren().remove(unitsMap.get(unit));
        unitsMap.remove(unit);
    }

    public void addMacro(Macro macro) {
        ImageView imageView = new ImageView(macro.getMacroImage().getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(macro.getMacroImage().getFitHeight() * MiniMap.SCALE);
        imageView.setLayoutX(macro.getX() * MiniMap.SCALE);
        imageView.setLayoutY(macro.getY() * MiniMap.SCALE);

        macrosMap.put(macro, imageView);
        pane.getChildren().addAll(imageView);
    }

    public void deleteMacro(Macro macro) {
        pane.getChildren().removeAll(macrosMap.get(macro));
        macrosMap.remove(macro);
    }

    public void updateMap() {
        mainArea.setWidth(Main.getScene().getWidth() * MiniMap.SCALE);
        mainArea.setHeight(Main.getScene().getHeight() * MiniMap.SCALE);

        for (Newbie unit : Main.getWorld().getUnits()) {
            ImageView imageView = unitsMap.get(unit);
            imageView.setLayoutX(unit.getX() * MiniMap.SCALE);
            imageView.setLayoutY(unit.getY() * MiniMap.SCALE);
            DropShadow shadow = new DropShadow();
            shadow.setColor(unit.getShadow().getColor());
            shadow.setRadius(unit.getShadow().getRadius() * SCALE);
            shadow.setSpread(unit.getShadow().getSpread() * SCALE);
            imageView.setEffect(shadow);
            DropShadow shadowActive = new DropShadow();
            shadowActive.setColor(Color.GREENYELLOW);
            shadowActive.setRadius(unit.getShadowActive().getRadius() * SCALE);
            shadowActive.setSpread(unit.getShadowActive().getSpread() * SCALE);
            shadowActive.setInput(imageView.getEffect());
            if (unit.isActive()) imageView.setEffect(shadowActive);
        }
    }
}


