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

import static sample.Main.getRandom;

public class Newbie implements Cloneable {

    protected static final double IMAGE_SIZE = 100 * SeaOfThieves.SIZE;
    protected static final double HEALTH_HEIGHT = 5 * SeaOfThieves.SIZE;
    protected static final double FONT_SIZE = 14 * SeaOfThieves.SIZE;
    protected static final Rectangle2D.Double UNIT_CONTAINER_BOUNDS = new Rectangle2D.Double(0, 0, IMAGE_SIZE, IMAGE_SIZE + HEALTH_HEIGHT + FONT_SIZE * 1.3 + 5);
    public static final int MAX_X_UNIT = (int) (SeaOfThieves.MAX_X - 5 - UNIT_CONTAINER_BOUNDS.width);
    public static final int MIN_X_UNIT = 5;
    public static final int MAX_Y_UNIT = (int) (SeaOfThieves.MAX_Y - 5 - UNIT_CONTAINER_BOUNDS.height);
    public static final int MIN_Y_UNIT = 5;
    public static final double SPEED = 3;

    public static final int MAX_LENGTH_NAME = 12;
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 1;

    protected static final Color[] TEAM_COLORS = {Color.RED, Color.BLUE};
    protected static final String[] NAMES = {"Adam", "Benjamin", "Charles", "David", "Ethan", "Frank", "George", "Henry",
            "Isaac", "John", "Kevin", "Liam", "Matthew", "Nathan", "Oliver", "Peter", "Quentin",
            "Robert", "Samuel", "Thomas", "Ulysses", "Victor", "William", "Xavier", "Yves", "Zachary"};

    protected static int defaultValueHealth() {
        return getRandom().nextInt(MAX_HEALTH - MIN_HEALTH + 1) + MIN_HEALTH;
    }

    protected static int defaultValueX() {
        return getRandom().nextInt(MAX_X_UNIT - MIN_X_UNIT + 1) + MIN_X_UNIT;
    }

    protected static int defaultValueY() {
        return getRandom().nextInt(MAX_Y_UNIT - MIN_Y_UNIT + 1) + MIN_Y_UNIT;
    }

    protected final Group unitContainer;
    protected String type;
    protected final Label unitName;
    protected double health;
    protected String team;
    protected double x;
    protected double y;
    protected final ImageView unitImage;
    protected final Rectangle healthBar;
    protected final Rectangle healthBarBackground;
    protected final DropShadow shadow;
    protected final DropShadow shadowActive;
    protected boolean active;
    protected int direction;

    public Newbie(String name, double health, Color team, double x, double y) {

        type = "Newbie";
        Image img = new Image(Objects.requireNonNull(Main.class.getResource("images/newbie.png")).toString(), IMAGE_SIZE, IMAGE_SIZE, false, true);
        unitImage = new ImageView(img);
        healthBar = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        healthBarBackground = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        unitContainer = new Group();
        unitName = new Label();
        TranslateTransition nameTransition = new TranslateTransition();
        shadow = new DropShadow();
        shadowActive = new DropShadow();
        DropShadow nameEffect = new DropShadow();
        active = false;

        setName(name);
        unitName.setFont(Font.font("System", FontWeight.BOLD, FONT_SIZE));
        unitName.setTextFill(Color.WHITE);
        unitName.setEffect(new Bloom());
        nameEffect.setBlurType(BlurType.GAUSSIAN);
        nameEffect.setColor(Color.BLACK);
        nameEffect.setRadius(4);
        nameEffect.setSpread(0.6);
        nameEffect.setInput(unitName.getEffect());
        unitName.setEffect(nameEffect);
        unitContainer.getChildren().addAll(unitName);

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
        setHealth(health);
        unitContainer.getChildren().addAll(healthBarBackground, healthBar);

        direction = 1;
        unitImage.setPreserveRatio(true);
        unitImage.setSmooth(true);
        unitImage.setCache(true);
        unitImage.setCacheHint(CacheHint.QUALITY);
        unitContainer.getChildren().add(unitImage);

        setTeam(team);
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

        this.x = x;
        this.y = y;
        setCoordinates();

        spawnTransition();

        Main.getWorld().getRoot().getChildren().add(unitContainer);
    }

    public Newbie() {
        this(NAMES[getRandom().nextInt(NAMES.length)],
                defaultValueHealth(),
                TEAM_COLORS[getRandom().nextInt(TEAM_COLORS.length)],
                defaultValueX(),
                defaultValueY());
        System.out.print("Random newbie appeared: " + this + "\n");
    }

    static {
        System.out.println("Та нехай почнеться битва!");
    }

    {
        System.out.println("Ласкаво просимо до світу піратів!");
    }

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

    private static String limitString(String value, String defaultValue) {
        if (value.equals("")) {
            return defaultValue;
        }
        if (value.length() > Newbie.MAX_LENGTH_NAME) {
            return value.substring(0, Newbie.MAX_LENGTH_NAME);
        }
        return value;
    }

    private static Color parseColor(String sTeam) {
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
        n.setHealth(parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH));
        n.setTeam(parseColor(cTeam));
        n.setX(parseDouble(sX, defaultValueX(), MIN_X_UNIT, MAX_X_UNIT));
        n.setY(parseDouble(sY, defaultValueY(), MIN_Y_UNIT, MAX_Y_UNIT));

        String s2 = n.toString();
        if (!s1.equals(s2)) System.out.println("Edited:\n" + s1 + "\nto:\n" + s2);
    }

    public static void createNewUnit(String sName, String sHealth, String cTeam, String sX, String sY) {
        String name = limitString(sName, NAMES[getRandom().nextInt(NAMES.length)]);
        double h = parseDouble(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH);
        Color team = parseColor(cTeam);
        double x = parseDouble(sX, defaultValueX(), MIN_X_UNIT, MAX_X_UNIT);
        double y = parseDouble(sY, defaultValueY(), MIN_Y_UNIT, MAX_Y_UNIT);
        Newbie n = new Newbie(name, h, team, x, y);
        System.out.println(n);
        Main.getWorld().addNewUnit(n);
    }

    public Group getUnitContainer() {
        return unitContainer;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return unitName.getText();
    }

    public void setName(String name) {
        unitName.setText(name);
    }

    public String getHealth() {
        return Double.toString(health);
    }

    public void setHealth(Double health) {
        this.health = health;
        double healthPercentage = this.health / MAX_HEALTH;
        if (healthPercentage > 0.7) {
            healthBar.setFill(Color.LIMEGREEN);
        } else if (healthPercentage > 0.4) {
            healthBar.setFill(Color.YELLOW);
        } else {
            healthBar.setFill(Color.RED);
        }
        healthBar.setWidth(healthPercentage * IMAGE_SIZE);
    }

    public ImageView getUnitImage() {
        return unitImage;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(Color color) {
        shadow.setColor(color);
        if (shadow.getColor() == Color.BLUE) {
            team = "GOOD";
        } else if (shadow.getColor() == Color.RED) {
            team = "BAD";
        }
    }

    public String getX() {
        return Double.toString(x);
    }

    public void setX(double x) {
        this.x = x;
        setCoordinates();
    }

    public String getY() {
        return Double.toString(y);
    }

    public void setY(double y) {
        this.y = y;
        setCoordinates();
    }

    public void setCoordinates() {
        unitName.setLayoutX(x);
        unitName.setLayoutY(y);

        healthBarBackground.setLayoutX(unitName.getLayoutX());
        healthBarBackground.setLayoutY(unitName.getLayoutY() + unitName.getFont().getSize() * 1.3 + 5);
        healthBar.setLayoutX(healthBarBackground.getLayoutX());
        healthBar.setLayoutY(healthBarBackground.getLayoutY());

        unitImage.setX(healthBarBackground.getLayoutX());
        unitImage.setY(healthBarBackground.getLayoutY() + healthBarBackground.getHeight());
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

    public void move(double dx, double dy, int dir) {
        double finalDX = x + dx * SPEED;
        double finalDY = y + dy * SPEED;
        setX(Math.max(Math.min(finalDX, MAX_X_UNIT), MIN_X_UNIT));
        setY(Math.max(Math.min(finalDY, MAX_Y_UNIT), MIN_Y_UNIT));
        if (dir != 0) {
            direction = dir;
            unitImage.setScaleX(direction);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void flipActivation() {
        if (active) unitImage.setEffect(shadow);
        else unitImage.setEffect(shadowActive);
        active = !active;
    }

    public boolean mouseIsActive(double mx, double my) {
        return unitImage.getBoundsInParent().contains(new Point2D(mx, my));
    }

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

    @Override
    public Newbie clone() {
        try {
            Newbie clone = (Newbie) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
