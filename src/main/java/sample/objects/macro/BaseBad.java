package sample.objects.macro;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.objects.micro.Newbie;
import sample.objects.SeaOfThieves;

import static sample.objects.SeaOfThieves.MAX_X;
import static sample.objects.SeaOfThieves.MAX_Y;

public class BaseBad extends Macro {

    private final int hpToHeal = 1;
    private final int time_delay = 40;
    private int time_current = 0;

    public BaseBad() {
        FONT_SIZE = 16 * SeaOfThieves.SIZE;
        BORDER_WH = new Rectangle2D.Double(-10, -5, 10, 10);
        IMAGE_WH = new Rectangle2D.Double(0, 0, 144 * SeaOfThieves.SIZE, 81 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(BORDER_WH.x, BORDER_WH.y, IMAGE_WH.getWidth() + BORDER_WH.width, IMAGE_WH.getHeight() + FONT_SIZE * 1.3 + BORDER_WH.height + FONT_SIZE * 1.3 + 5);
        MAX_MACRO = new Point((int) (MAX_X - 5 - MACRO_WH.width), (int) (MAX_Y - 5 - MACRO_WH.height));
        MIN_MACRO = new Point((int) (MAX_X - 500 - MACRO_WH.x), (int) (5 - MACRO_WH.y));

        unitIn = new ArrayList<>();
        macroContainer = new Group();
        type = "BaseBad";
        team = "BAD";

        border = new Rectangle();
        border.setFill(Color.rgb(255, 0, 0, 0.5));

        macroName = new Label("Base Bad");
        unitsInLabel = new Label("Units in: 0");

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/BaseBad.png")).toString()));

        setCoordinates();
        initialize();

        macroContainer.getChildren().addAll(border, macroName, unitsInLabel, macroImage);
    }


    @Override
    public void setCoordinates() {
        x = (int) (MAX_MACRO.getX() - 200);
        y = (int) (MAX_Y / 2 - MACRO_WH.height / 2);
        macroName.setLayoutX(x + IMAGE_WH.getWidth() / 2 - 55);
        macroName.setLayoutY(y);
        unitsInLabel.setLayoutX(x);
        unitsInLabel.setLayoutY(macroName.getLayoutY() + FONT_SIZE * 1.3);
        macroImage.setLayoutX(x);
        macroImage.setLayoutY(unitsInLabel.getLayoutY() + FONT_SIZE * 1.3 + 5);
        border.setLayoutX(x + BORDER_WH.x);
        border.setLayoutY(y + BORDER_WH.y);
        border.setWidth(MACRO_WH.width + BORDER_WH.width);
        border.setHeight(MACRO_WH.height + BORDER_WH.height + BORDER_WH.y);
    }

    @Override
    public void addUnitIn(Newbie newbie) {
        unitIn.add(newbie);

        getUnitsInLabel().setText("Units in: " + unitIn.size());
    }

    @Override
    public void removeUnitIn(Newbie newbie) {
        unitIn.remove(newbie);

        getUnitsInLabel().setText("Units in: " + unitIn.size());
    }

    @Override
    public void lifeCycle() {
        time_current++;
        if (time_current < time_delay) return;
        time_current = 0;

        for (int i = 0; i < unitIn.size(); i++) {
            Newbie unit = unitIn.get(i);
            if (unit.getUnitTeam().equals("BAD")) {
                if (unit.getUnitHealth() < 100) {
                    unit.setUnitHealth(unit.getUnitHealth() + hpToHeal);
                } else {
                    unit.setUnitHealth((double) Newbie.MAX_HEALTH);
                }
            } else if (unit.getUnitTeam().equals("GOOD")) {
                if (unit.getUnitHealth() > 0) {
                    unit.setUnitHealth(unit.getUnitHealth() - hpToHeal);
                } else {
                    unitIn.remove(unit);
                    Main.getWorld().deleteUnit(unit);
                    getUnitsInLabel().setText("Units in: " + unitIn.size());
                    i--;
                }
            }
        }
    }
}