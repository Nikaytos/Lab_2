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
import sample.objects.micro.Actions;
import sample.objects.micro.Newbie;
import sample.objects.SeaOfThieves;

import static sample.objects.SeaOfThieves.MAX_Y;

public class BaseGood extends Macro {

    private final int time_delay = 40;
    private int time_current = 0;

    public BaseGood() {
        FONT_SIZE = 16 * SeaOfThieves.SIZE;
        BORDER_WH = new Rectangle2D.Double(-10, -5, 10, 10);
        IMAGE_WH = new Rectangle2D.Double(0, 0, 144 * SeaOfThieves.SIZE, 81 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(BORDER_WH.x, BORDER_WH.y, IMAGE_WH.getWidth() + BORDER_WH.width, IMAGE_WH.getHeight() + FONT_SIZE * 1.3 + BORDER_WH.height + FONT_SIZE * 1.3 + 5);
        MAX_MACRO = new Point((int) (500 - MACRO_WH.width - 5), (int) (MAX_Y - MACRO_WH.height - 5));
        MIN_MACRO = new Point((int) (5 - MACRO_WH.x), (int) (5 - MACRO_WH.y));

        unitIn = new ArrayList<>();
        macroContainer = new Group();
        type = "BaseGood";
        team = "GOOD";

        border = new Rectangle();
        border.setFill(Color.rgb(0, 100, 255, 0.5));

        macroName = new Label("Base Good");
        unitsInLabel = new Label("Units in: 0");

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/BaseGood.jpg")).toString()));

        setCoordinates();
        initialize();
        macroContainer.getChildren().addAll(border, macroName, unitsInLabel, macroImage);
    }

    @Override
    public void setCoordinates() {
        x = 200;
        y = (int) (MAX_Y / 4 - MACRO_WH.height / 2);
        macroName.setLayoutX(x);
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
            if (unit.getUnitTeam().equals("GOOD")) {
                unit.heal();

                if  (unit.getIntCoins() > 4) {
                    int countCoins = unit.getIntCoins() - 5;
                    unit.setCoinsCount(String.valueOf(countCoins));
                    coins+=5;
                } else if (unit.getUnitHealth() != Newbie.MAX_HEALTH) {
                    continue;
                } else if ((Main.getWorld().isGoBase())) {
                    continue;
                } else {
                    removeUnitIn(unit);
                    Main.getWorld().askWorldplanning(unit, Actions.TAKECOINS );
                    i--;
                }


            }
//            else if (unit.getUnitTeam().equals("BAD")) {
//                if (unit.getUnitHealth() > 0) {
//                    unit.setUnitHealth(unit.getUnitHealth() - hpToHeal);
//                } else {
//                    unitIn.remove(unit);
//                    Main.getWorld().deleteUnit(unit);
//                    getUnitsInLabel().setText("Units in: " + unitIn.size());
//                    i--;
//                }
//            }
        }
    }
}
