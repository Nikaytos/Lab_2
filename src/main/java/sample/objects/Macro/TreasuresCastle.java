package sample.objects.Macro;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.awt.geom.Rectangle2D;
import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Main;
import sample.objects.Micro.Newbie;
import sample.objects.SeaOfThieves;

import static sample.objects.SeaOfThieves.MAX_X;
import static sample.objects.SeaOfThieves.MAX_Y;

public class TreasuresCastle extends Macro {

    public TreasuresCastle() {
        FONT_SIZE = 20 * SeaOfThieves.SIZE;
        IMAGE_WH = new Rectangle2D.Double(0, 0, 256 * SeaOfThieves.SIZE, 166 * SeaOfThieves.SIZE);
        MACRO_WH = new Rectangle2D.Double(0, 0, IMAGE_WH.getWidth(), IMAGE_WH.getHeight() + FONT_SIZE * 1.3);

        macroContainer = new Group();
        type = "TreasuresCastle";

        border = new Rectangle();
        border.setFill(Color.rgb(255, 255, 255, 0.5));

        macroName = new Label("Treasures Castle");

        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("images/TreasureCastle.png")).toString()));

        setCoordinates();
        initialize();
        macroContainer.getChildren().addAll(border, macroName, macroImage);
    }

    @Override
    public void setCoordinates() {
        x = (double) MAX_X / 2 - MACRO_WH.width / 2;
        y = (double) MAX_Y / 2 - MACRO_WH.height / 2;
        macroName.setLayoutX(x + IMAGE_WH.getWidth() / 2 - 115);
        macroName.setLayoutY(y);
        macroImage.setLayoutX(x);
        macroImage.setLayoutY(macroName.getLayoutY() + FONT_SIZE * 1.3);
        border.setLayoutX(x - 10);
        border.setLayoutY(y - 10);
        border.setWidth(MACRO_WH.width + 20);
        border.setHeight(MACRO_WH.height + 20);
    }

    @Override
    public void interact(Newbie newbie) {
        if (newbie.getUnitImage().getLayoutBounds().intersects(this.border.getBoundsInParent())) {
            System.out.println("pipyao");
        }
    }

}