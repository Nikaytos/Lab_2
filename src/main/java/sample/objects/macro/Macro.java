package sample.objects.macro;

import javafx.geometry.Point2D;
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
import sample.objects.micro.Newbie;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Macro {
    protected int x;
    protected int y;

    protected double FONT_SIZE;
    protected Rectangle2D.Double IMAGE_WH;
    protected Point MIN_MACRO;
    protected Point MAX_MACRO;
    protected Rectangle2D.Double MACRO_WH;
    protected Rectangle2D.Double BORDER_WH;

    protected Rectangle border;
    protected ImageView macroImage;
    protected Label macroName;

    protected ArrayList<Newbie> unitsIn;
    protected Group macroContainer;

    protected String type = "Nothing";
    protected String team = "Nothing";

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

        Rectangle clipRect = new Rectangle(macroImage.getFitWidth(), macroImage.getFitHeight());
        clipRect.setArcWidth(20);
        clipRect.setArcHeight(20);
        macroImage.setClip(clipRect);
    }

    public String getName() {
        return macroName.getText();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Group getMacroContainer() {
        return macroContainer;
    }

    public String getType() {
        return type;
    }

    public String getTeam() {
        return team;
    }

    public ArrayList<Newbie> getUnitsIn() {
        return unitsIn;
    }
    public ArrayList<String> getNames() {
        ArrayList<String> arr = new ArrayList<>();
        unitsIn.forEach(n -> arr.add(n.toString()));
        return arr;
    }

    public boolean mouseIsOn(double mx, double my) {
        return macroContainer.getBoundsInParent().contains(new Point2D(mx, my));
    }
    public abstract void setCoordinates();
    public void addUnit(Newbie newbie) {}
    public void removeUnit(Newbie newbie) {}

    @Override
    public String toString() {
        return "Macro{" +
                "macroName=" + getName() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
