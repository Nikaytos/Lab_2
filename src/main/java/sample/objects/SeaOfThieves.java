package sample.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.objects.Micro.Newbie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SeaOfThieves {
    final public static int MAX_X = 3000;
    final public static  int MAX_Y = 2000;
    public static final double SIZE = 1.5;
    //---------------------------------------------------------
    private static Pane root;
    private static ImageView bg;
    private static ArrayList<Newbie> newbies;
    private HashMap<String, ArrayList> objects;
    public SeaOfThieves() {
        root = new Pane();
        root.setMinWidth(MAX_X);
        root.setMinHeight(MAX_Y);

        newbies = new ArrayList<>();

        Image mapImage = new Image(Objects.requireNonNull(Main.class.getResource("images/bg.jpeg")).toString());
        bg = new ImageView(mapImage);
        bg.setFitWidth(MAX_X);
        bg.setFitHeight(MAX_Y);
        bg.setPreserveRatio(false);
        bg.setSmooth(true);
        root.getChildren().add(bg);

    }

    public static Pane getRoot() {
        return root;
    }

    public static ImageView getBg() {
        return bg;
    }

    public static ArrayList<Newbie> getNewbies() {
        return newbies;
    }

    public static ArrayList<String> getParamsToChange( int index ){
            Newbie n = newbies.get(index);
            ArrayList<String> arr= new ArrayList<>();
            arr.add( n.getName() );
            arr.add( n.getNewbieHealth() );
            arr.add( n.getTeam() );
            arr.add( n.getX() );
            arr.add( n.getY() );
            return arr;
        }

    public static ArrayList<String> getNames() {
        ArrayList<String> arr = new ArrayList<>();
        newbies.forEach(n -> arr.add(n.toString()));
        return arr;
    }
}