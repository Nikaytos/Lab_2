package sample.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.objects.Macro.BaseBad;
import sample.objects.Macro.BaseGood;
import sample.objects.Macro.Macro;
import sample.objects.Macro.TreasuresCastle;
import sample.objects.Micro.Newbie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SeaOfThieves {
    final public static int MAX_X = 3000;
    final public static  int MAX_Y = 2000;
    public static final double SIZE = 1.5;

    private Pane root;
    private ImageView bg;
    private ArrayList<Newbie> units;
    private ArrayList<Macro> macros;
    private HashMap<String, ArrayList> objects = new HashMap<>();

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

        objects.put("Newbie", new ArrayList<Newbie>());
        objects.put("BaseGood", new ArrayList<BaseGood>());
        objects.put("BaseBad", new ArrayList<BaseBad>());
        objects.put("TreasuresCastle", new ArrayList<TreasuresCastle>());
    }

    public void addNewUnit(Newbie newbie) {
        units.add(newbie);
        root.getChildren().add(newbie.getUnitContainer());

        objects.get(newbie.getType()).add(newbie);
    }

    public void deleteUnit(Newbie newbie) {
        System.out.println(newbie.getName() + " попрощався з життям. . .");

        root.getChildren().remove(newbie.getUnitContainer());
        units.remove(newbie);

        objects.get(newbie.getType()).remove(newbie);
    }

    public void addNewMacro(Macro macro) {
        macros.add(macro);
        root.getChildren().add(macro.getMacroContainer());

        objects.get(macro.getType()).add(macro);
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
