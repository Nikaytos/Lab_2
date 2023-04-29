package sample.objects.Macro;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

public abstract class Macro {
    protected double x;
    protected double y;

    protected ImageView macroImage;
    protected Label macroName;

    protected Group macroContainer;

    protected String macroType = "Nothing";

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void setCoordinates();
}
