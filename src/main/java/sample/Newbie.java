package sample;

import javafx.animation.*;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Objects;
import java.util.Random;
import static javafx.util.Duration.millis;
import static javafx.util.Duration.seconds;

import static sample.Operations.MAX_Y;
import static sample.Operations.MIN_Y;
import static sample.Operations.MAX_X;
import static sample.Operations.MIN_X;

public class Newbie {
//---------------------------------------------------------
    public static final int MAX_X_NEWBIE = MAX_X - 100;
    public static final int MIN_X_NEWBIE = MIN_X;
    public static final int MAX_Y_NEWBIE = MAX_Y - 130;
    public static final int MIN_Y_NEWBIE = MIN_Y;
    public static final int MAX_LENGTH_NAME = 12;
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 1;
    public static final int SPEED = 10;
//---------------------------------------------------------
    private static final Random rand = new Random();
    private static final Color[] TEAM_COLORS = {Color.RED, Color.BLUE};
    private static final String[] NAMES =  {"Adam", "Benjamin", "Charles", "David", "Ethan", "Frank", "George", "Henry",
            "Isaac", "John", "Kevin", "Liam", "Matthew", "Nathan", "Oliver", "Peter", "Quentin",
            "Robert", "Samuel", "Thomas", "Ulysses", "Victor", "William", "Xavier", "Yves", "Zachary"};
    private static int defaultValueHealth() {return rand.nextInt(MAX_HEALTH-MIN_HEALTH+1)+MIN_HEALTH;}
    private static int defaultValueX() {return rand.nextInt(MAX_X_NEWBIE-MIN_X_NEWBIE+1)+MIN_X_NEWBIE;}
    private static int defaultValueY() {return rand.nextInt(MAX_Y_NEWBIE-MIN_Y_NEWBIE+1)+MIN_Y_NEWBIE;}
//---------------------------------------------------------
    private final Group newbieContainer;
    private double x;
    private double y;
    private double health;
    private final Rectangle healthBar;
    private final Rectangle healthBarBackground;
    private boolean active;
    private final ImageView iNewbie;
    private final Label name;
    private final DropShadow shadow;
    private final DropShadow shadowActive;
    private String team;
    private int direction = 1;
//---------------------------------------------------------
    public Newbie(String n, double h, Color team, double _x, double _y) {
        Image img = new Image(Objects.requireNonNull(SeaOfThieves.class.getResource("newbie.png")).toString(), 100, 100, false, true);
        iNewbie = new ImageView(img);
        healthBar = new Rectangle(0, 0, 100, 5);
        healthBarBackground = new Rectangle(0, 0, 100, 5);
        newbieContainer = new Group();
        name = new Label();
        TranslateTransition nameTransition = new TranslateTransition();
        shadow = new DropShadow();
        shadowActive = new DropShadow();
        DropShadow nameEffect = new DropShadow();
        Bloom bloom = new Bloom();
        active = false;
//---------------------------------------------------------
        x = _x;
        y = _y;
        setCoordinates();
//---------------------------------------------------------
        setName(n);
        name.setFont(Font.font("System", FontWeight.BOLD, 14));
        name.setTextFill(Color.WHITE);
        name.setEffect(bloom);
        nameEffect.setBlurType(BlurType.GAUSSIAN);
        nameEffect.setColor(Color.BLACK);
        nameEffect.setRadius(4);
        nameEffect.setSpread(0.6);
        nameEffect.setInput(name.getEffect());
        name.setEffect(nameEffect);
        newbieContainer.getChildren().addAll(name);
//---------------------------------------------------------
        healthBar.setStroke(Color.BLACK);
        healthBar.setStrokeWidth(0.4);
        healthBar.setArcWidth(5);
        healthBar.setArcHeight(5);
        healthBarBackground.setFill(Color.WHITE);
        healthBarBackground.setStroke(Color.BLACK);
        healthBarBackground.setStrokeWidth(0.4);
        healthBarBackground.setArcWidth(5);
        healthBarBackground.setArcHeight(5);
        setHealth(h);
        newbieContainer.getChildren().addAll(healthBarBackground, healthBar);
//---------------------------------------------------------
        iNewbie.setPreserveRatio(true);
        iNewbie.setSmooth(true);
        iNewbie.setCache(true);
        iNewbie.setCacheHint(CacheHint.QUALITY);
        newbieContainer.getChildren().add(iNewbie);
//---------------------------------------------------------
        setTeam(team);
        shadow.setRadius(7);
        shadow.setSpread(0.8);
        iNewbie.setEffect(shadow);
//---------------------------------------------------------
        shadowActive.setColor(Color.GREENYELLOW);
        shadowActive.setRadius(10);
        shadowActive.setSpread(0.8);
        shadowActive.setInput(iNewbie.getEffect());
//---------------------------------------------------------
        nameTransition.setNode(name);
        nameTransition.setDuration(seconds(0.4));
        nameTransition.setFromY(0);
        nameTransition.setToY(-5);
        nameTransition.setInterpolator(Interpolator.EASE_OUT);
        nameTransition.setCycleCount(Animation.INDEFINITE);
        nameTransition.setAutoReverse(true);
        nameTransition.play();
//---------------------------------------------------------
        iNewbie.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            ScaleTransition scaleTransition = new ScaleTransition(millis(100), iNewbie);
            scaleTransition.setToX(1.1* direction);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
//---------------------------------------------------------
        iNewbie.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            ScaleTransition scaleTransition = new ScaleTransition(millis(100), iNewbie);
            scaleTransition.setToX(direction);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
//---------------------------------------------------------
        jump();
//---------------------------------------------------------
        SeaOfThieves.group.getChildren().add(newbieContainer);
    }
//---------------------------------------------------------
    public Newbie() {
        this(NAMES[rand.nextInt(NAMES.length)],
                defaultValueHealth(),
                TEAM_COLORS[rand.nextInt(TEAM_COLORS.length)],
                defaultValueX(),
                defaultValueY());
        System.out.print("Random newbie appeared: " + this + "\n");
    }
//---------------------------------------------------------
    static {
        System.out.println("Та нехай почнеться битва!");
    }
    {
        System.out.println("Ласкаво просимо до світу піратів!");
    }
//---------------------------------------------------------
    private static double parseDouble(String value, double defaultValue, double minValue, double maxValue) {
        try {
            double result = Double.parseDouble(value);
            if (result < minValue) {
                return minValue;
            }
            if (result > maxValue) {
                return maxValue;
            }
            return result;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
//---------------------------------------------------------
    private static String limitString(String value, String defaultValue) {
        if (value.equals("")) {
            return defaultValue;
        }
        if (value.length() > Newbie.MAX_LENGTH_NAME) {
            return value.substring(0, Newbie.MAX_LENGTH_NAME);
        }
        return value;
    }
//---------------------------------------------------------
    private static Color parseColor(String sTeam) {
        Color defaultValue = TEAM_COLORS[rand.nextInt(TEAM_COLORS.length)];
        try {
            if (sTeam.equals("")) {
                return defaultValue;
            }
            return Color.valueOf(sTeam);
        } catch (Exception e) {
            return defaultValue;
        }
    }
//---------------------------------------------------------
    public static void changeUnit(int unitIndex, String sName, String sHealth, String cTeam, String sX, String sY) {
        Newbie n = NewbieManager.newbies.get(unitIndex);

        String s1 = n.toString();

        n.setName(limitString(sName, NAMES[rand.nextInt(NAMES.length)]));
        n.setHealth(parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH));
        n.setTeam(parseColor(cTeam));
        n.setX(parseDouble(sX, defaultValueX(), MIN_X_NEWBIE, MAX_X_NEWBIE));
        n.setY(parseDouble(sY, defaultValueY(), MIN_Y_NEWBIE, MAX_Y_NEWBIE));

        String s2 = n.toString();
        if (!s1.equals(s2)) System.out.println("Edited:\n" + s1 + "\nto:\n" + s2);
    }
//---------------------------------------------------------
    public static void createNewUnit(String sName, String sHealth, String cTeam, String sX, String sY) {
        String name = limitString(sName, NAMES[rand.nextInt(NAMES.length)]);
        double h = parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH);
        Color team = parseColor(cTeam);
        double x = parseDouble(sX, defaultValueX(), MIN_X_NEWBIE, MAX_X_NEWBIE);
        double y = parseDouble(sY, defaultValueY(), MIN_Y_NEWBIE, MAX_Y_NEWBIE);
        Newbie n = new Newbie(name, h, team, x, y);
        System.out.println(n);
        NewbieManager.newbies.add(n);
    }
//---------------------------------------------------------
    public String getName(){
        return name.getText();
    }
//---------------------------------------------------------
    public void setName(String n){
        name.setText(n);
    }
//---------------------------------------------------------
    public String getHealth() {
        return Double.toString(health);
    }
//---------------------------------------------------------
    public void setHealth(Double h){
        health = h;
        double healthPercentage = health / MAX_HEALTH;
        if (healthPercentage > 0.7) {
            healthBar.setFill(Color.LIMEGREEN);
        } else if (healthPercentage > 0.4) {
            healthBar.setFill(Color.YELLOW);
        } else {
            healthBar.setFill(Color.RED);
        }
        healthBar.setWidth(healthPercentage * 100);
    }
//---------------------------------------------------------
    public String getTeam() {
        return team;
    }
//---------------------------------------------------------
    public void setTeam(Color color){
        shadow.setColor(color);
        if (shadow.getColor() == Color.BLUE) {
            team = "GOOD";
        }
        else if (shadow.getColor() == Color.RED) {
            team = "BAD";
        }
    }
//---------------------------------------------------------
    public String getX(){
        return Double.toString(x);
    }
//---------------------------------------------------------
    public void setX( double _x ){
        x= _x;
        setCoordinates();
    }
//---------------------------------------------------------
    public String getY(){
        return Double.toString(y);
    }
//---------------------------------------------------------
    public void setY( double _y ){
        y= _y;
        setCoordinates();
    }
//---------------------------------------------------------
    public void setCoordinates(){
        name.setLayoutX(x);
        name.setLayoutY(y);

        healthBar.setLayoutX(x);
        healthBar.setLayoutY(y + 20);
        healthBarBackground.setLayoutX(x);
        healthBarBackground.setLayoutY(y + 20);

        iNewbie.setX(x);
        iNewbie.setY(y+30);
    }
//---------------------------------------------------------
    public void jump() {
        TranslateTransition translateTransition = new TranslateTransition(millis(150), iNewbie);
        translateTransition.setToY(-100);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        TranslateTransition backTransition = new TranslateTransition(millis(150), iNewbie);
        backTransition.setToY(0);
        backTransition.setInterpolator(Interpolator.EASE_OUT);
        SequentialTransition sequentialTransition = new SequentialTransition(iNewbie, translateTransition, backTransition);
        sequentialTransition.play();
    }
//---------------------------------------------------------
    public void move( double dx, double dy, int dir ){
        setX(x+dx);
        setY(y+dy);
        if (dir != 0) {
            direction = dir;
            iNewbie.setScaleX(direction);
        }

        if (x>MAX_X_NEWBIE) setX(MAX_X_NEWBIE);
        if (x<MIN_X_NEWBIE) setX(MIN_X_NEWBIE);
        if (y>MAX_Y_NEWBIE) setY(MAX_Y_NEWBIE);
        if (y<MIN_Y_NEWBIE) setY(MIN_Y_NEWBIE);
    }
//---------------------------------------------------------
    public boolean isActive() {
        return active;
    }
//---------------------------------------------------------
    public void flipActivation(){
        if (active) iNewbie.setEffect(shadow);
        else iNewbie.setEffect(shadowActive);
        active = !active;
    }
//---------------------------------------------------------
    public boolean mouseActivate( double mx, double my ){
        if(iNewbie.boundsInParentProperty().get().contains(mx,my)){
            flipActivation();
            return true;
        }
        return false;
    }
//---------------------------------------------------------
    public void delete(){
        System.out.println(getName() + " попрощався з життям. . .");
        SeaOfThieves.group.getChildren().removeAll(newbieContainer);
    }
//---------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newbie newbie = (Newbie) o;
        return Objects.equals(team, newbie.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team);
    }

    @Override
    public String toString() {
        return "Newbie{" +
                "name=" + getName() +
                ", health=" + health +
                ", team=" + team +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
