package sample.objects.macro;

import javafx.geometry.Pos;
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

import static sample.objects.SeaOfThieves.MAX_Y;

public class BaseGood extends Macro {

    public BaseGood() {
        FONT_SIZE = 16 * SeaOfThieves.SIZE;
        BORDER_WH = new Rectangle2D.Double(-10, -5, 10, 10);
        IMAGE_WH = new Rectangle2D.Double(0, 0, 144 * SeaOfThieves.SIZE, 81 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(BORDER_WH.x, BORDER_WH.y, IMAGE_WH.getWidth() + BORDER_WH.width, IMAGE_WH.getHeight() + FONT_SIZE * 1.3 + 5 + BORDER_WH.height);
        MAX_MACRO = new Point((int) (500 - MACRO_WH.width - 5), (int) (MAX_Y - MACRO_WH.height - 5));
        MIN_MACRO = new Point((int) (5 - MACRO_WH.x), (int) (5 - MACRO_WH.y));

        unitsIn = new ArrayList<>();
        macroContainer = new Group();
        type = "BaseGood";
        team = "GOOD";

        border = new Rectangle();
        border.setFill(Color.rgb(0, 100, 255, 0.5));

        macroName = new Label("Base Good");
        macroName.setPrefWidth(IMAGE_WH.width);
        macroName.setAlignment(Pos.CENTER);

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/BaseGood.jpg")).toString()));

        setCoordinates();
        initialize();
        macroContainer.getChildren().addAll(border, macroName, macroImage);
    }

    @Override
    public void setCoordinates() {
        x = 200;
        y = (int) (MAX_Y/2 - MACRO_WH.height/2);
        macroName.setLayoutX(x);
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
        if (newbie.getUnitTeam().equals("GOOD")) {
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
