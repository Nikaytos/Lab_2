package sample.objects.Macro;

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
import sample.objects.Micro.Newbie;
import sample.objects.SeaOfThieves;

import static sample.Main.getRandom;
import static sample.objects.SeaOfThieves.MAX_X;
import static sample.objects.SeaOfThieves.MAX_Y;

public class BaseBad extends Macro {

    public BaseBad() {
        FONT_SIZE = 16 * SeaOfThieves.SIZE;
        BORDER_WH = new Rectangle2D.Double(-10, -5, 10, 10);
        IMAGE_WH = new Rectangle2D.Double(0, 0, 144 * SeaOfThieves.SIZE, 81 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(BORDER_WH.x, BORDER_WH.y, IMAGE_WH.getWidth() + BORDER_WH.width, IMAGE_WH.getHeight() + FONT_SIZE * 1.3 + 5 + BORDER_WH.height);
        MAX_MACRO = new Point((int) (MAX_X - 5 - MACRO_WH.width), (int) (MAX_Y - 5 - MACRO_WH.height));
        MIN_MACRO = new Point((int) (MAX_X - 500 - MACRO_WH.x), (int) (5 - MACRO_WH.y));

        unitsIn = new ArrayList<>();
        macroContainer = new Group();
        type = "BaseBad";
        team = "BAD";

        border = new Rectangle();
        border.setFill(Color.rgb(255, 0, 0, 0.5));

        macroName = new Label("Base Bad");

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/BaseBad.png")).toString()));

        setCoordinates();
        initialize();


        macroContainer.getChildren().addAll(border, macroName, macroImage);
    }


    @Override
    public void setCoordinates() {
        x = (int) (MAX_MACRO.getX() - 200);
        y = (int) (getRandom().nextInt((int) (MAX_MACRO.getY() - MIN_MACRO.getY() + 1)) + MIN_MACRO.getY());
        macroName.setLayoutX(x + IMAGE_WH.getWidth() / 2 - 55);
        macroName.setLayoutY(y);
        macroImage.setLayoutX(x);
        macroImage.setLayoutY(macroName.getLayoutY() + FONT_SIZE * 1.3 + 5);
        border.setLayoutX(x + BORDER_WH.x);
        border.setLayoutY(y + BORDER_WH.y);
        border.setWidth(MACRO_WH.width + BORDER_WH.width);
        border.setHeight(MACRO_WH.height + BORDER_WH.height + BORDER_WH.y);
    }
    @Override
    public void addUnit(Newbie newbie) {
        if (newbie.getUnitTeam().equals("BAD")) {
            unitsIn.add(newbie);
            Main.getWorld().deleteUnit(newbie);
        }
        else {
            System.out.println("вон атсудава");
        }
    }

    @Override
    public void removeUnit(Newbie newbie) {
        Main.getWorld().addNewUnit(newbie);
        unitsIn.remove(newbie);
    }
}