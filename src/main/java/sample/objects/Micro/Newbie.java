package sample.objects.Micro;

import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
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

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import static javafx.util.Duration.millis;
import static javafx.util.Duration.seconds;

import sample.Main;
import sample.objects.Macro.Macro;
import sample.objects.SeaOfThieves;

import static sample.Main.getRandom;

public class Newbie implements Cloneable {

    protected static final double IMAGE_SIZE = 100 * SeaOfThieves.SIZE;
    protected static final double HEALTH_HEIGHT = 5 * SeaOfThieves.SIZE;
    protected static final double FONT_SIZE = 14 * SeaOfThieves.SIZE;
    protected static final Rectangle2D.Double UNIT_CONTAINER_BOUNDS = new Rectangle2D.Double(0, 0, IMAGE_SIZE, IMAGE_SIZE + HEALTH_HEIGHT + FONT_SIZE * 1.3 + 5);
    protected static final Point MAX_UNIT = new Point((int) (SeaOfThieves.MAX_X - 5 - UNIT_CONTAINER_BOUNDS.width), (int) (SeaOfThieves.MAX_Y - 5 - UNIT_CONTAINER_BOUNDS.height));
    protected static final Point MIN_UNIT = new Point(5, 5);
    public static final int SPEED = 5;

    public static final int MAX_LENGTH_NAME = 7;
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 1;

    protected static final Color[] TEAM_COLORS = {Color.RED, Color.BLUE};
    protected static final String[] NAMES = {"Adam", "Brandon", "Charles", "David", "Ethan", "Frank", "George", "Henry",
            "Isaac", "John", "Kevin", "Liam", "Matthew", "Nathan", "Oliver", "Peter", "Quentin",
            "Robert", "Samuel", "Thomas", "Ulysses", "Victor", "William", "Xavier", "Yves", "Zachary"};

    protected static int defaultValueHealth() {
        return getRandom().nextInt(MAX_HEALTH - MIN_HEALTH + 1) + MIN_HEALTH;
    }

    protected static int defaultValueX() {
        return getRandom().nextInt(MAX_UNIT.x - MIN_UNIT.x + 1) + MIN_UNIT.x;
    }

    protected static int defaultValueY() {
        return getRandom().nextInt(MAX_UNIT.y - MIN_UNIT.y + 1) + MIN_UNIT.y;
    }

    protected Group unitContainer;
    protected String type;
    protected Label unitName;
    protected double unitHealth;
    protected String unitTeam;
    protected int x;
    protected int y;
    protected ImageView unitImage;
    protected Rectangle healthBar;
    protected Rectangle healthBarBackground;
    protected DropShadow shadow;
    protected DropShadow shadowActive;

    protected boolean active;
    protected int direction;

    public Group getUnitContainer() {
        return unitContainer;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return unitName.getText();
    }

    public double getUnitHealth() {
        return unitHealth;
    }

    public ImageView getUnitImage() {
        return unitImage;
    }

    public String getUnitTeam() {
        return unitTeam;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Point getMAX_UNIT() {
        return MAX_UNIT;
    }

    public static Point getMIN_UNIT() {
        return MIN_UNIT;
    }

    public Newbie(String name, double health, Color team, double x, double y) {

        type = "Newbie";

        unitName = new Label();
        Image img = new Image(Objects.requireNonNull(Main.class.getResource("images/newbie.png")).toString(), IMAGE_SIZE, IMAGE_SIZE, false, true);
        unitImage = new ImageView(img);
        healthBar = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        healthBarBackground = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        unitContainer = new Group();
        shadow = new DropShadow();
        shadowActive = new DropShadow();
        active = false;

        setName(name);

        setUnitHealth(health);

        setUnitTeam(team);

        initialize();

        this.x = (int) x;
        this.y = (int) y;
        setCoordinates();

        spawnTransition();

        unitContainer.getChildren().addAll(unitName, healthBarBackground, healthBar, unitImage);
    }

    public Newbie() {
        this(NAMES[getRandom().nextInt(NAMES.length)],
                0,
                TEAM_COLORS[getRandom().nextInt(TEAM_COLORS.length)],
                0,
                0);
        setUnitHealth((double) defaultValueHealth());
        setX(defaultValueX());
        setY(defaultValueY());
        System.out.print("Random newbie appeared: " + this + "\n");
    }

    static {
        System.out.println("Та нехай почнеться битва!");
    }

    {
        System.out.println("Ласкаво просимо до світу піратів!");
    }

    private void initialize() {
        unitName.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE));
        unitName.setTextFill(Color.WHITE);
        unitName.setAlignment(Pos.CENTER);
        unitName.setPrefWidth(IMAGE_SIZE);
        unitName.setEffect(new Bloom());
        DropShadow nameEffect = new DropShadow();
        nameEffect.setBlurType(BlurType.GAUSSIAN);
        nameEffect.setColor(Color.BLACK);
        nameEffect.setRadius(4);
        nameEffect.setSpread(0.6);
        nameEffect.setInput(unitName.getEffect());
        unitName.setEffect(nameEffect);

        TranslateTransition nameTransition = new TranslateTransition();
        nameTransition.setNode(unitName);
        nameTransition.setDuration(seconds(0.4));
        nameTransition.setFromY(0);
        nameTransition.setToY(-5);
        nameTransition.setInterpolator(Interpolator.EASE_OUT);
        nameTransition.setCycleCount(Animation.INDEFINITE);
        nameTransition.setAutoReverse(true);
        nameTransition.play();

        healthBar.setStroke(Color.BLACK);
        healthBar.setStrokeWidth(0.4);
        healthBar.setArcWidth(5);
        healthBar.setArcHeight(5);
        healthBarBackground.setFill(Color.WHITE);
        healthBarBackground.setStroke(Color.BLACK);
        healthBarBackground.setStrokeWidth(0.4);
        healthBarBackground.setArcWidth(5);
        healthBarBackground.setArcHeight(5);

        direction = 1;
        unitImage.setPreserveRatio(true);
        unitImage.setSmooth(true);
        unitImage.setCache(true);
        unitImage.setCacheHint(CacheHint.QUALITY);

        shadow.setRadius(7);
        shadow.setSpread(0.8);
        unitImage.setEffect(shadow);

        shadowActive.setColor(Color.GREENYELLOW);
        shadowActive.setRadius(10);
        shadowActive.setSpread(0.8);
        shadowActive.setInput(unitImage.getEffect());

        unitImage.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            ScaleTransition scaleTransition = new ScaleTransition(millis(100), unitImage);
            scaleTransition.setToX(1.1 * direction);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        unitImage.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            ScaleTransition scaleTransition = new ScaleTransition(millis(100), unitImage);
            scaleTransition.setToX(direction);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });
    }

    public static double parseDouble(String value, double defaultValue, double minValue, double maxValue) {
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

    public static String limitString(String value, String defaultValue) {
        if (value.equals("")) {
            return defaultValue;
        }
        if (value.length() > Newbie.MAX_LENGTH_NAME) {
            return value.substring(0, Newbie.MAX_LENGTH_NAME);
        }
        return value;
    }

    public static Color parseColor(String sTeam) {
        Color defaultValue = TEAM_COLORS[getRandom().nextInt(TEAM_COLORS.length)];
        try {
            if (sTeam.equals("")) {
                return defaultValue;
            }
            return Color.valueOf(sTeam);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void changeUnit(int unitIndex, String sName, String sHealth, String cTeam, String sX, String sY) {
        Newbie n = Main.getWorld().getUnits().get(unitIndex);

        String s1 = n.toString();

        n.setName(limitString(sName, NAMES[getRandom().nextInt(NAMES.length)]));
        n.setUnitHealth(parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH));
        n.setUnitTeam(parseColor(cTeam));
        n.setX((int) parseDouble(sX, defaultValueX(), getMIN_UNIT().x, getMAX_UNIT().x));
        n.setY((int) parseDouble(sY, defaultValueY(), getMIN_UNIT().y, getMAX_UNIT().y));

        String s2 = n.toString();
        if (!s1.equals(s2)) System.out.println("Edited:\n" + s1 + "\nto:\n" + s2);
    }

    public static void createNewUnit(String sName, String sHealth, String cTeam, String sX, String sY) {
        String name = limitString(sName, NAMES[getRandom().nextInt(NAMES.length)]);
        double h = parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH);
        Color team = parseColor(cTeam);
        double x = parseDouble(sX, defaultValueX(), getMIN_UNIT().x, getMAX_UNIT().x);
        double y = parseDouble(sY, defaultValueY(), getMIN_UNIT().y, getMAX_UNIT().y);
        Newbie n = new Newbie(name, h, team, x, y);
        System.out.println(n);
        Main.getWorld().addNewUnit(n);
    }


    public void setName(String name) {
        unitName.setText(name);
    }

    public void setUnitHealth(Double unitHealth) {
        this.unitHealth = unitHealth;
        double healthPercentage = this.unitHealth / MAX_HEALTH;
        if (healthPercentage > 0.7) {
            healthBar.setFill(Color.LIMEGREEN);
        } else if (healthPercentage > 0.4) {
            healthBar.setFill(Color.YELLOW);
        } else {
            healthBar.setFill(Color.RED);
        }
        healthBar.setWidth(healthPercentage * IMAGE_SIZE);
    }

    public void setUnitTeam(Color color) {
        shadow.setColor(color);
        if (shadow.getColor() == Color.BLUE) {
            unitTeam = "GOOD";
        } else if (shadow.getColor() == Color.RED) {
            unitTeam = "BAD";
        }
    }

    public void setX(int x) {
        this.x = x;
        setCoordinates();
    }

    public void setY(int y) {
        this.y = y;
        setCoordinates();
    }

    public void setCoordinates() {
        unitName.setLayoutX(x);
        unitName.setLayoutY(y);

        healthBarBackground.setLayoutX(x);
        healthBarBackground.setLayoutY(unitName.getLayoutY() + unitName.getFont().getSize() * 1.3 + 5);
        healthBar.setLayoutX(x);
        healthBar.setLayoutY(healthBarBackground.getLayoutY());

        unitImage.setLayoutX(x);
        unitImage.setLayoutY(healthBarBackground.getLayoutY() + healthBarBackground.getHeight());
    }

    public void spawnTransition() {
        TranslateTransition translateTransition = new TranslateTransition(millis(150), unitContainer);
        translateTransition.setToY(-100);
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        TranslateTransition backTransition = new TranslateTransition(millis(150), unitContainer);
        backTransition.setToY(0);
        backTransition.setInterpolator(Interpolator.EASE_OUT);
        SequentialTransition sequentialTransition = new SequentialTransition(unitContainer, translateTransition, backTransition);
        sequentialTransition.play();
    }

    public boolean isActive() {
        return active;
    }

    public void flipActivation() {
        if (active) unitImage.setEffect(shadow);
        else unitImage.setEffect(shadowActive);
        active = !active;
    }

    public boolean mouseIsOn(double mx, double my) {
        return unitImage.getBoundsInParent().contains(new Point2D(mx, my));
    }

    public void moveToBase() {

            for (Macro macro : Main.getWorld().getMacros()) {
                if (macro.getTeam().equals(unitTeam)) {
                    simpleMove(macro.getX(), macro.getY());
                }
            }

    }

    public void move(int dx, int dy, int dir) {
        int finalDX = x + dx;
        int finalDY = y + dy;
        setX(Math.max(Math.min(finalDX, MAX_UNIT.x), MIN_UNIT.x));
        setY(Math.max(Math.min(finalDY, MAX_UNIT.y), MIN_UNIT.y));
        if (dir != 0) {
            direction = dir;
            unitImage.setScaleX(direction);
        }
    }

    public void simpleMove(int x, int y) {
        int dx = 0;
        int dy = 0;
        int dir = 0;
        if (x < this.x - SPEED){
            dx = -SPEED;
            dir = 1;
        }
        else if (x > this.x + SPEED){
            dx = SPEED;
            dir = -1;
        }
        if (y > this.y + SPEED){
            dy = SPEED;
        }
        else if (y < this.y - SPEED){
            dy = -SPEED;
        }
        move(dx, dy, dir);
    }

    @Override
    public String toString() {
        return "Newbie{" +
                "name=" + getName() +
                ", health=" + unitHealth +
                ", team=" + unitTeam +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public Newbie clone() throws CloneNotSupportedException {
        Newbie clone = (Newbie) super.clone();
        clone.unitName = new Label();
        clone.unitImage = new ImageView(this.unitImage.getImage());
        clone.healthBar = new Rectangle(0, 0, this.healthBar.getWidth(), this.healthBar.getHeight());
        clone.healthBarBackground = new Rectangle(0, 0, this.healthBarBackground.getWidth(), this.healthBarBackground.getHeight());
        clone.unitContainer = new Group();
        clone.shadow = new DropShadow();
        clone.shadowActive = new DropShadow();
        clone.active = false;
        clone.setName(this.getName());
        clone.setUnitHealth(this.getUnitHealth());
        clone.setUnitTeam(this.shadow.getColor());

        clone.initialize();

        clone.setX((int) parseDouble(Double.toString(this.getX() + this.getUnitImage().getLayoutBounds().getMaxX() + 10), this.getX(), MIN_UNIT.x, MAX_UNIT.x));

        clone.spawnTransition();

        clone.unitContainer.getChildren().addAll(clone.unitName, clone.healthBarBackground, clone.healthBar, clone.unitImage);
        return clone;
    }
}
