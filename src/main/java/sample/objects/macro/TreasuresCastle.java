package sample.objects.macro;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.objects.SeaOfThieves;
import sample.objects.micro.Newbie;

import static sample.objects.SeaOfThieves.MAX_X;
import static sample.objects.SeaOfThieves.MAX_Y;

public class TreasuresCastle extends Macro {

    private final int time_delay = 40;
    private int time_current = 0;

    public TreasuresCastle() {
        FONT_SIZE = 20 * SeaOfThieves.SIZE;
        IMAGE_WH = new Rectangle2D.Double(0, 0, 256 * SeaOfThieves.SIZE, 166 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(0, 0, IMAGE_WH.getWidth(), IMAGE_WH.getHeight() + FONT_SIZE * 1.3 + FONT_SIZE * 1.3 + 5);

        unitIn = new ArrayList<>();
        macroContainer = new Group();
        type = "TreasuresCastle";

        border = new Rectangle();
        border.setFill(Color.rgb(255, 255, 255, 0.5));

        macroName = new Label("Treasures Castle");
        unitsInLabel = new Label("Units in: 0");

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/TreasureCastle.png")).toString()));

        setCoordinates();
        initialize();

        unitsInLabel.setTextFill(Color.BLACK);

        macroContainer.getChildren().addAll(border, macroName, unitsInLabel, macroImage);
    }

    @Override
    public void setCoordinates() {
        x = (int) ( MAX_X / 2 - MACRO_WH.width / 2);
        y = (int) ( MAX_Y / 2 - MACRO_WH.height / 2);
        macroName.setLayoutX(x + IMAGE_WH.getWidth() / 2 - 115);
        macroName.setLayoutY(y);
        unitsInLabel.setLayoutX(x);
        unitsInLabel.setLayoutY(macroName.getLayoutY() + FONT_SIZE * 1.3);
        macroImage.setLayoutX(x);
        macroImage.setLayoutY(unitsInLabel.getLayoutY() + FONT_SIZE * 1.3 + 5);
        border.setLayoutX(x - 10);
        border.setLayoutY(y - 10);
        border.setWidth(MACRO_WH.width + 20);
        border.setHeight(MACRO_WH.height + 20);
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

        if (unitIn.size() > 0) {
            Color color = Color.rgb(255, 255, 255, 0.5);
            String sColor = "white";
            for (Newbie unit : unitIn) {
                int countCoins = Integer.parseInt(unit.getCoinsCount().getText()) + 1;
                unit.setCoinsCount(String.valueOf(countCoins));
            }

            for (Newbie unit : unitIn) {
                if (unit.getUnitTeam().equals("GOOD") && !sColor.equals("red")) {
                    color = Color.rgb(0, 100, 255, 0.5);
                    sColor = "blue";
                } else if (unit.getUnitTeam().equals("BAD") && !sColor.equals("blue")) {
                    color = Color.rgb(255, 0, 0, 0.5);
                    sColor = "red";
                } else {
                    color = Color.rgb(255, 0, 255, 0.5);
                    break;
                }
            }
            border.setFill(color);
        }
        else border.setFill(Color.rgb(255, 255, 255, 0.5));
    }
}