package sample.objects.Macro;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.objects.Micro.Newbie;
import sample.objects.SeaOfThieves;

import static sample.Main.getRandom;
import static sample.objects.SeaOfThieves.MAX_Y;

public class BaseGood extends Macro {

    public BaseGood() {
        FONT_SIZE = 16 * SeaOfThieves.SIZE;
        BORDER_WH = new Rectangle2D.Double(-10, -5, 10, 10);
        IMAGE_WH = new Rectangle2D.Double(0, 0, 144 * SeaOfThieves.SIZE, 81 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(BORDER_WH.x, BORDER_WH.y, IMAGE_WH.getWidth() + BORDER_WH.width, IMAGE_WH.getHeight() + FONT_SIZE * 1.3 + 5 + BORDER_WH.height);
        MAX_MACRO = new Point((int) (500 - MACRO_WH.width - 5), (int) (MAX_Y - MACRO_WH.height - 5));
        MIN_MACRO = new Point((int) (5 - MACRO_WH.x), (int) (5 - MACRO_WH.y));

        macroContainer = new Group();
        type = "BaseGood";

        border = new Rectangle();
        border.setFill(Color.rgb(0, 100, 255, 0.5));

        macroName = new Label("Base Good");

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/BaseGood.jpg")).toString()));

        setCoordinates();
        initialize();
        macroContainer.getChildren().addAll(border, macroName, macroImage);
    }

    @Override
    public void setCoordinates() {
        x = 200;
        y = getRandom().nextInt((int) (MAX_MACRO.getY() - MIN_MACRO.getY())) + MIN_MACRO.getY();
        macroName.setLayoutX(x + IMAGE_WH.getWidth() / 2 - 60);
        macroName.setLayoutY(y);
        macroImage.setLayoutX(x);
        macroImage.setLayoutY(macroName.getLayoutY() + FONT_SIZE * 1.3 + 5);
        border.setLayoutX(x + BORDER_WH.x);
        border.setLayoutY(y + BORDER_WH.y);
        border.setWidth(MACRO_WH.width + BORDER_WH.width);
        border.setHeight(MACRO_WH.height + BORDER_WH.height + BORDER_WH.y);
    }

    @Override
    public void interact(Newbie newbie) {
        if (newbie.getUnitImage().getLayoutBounds().intersects(this.border.getBoundsInParent())) {
            if (newbie.getUnitTeam().equals("GOOD")) {
                newbie.setUnitHealth(100.0);
            }
            else {
                System.out.println("вон атсудава");
            }
        }
    }

}
