package sample.objects.Micro;

import javafx.animation.*;
import javafx.geometry.Point2D;
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
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import static javafx.util.Duration.millis;
import static javafx.util.Duration.seconds;

import sample.Main;
import sample.objects.SeaOfThieves;
import static sample.Main.random;

public class Newbie {
//---------------------------------------------------------
    private static final double IMAGE_SIZE = 100 * SeaOfThieves.SIZE;
    private static final double HEALTH_HEIGHT = 5 * SeaOfThieves.SIZE;
    private static final double FONT_SIZE = 14 * SeaOfThieves.SIZE;
    private static final Rectangle2D.Double NEWBIE_CONTAINER_BOUNDS = new Rectangle2D.Double(0, 0, IMAGE_SIZE, IMAGE_SIZE + HEALTH_HEIGHT + FONT_SIZE*1.3);
    private static final int MAX_X_NEWBIE = (int) (SeaOfThieves.MAX_X - 5 - NEWBIE_CONTAINER_BOUNDS.width);
    private static final int MIN_X_NEWBIE = 5;
    private static final int MAX_Y_NEWBIE = (int) (SeaOfThieves.MAX_Y - 5 - NEWBIE_CONTAINER_BOUNDS.height);
    private static final int MIN_Y_NEWBIE = 5;
    private static final double SPEED = 3;

    public static final int MAX_LENGTH_NAME = 12;
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 1;
//---------------------------------------------------------RANDOM_VALUES
    private static final Color[] TEAM_COLORS = {Color.RED, Color.BLUE};
    private static final String[] NAMES =  {"Adam", "Benjamin", "Charles", "David", "Ethan", "Frank", "George", "Henry",
            "Isaac", "John", "Kevin", "Liam", "Matthew", "Nathan", "Oliver", "Peter", "Quentin",
            "Robert", "Samuel", "Thomas", "Ulysses", "Victor", "William", "Xavier", "Yves", "Zachary"};
    private static int defaultValueHealth() {return random.nextInt(MAX_HEALTH-MIN_HEALTH+1)+MIN_HEALTH;}
    private static int defaultValueX() {return random.nextInt(MAX_X_NEWBIE-MIN_X_NEWBIE+1)+MIN_X_NEWBIE;}
    private static int defaultValueY() {return random.nextInt(MAX_Y_NEWBIE-MIN_Y_NEWBIE+1)+MIN_Y_NEWBIE;}
//---------------------------------------------------------PRIVATE_VARIABLES
    private final Group newbieContainer;
    private final Label newbieName;
    private double newbieHealth;
    private String team;
    private double x;
    private double y;
    private final ImageView imageNewbie;
    private final TranslateTransition nameTransition;
    private final DropShadow nameEffect;
    private final Rectangle healthBar;
    private final Rectangle healthBarBackground;
    private final DropShadow shadow;
    private final DropShadow shadowActive;
    private boolean active;
    private int direction;
//---------------------------------------------------------
    public Newbie(String name, double health, Color team, double x, double y) {
//---------------------------------------------------------VARIABLES
        Image img = new Image(Objects.requireNonNull(Main.class.getResource("images/newbie.png")).toString(), IMAGE_SIZE, IMAGE_SIZE, false, true);
        imageNewbie = new ImageView(img);
        healthBar = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        healthBarBackground = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        newbieContainer = new Group();
        newbieName = new Label();
        nameTransition = new TranslateTransition();
        shadow = new DropShadow();
        shadowActive = new DropShadow();
        nameEffect = new DropShadow();
        active = false;
//---------------------------------------------------------NAME
        setName(name);
        this.newbieName.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE));
        this.newbieName.setTextFill(Color.WHITE);
        this.newbieName.setEffect(new Bloom());
        nameEffect.setBlurType(BlurType.GAUSSIAN);
        nameEffect.setColor(Color.BLACK);
        nameEffect.setRadius(4);
        nameEffect.setSpread(0.6);
        nameEffect.setInput(this.newbieName.getEffect());
        this.newbieName.setEffect(nameEffect);
        newbieContainer.getChildren().addAll(this.newbieName);
//---------------------------------------------------------ANIMATION_NAME
        nameTransition.setNode(this.newbieName);
        nameTransition.setDuration(seconds(0.4));
        nameTransition.setFromY(0);
        nameTransition.setToY(-5);
        nameTransition.setInterpolator(Interpolator.EASE_OUT);
        nameTransition.setCycleCount(Animation.INDEFINITE);
        nameTransition.setAutoReverse(true);
        nameTransition.play();
//---------------------------------------------------------HEALTH
        healthBar.setStroke(Color.BLACK);
        healthBar.setStrokeWidth(0.4);
        healthBar.setArcWidth(5);
        healthBar.setArcHeight(5);
        healthBarBackground.setFill(Color.WHITE);
        healthBarBackground.setStroke(Color.BLACK);
        healthBarBackground.setStrokeWidth(0.4);
        healthBarBackground.setArcWidth(5);
        healthBarBackground.setArcHeight(5);
        setNewbieHealth(health);
        newbieContainer.getChildren().addAll(healthBarBackground, healthBar);
//---------------------------------------------------------IMAGE
        direction = 1;
        imageNewbie.setPreserveRatio(true);
        imageNewbie.setSmooth(true);
        imageNewbie.setCache(true);
        imageNewbie.setCacheHint(CacheHint.QUALITY);
        newbieContainer.getChildren().add(imageNewbie);
//---------------------------------------------------------TEAM
        setTeam(team);
        shadow.setRadius(7);
        shadow.setSpread(0.8);
        imageNewbie.setEffect(shadow);
//---------------------------------------------------------SHADOW
        shadowActive.setColor(Color.GREENYELLOW);
        shadowActive.setRadius(10);
        shadowActive.setSpread(0.8);
        shadowActive.setInput(imageNewbie.getEffect());
//---------------------------------------------------------ANIMATION_IMAGE
        imageNewbie.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            ScaleTransition scaleTransition = new ScaleTransition(millis(100), imageNewbie);
            scaleTransition.setToX(1.1* direction);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });
//---------------------------------------------------------ANIMATION_IMAGE
        imageNewbie.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            ScaleTransition scaleTransition = new ScaleTransition(millis(100), imageNewbie);
            scaleTransition.setToX(direction);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
//---------------------------------------------------------COORDINATES
        this.x = x;
        this.y = y;
        setCoordinates();
//---------------------------------------------------------
        spawnTransition();
//---------------------------------------------------------
        SeaOfThieves.getRoot().getChildren().add(newbieContainer);
    }
//---------------------------------------------------------
    public Newbie() {
        this(NAMES[random.nextInt(NAMES.length)],
                defaultValueHealth(),
                TEAM_COLORS[random.nextInt(TEAM_COLORS.length)],
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
        Color defaultValue = TEAM_COLORS[random.nextInt(TEAM_COLORS.length)];
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
        Newbie n = SeaOfThieves.getNewbies().get(unitIndex);

        String s1 = n.toString();

        n.setName(limitString(sName, NAMES[random.nextInt(NAMES.length)]));
        n.setNewbieHealth(parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH));
        n.setTeam(parseColor(cTeam));
        n.setX(parseDouble(sX, defaultValueX(), MIN_X_NEWBIE, MAX_X_NEWBIE));
        n.setY(parseDouble(sY, defaultValueY(), MIN_Y_NEWBIE, MAX_Y_NEWBIE));

        String s2 = n.toString();
        if (!s1.equals(s2)) System.out.println("Edited:\n" + s1 + "\nto:\n" + s2);
    }
//---------------------------------------------------------
    public static void createNewUnit(String sName, String sHealth, String cTeam, String sX, String sY) {
        String name = limitString(sName, NAMES[random.nextInt(NAMES.length)]);
        double h = parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH);
        Color team = parseColor(cTeam);
        double x = parseDouble(sX, defaultValueX(), MIN_X_NEWBIE, MAX_X_NEWBIE);
        double y = parseDouble(sY, defaultValueY(), MIN_Y_NEWBIE, MAX_Y_NEWBIE);
        Newbie n = new Newbie(name, h, team, x, y);
        System.out.println(n);
        SeaOfThieves.getNewbies().add(n);
    }
//---------------------------------------------------------
    public Group getNewbieContainer() {
        return newbieContainer;
    }
//---------------------------------------------------------
    public String getName(){
        return newbieName.getText();
    }
//---------------------------------------------------------
    public void setName(String name){
        newbieName.setText(name);
    }
//---------------------------------------------------------
    public String getNewbieHealth() {
        return Double.toString(newbieHealth);
    }
//---------------------------------------------------------
    public void setNewbieHealth(Double newbieHealth){
        this.newbieHealth = newbieHealth;
        double healthPercentage = this.newbieHealth / MAX_HEALTH;
        if (healthPercentage > 0.7) {
            healthBar.setFill(Color.LIMEGREEN);
        } else if (healthPercentage > 0.4) {
            healthBar.setFill(Color.YELLOW);
        } else {
            healthBar.setFill(Color.RED);
        }
        healthBar.setWidth(healthPercentage * IMAGE_SIZE);
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
        newbieName.setLayoutX(x);
        newbieName.setLayoutY(y);

        healthBarBackground.setLayoutX(newbieName.getLayoutX());
        healthBarBackground.setLayoutY(newbieName.getLayoutY() + newbieName.getFont().getSize()*1.3);
        healthBar.setLayoutX(healthBarBackground.getLayoutX());
        healthBar.setLayoutY(healthBarBackground.getLayoutY());

        imageNewbie.setX(healthBarBackground.getLayoutX());
        imageNewbie.setY(healthBarBackground.getLayoutY()+healthBarBackground.getHeight());
    }
//---------------------------------------------------------
    public void spawnTransition() {
        TranslateTransition translateTransition = new TranslateTransition(millis(150), newbieContainer);
        translateTransition.setToY(-100);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        TranslateTransition backTransition = new TranslateTransition(millis(150), newbieContainer);
        backTransition.setToY(0);
        backTransition.setInterpolator(Interpolator.EASE_OUT);
        SequentialTransition sequentialTransition = new SequentialTransition(newbieContainer, translateTransition, backTransition);
        sequentialTransition.play();
    }
//---------------------------------------------------------
    public void move(double dx, double dy, int dir){
        double finalDX = dx * SPEED;
        double finalDY = dy * SPEED;
        setX(Math.max(Math.min(finalDX, MAX_X_NEWBIE), MIN_X_NEWBIE));
        setY(Math.max(Math.min(finalDY, MAX_Y_NEWBIE), MIN_Y_NEWBIE));
        if (dir != 0) {
            direction = dir;
            imageNewbie.setScaleX(direction);
        }
    }
//---------------------------------------------------------
    public boolean isActive() {
        return active;
    }
//---------------------------------------------------------
    public void flipActivation(){
        if (active) imageNewbie.setEffect(shadow);
        else imageNewbie.setEffect(shadowActive);
        active = !active;
    }
//---------------------------------------------------------
    public boolean mouseIsActive(double mx, double my ){
        return imageNewbie.getBoundsInParent().contains(new Point2D(mx, my));
    }
//---------------------------------------------------------
    public void delete(){
        System.out.println(getName() + " попрощався з життям. . .");
        SeaOfThieves.getRoot().getChildren().removeAll(newbieContainer);
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
                ", health=" + newbieHealth +
                ", team=" + team +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
