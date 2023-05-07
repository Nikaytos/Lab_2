package sample.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.objects.Macro.Macro;
import sample.objects.Micro.Newbie;

import java.util.ArrayList;
import java.util.Objects;

public class SeaOfThieves {
    final public static int MAX_X = 3000;
    final public static  int MAX_Y = 2000;
    public static final double SIZE = 1.5;

    private Pane root;
    private ImageView bg;
    private ArrayList<Newbie> units;
    private ArrayList<Macro> macros;

    public SeaOfThieves() {
        root = new Pane();
        root.setMinWidth(MAX_X);
        root.setMinHeight(MAX_Y);

        units = new ArrayList<>();
        macros = new ArrayList<>();

        Image mapImage = new Image(Objects.requireNonNull(Main.class.getResource("images/bg.jpeg")).toString());
        bg = new ImageView(mapImage);
        bg.setFitWidth(MAX_X);
        bg.setFitHeight(MAX_Y);
        bg.setPreserveRatio(false);
        bg.setSmooth(true);
        root.getChildren().add(bg);

    }

    public void addNewUnit(Newbie unit) {
        units.add(unit);
        root.getChildren().add(unit.getUnitContainer());
    }

    public void deleteUnit(Newbie unit) {
        root.getChildren().remove(unit.getUnitContainer());
        units.remove(unit);
    }

    public void addNewMacro(Macro macro) {
        macros.add(macro);
        root.getChildren().add(macro.getMacroContainer());
    }

    public Pane getRoot() {
        return root;
    }

    public ArrayList<Newbie> getUnits() {
        return units;
    }

    public ArrayList<Macro> getMacros() {
        return macros;
    }

    public ArrayList<String> getParamsToChange(int index ){
            Newbie n = units.get(index);
            ArrayList<String> arr= new ArrayList<>();
            arr.add( n.getName() );
            arr.add( Double.toString(n.getUnitHealth()) );
            arr.add( n.getUnitTeam() );
            arr.add( Double.toString(n.getX()) );
            arr.add( Double.toString(n.getY()) );
            return arr;
        }

    public ArrayList<String> getNames() {
        ArrayList<String> arr = new ArrayList<>();
        units.forEach(n -> arr.add(n.toString()));
        return arr;
    }
}
