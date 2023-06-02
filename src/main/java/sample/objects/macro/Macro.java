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

    protected int coins;

    protected double FONT_SIZE;
    protected Rectangle2D.Double IMAGE_WH;
    protected Point MIN_MACRO;
    protected Point MAX_MACRO;
    protected Rectangle2D.Double MACRO_WH;
    protected Rectangle2D.Double BORDER_WH;

    protected Rectangle border;
    protected ImageView macroImage;
    protected Label macroName;
    protected Label unitsInLabel;

    protected ArrayList<Newbie> unitIn;
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

        unitsInLabel.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE-4));
        unitsInLabel.setTextFill(Color.WHITE);
        unitsInLabel.setEffect(new Bloom());

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

    public int getCoins() {
        return coins;
    }

    public int getCenterX() {
        return (int) getMacroContainer().getBoundsInParent().getCenterX();
    }

    public int getCenterY() {
        return (int) getMacroContainer().getBoundsInParent().getCenterY();
    }

    public Group getMacroContainer() {
        return macroContainer;
    }

    public Label getUnitsInLabel() {
        return unitsInLabel;
    }

    public ArrayList<Newbie> getUnitsIn() {
        return unitIn;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> arr = new ArrayList<>();
        unitIn.forEach(n -> arr.add(n.toString()));
        return arr;
    }

    public boolean mouseIsOn(double mx, double my) {
        return macroContainer.getBoundsInParent().contains(new Point2D(mx, my));
    }

    public abstract void setCoordinates();

    public void addUnitIn(Newbie newbie) {}

    public void removeUnitIn(Newbie newbie) {}

    public void lifeCycle() {}

    @Override
    public String toString() {
        return "Macro{" +
                "macroName=" + getName() +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean worksWith(Newbie unit) {
        if (unitIn.contains(unit)) return true;

        if (unit.getUnitImage().getBoundsInParent().intersects(getMacroContainer().getBoundsInParent())) {
            unit.setProcessing(true);
            addUnitIn(unit);
            return true;
        }

        return false;
    }

    public void aimUnit(Newbie unit) {
        unit.setAim(  getCenterX(), getCenterY() );
    }
}
