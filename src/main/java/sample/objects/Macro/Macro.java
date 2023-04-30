package sample.objects.Macro;

import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.objects.Micro.Newbie;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

public abstract class Macro {
    protected double x;
    protected double y;

    protected double FONT_SIZE;
    protected Rectangle2D.Double IMAGE_WH;
    protected Point MIN_MACRO;
    protected Point MAX_MACRO;
    protected Rectangle2D.Double MACRO_WH;
    protected Rectangle2D.Double BORDER_WH;

    protected Rectangle border;
    protected ImageView macroImage;
    protected Label macroName;

    protected Group macroContainer;

    protected String type = "Nothing";

    public void initialize() {
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(3);
        border.setArcWidth(20);
        border.setArcHeight(20);

        macroName.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE));
        macroName.setTextFill(Color.WHITE);
        macroName.setEffect(new Bloom());

        DropShadow nameEffect = new DropShadow();
        nameEffect.setBlurType(BlurType.GAUSSIAN);
        nameEffect.setColor(Color.BLACK);
        nameEffect.setRadius(4);
        nameEffect.setSpread(0.6);
        nameEffect.setInput(macroName.getEffect());
        macroName.setEffect(nameEffect);

        macroImage.setFitWidth(IMAGE_WH.getWidth());
        macroImage.setFitHeight(IMAGE_WH.getHeight());
        macroImage.setPreserveRatio(true);
        macroImage.setSmooth(true);
        macroImage.setCache(true);
        macroImage.setCacheHint(CacheHint.QUALITY);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Group getMacroContainer() {
        return macroContainer;
    }

    public String getType() {
        return type;
    }

    public abstract void setCoordinates();
    public abstract void interact(Newbie newbie);
}
