package sample.objects.Macro;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.awt.geom.Rectangle2D;
import java.util.Objects;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import sample.Main;
import sample.objects.SeaOfThieves;

import static sample.Main.random;
import static sample.objects.SeaOfThieves.MAX_X;
import static sample.objects.SeaOfThieves.MAX_Y;

public class BaseGood extends Macro{
    private static final double FONT_SIZE = 16 * SeaOfThieves.SIZE;
    private static final double IMAGE_W = 144 * SeaOfThieves.SIZE;
    private static final double IMAGE_H = 81 * SeaOfThieves.SIZE;
    private static final Rectangle2D.Double BASE_GOOD_WH = new Rectangle2D.Double(0, 0, IMAGE_W, IMAGE_H + FONT_SIZE*1.3);
    private static final int MAX_X_BASE_GOOD = (int) (MAX_X - BASE_GOOD_WH.width - 5);
    private static final int MIN_X_BASE_GOOD = MAX_X - 500;
    private static final int MAX_Y_BASE_GOOD = (int) (MAX_Y - BASE_GOOD_WH.height - 5);
    private static final int MIN_Y_BASE_GOOD = 0;

    public BaseGood() {
        x = random.nextInt(MAX_X_BASE_GOOD - MIN_X_BASE_GOOD) + MAX_X_BASE_GOOD;
        y = random.nextInt(MAX_Y_BASE_GOOD - MIN_Y_BASE_GOOD) + MAX_Y_BASE_GOOD;
        macroType = "BaseGood";
        macroImage = new ImageView(new Image(Objects.requireNonNull(Main.class.getResource("BaseGood.jpg")).toString()));
        macroImage.setPreserveRatio(true);
        macroImage.setSmooth(true);
        macroImage.setCache(true);
        macroImage.setCacheHint(CacheHint.QUALITY);
        macroContainer.getChildren().add(macroImage);

        macroName = new Label("Аванпост хороших піратів");
        macroName.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE));
    }

    @Override
    public void setCoordinates() {

    }
}
