package sample.objects.micro;

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
import java.util.Comparator;
import java.util.Objects;

import static javafx.util.Duration.millis;
import static javafx.util.Duration.seconds;

import sample.Main;
import sample.objects.SeaOfThieves;
import sample.objects.macro.Macro;

import static sample.Main.getRandom;

public class Newbie implements Cloneable, Comparable<Newbie> {

    protected static final double IMAGE_SIZE = 100 * SeaOfThieves.SIZE;
    protected static final double HEALTH_HEIGHT = 5 * SeaOfThieves.SIZE;
    protected static final double FONT_SIZE = 14 * SeaOfThieves.SIZE;
    protected static final Rectangle2D.Double UNIT_CONTAINER_BOUNDS = new Rectangle2D.Double(0, 0, IMAGE_SIZE, IMAGE_SIZE + HEALTH_HEIGHT + FONT_SIZE * 1.3 + 5);
    protected static final Point MAX_UNIT = new Point((int) (SeaOfThieves.MAX_X - 5 - UNIT_CONTAINER_BOUNDS.width), (int) (SeaOfThieves.MAX_Y - 5 - UNIT_CONTAINER_BOUNDS.height));
    protected static final Point MIN_UNIT = new Point(5, 5);

    public static final int MAX_LENGTH_NAME = 7;
    public static final int MAX_HEALTH = 100;
    public static final int MIN_HEALTH = 1;
    public static final int MAX_COINS = 1000;
    public static final int MIN_COINS = 1;

    public static final String[] TYPES = {"Newbie", "Enjoyer", "Pro"};
    protected static final String[] TEAMS = {"GOOD", "BAD"};
    protected static final String[] NAMES = {"Adam", "Brandon", "Charles", "David", "Ethan", "Frank", "George", "Henry",
            "Isaac", "John", "Kevin", "Liam", "Matthew", "Nathan", "Oliver", "Peter", "Quentin",
            "Robert", "Samuel", "Thomas", "Ulysses", "Victor", "William", "Xavier", "Yves", "Zachary"};

    protected static double defaultValueHealth() {
        return getRandom().nextInt(MAX_HEALTH - MIN_HEALTH + 1) + MIN_HEALTH;
    }

    protected static int defaultValueCoins() {
        return getRandom().nextInt(MAX_COINS - MIN_COINS + 1) + MIN_COINS;
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
    protected Coins coins;
    protected int x;
    protected int y;
    protected final int speed = 5;
    protected ImageView unitImage;
    protected Rectangle healthBar;
    protected Rectangle healthBarBackground;
    protected DropShadow shadow;
    protected DropShadow shadowActive;
    protected boolean active;
    protected boolean processing;
    protected int direction;
    protected int aimx;
    protected int aimy;
    protected Macro bigTarget;

    public Group getUnitContainer() {
        return unitContainer;
    }

    public String getUnitName() {
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

    public int getDirection() {
        return direction;
    }

    public boolean isActive() {
        return active;
    }

    public static Point getMAX_UNIT() {
        return MAX_UNIT;
    }

    public static Point getMIN_UNIT() {
        return MIN_UNIT;
    }

    public int getIntCoins() {
        return coins.getCoinsCount();
    }

    public String getType() {
        return type;
    }

    public DropShadow getShadow() {
        return shadow;
    }

    public DropShadow getShadowActive() {
        return shadowActive;
    }

    public boolean isEmptyAim() {
        return (aimx < 0) && (aimy < 0);
    }

    public void setAim(int ax, int ay) {
        aimx = ax;
        aimy = ay;
    }

    public void setBigTarget(Macro macro) {
        bigTarget = macro;
    }

    public void clearAim() {
        aimx = aimy = -1000;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public void setCoinsCount(String count) {
        this.coins.getCount().setText(count);
    }

    public void setUnitName(String name) {
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

    public void setUnitTeam(String team) {
        unitTeam = team;
        if (team.equals("GOOD")) {
            shadow.setColor(Color.BLUE);
        } else if (team.equals("BAD")) {
            shadow.setColor(Color.RED);
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

    public void setDirection(int direction) {
        this.direction = direction;
        unitImage.setScaleX(direction);
    }

    public void setActive(boolean active) {
        if (!active) {
            unitImage.setEffect(shadow);
        } else unitImage.setEffect(shadowActive);
        this.active = active;
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

        coins.setCoordinates((int) (healthBarBackground.getLayoutX() + healthBarBackground.getWidth() + 5), (int) healthBarBackground.getLayoutY());
    }

    public Newbie(String name, double health, int iCoins, String team, int x, int y, boolean active) {

        type = "Newbie";

        unitName = new Label();
        Image img = new Image(Objects.requireNonNull(Main.class.getResource("images/newbie.png")).toString(), IMAGE_SIZE, IMAGE_SIZE, false, true);
        unitImage = new ImageView(img);
        healthBar = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        healthBarBackground = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        coins = new Coins(iCoins);
        unitContainer = new Group();
        shadow = new DropShadow();
        shadowActive = new DropShadow();
        processing = false;

        setUnitName(name);
        setUnitHealth(health);
        setUnitTeam(team);
        initialize();
        setX(x);
        setY(y);
        clearAim();
        setActive(active);

        spawnTransition();

        unitContainer.getChildren().addAll(unitName, healthBarBackground, healthBar, unitImage, coins.getCount());
    }

    public Newbie() {
        this(NAMES[getRandom().nextInt(NAMES.length)],
                defaultValueHealth(),
                defaultValueCoins(),
                TEAMS[getRandom().nextInt(TEAMS.length)],
                defaultValueX(),
                defaultValueY(),
                false);
        System.out.print("Random newbie appeared: " + this + "\n");
    }

    static {
        System.out.println("Та нехай почнеться битва!");
    }

    {
        System.out.println("Ласкаво просимо до світу піратів!");
    }


    protected void initialize() {
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

    public static double parseValue(String value, double defaultValue, double minValue, double maxValue) {
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

    public static String parseTeam(String sTeam) {
        String defaultValue = TEAMS[getRandom().nextInt(TEAMS.length)];
        try {
            if (sTeam.equals("")) {
                return defaultValue;
            }
            return sTeam;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void createNewUnit(String cType, String sName, String sHealth, String sCoins, String cTeam, String sX, String sY, boolean active) {
        Newbie newbie;
        Enjoyer enjoyer;
        Pro pro;

        String name = limitString(sName, NAMES[getRandom().nextInt(NAMES.length)]);
        double h = parseValue(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH);
        int coins = (int) parseValue(sCoins, defaultValueCoins(), MIN_COINS, MAX_COINS);
        String team = parseTeam(cTeam);
        int x = (int) parseValue(sX, defaultValueX(), getMIN_UNIT().x, getMAX_UNIT().x);
        int y = (int) parseValue(sY, defaultValueY(), getMIN_UNIT().y, getMAX_UNIT().y);

        if (cType == null || (!cType.equals("Newbie") && !cType.equals("Enjoyer") && !cType.equals("Pro"))) {
            cType = TYPES[getRandom().nextInt(TYPES.length)];
        }

        switch (cType) {
            case "Newbie" -> {
                newbie = new Newbie(name, h, coins, team, x, y, active);
                System.out.println(newbie);
                Main.getWorld().addNewUnit(newbie);
            }
            case "Enjoyer" -> {
                enjoyer = new Enjoyer(name, h, coins, team, x, y, active);
                System.out.println(enjoyer);
                Main.getWorld().addNewUnit(enjoyer);
            }
            case "Pro" -> {
                pro = new Pro(name, h, coins, team, x, y, active);
                System.out.println(pro);
                Main.getWorld().addNewUnit(pro);
            }
        }
    }

    public static void changeUnit(int unitIndex, String cType, String sName, String sHealth, String sCoins, String cTeam, String sX, String sY, boolean active) {
        Newbie newbie;
        Enjoyer enjoyer;
        Pro pro;

        String s1 = Main.getWorld().getUnits().get(unitIndex).toString();

        String name = limitString(sName, NAMES[getRandom().nextInt(NAMES.length)]);
        double h = parseValue(sHealth, defaultValueHealth(), MIN_HEALTH, MAX_HEALTH);
        int coins = (int) parseValue(sCoins, defaultValueCoins(), MIN_COINS, MAX_COINS);
        String team = parseTeam(cTeam);
        int x = (int) parseValue(sX, defaultValueX(), getMIN_UNIT().x, getMAX_UNIT().x);
        int y = (int) parseValue(sY, defaultValueY(), getMIN_UNIT().y, getMAX_UNIT().y);

        String s2 = null;

        switch (cType) {
            case "Newbie" -> {
                newbie = new Newbie(name, h, coins, team, x, y, active);
                Main.getWorld().deleteUnit(Main.getWorld().getUnits().get(unitIndex));
                Main.getWorld().addNewUnit(newbie);
                s2 = newbie.toString();
            }
            case "Enjoyer" -> {
                enjoyer = new Enjoyer(name, h, coins, team, x, y, active);
                Main.getWorld().deleteUnit(Main.getWorld().getUnits().get(unitIndex));
                Main.getWorld().addNewUnit(enjoyer);
                s2 = enjoyer.toString();
            }
            case "Pro" -> {
                pro = new Pro(name, h, coins, team, x, y, active);
                Main.getWorld().deleteUnit(Main.getWorld().getUnits().get(unitIndex));
                Main.getWorld().addNewUnit(pro);
                s2 = pro.toString();
            }
        }

        if (!s1.equals(s2)) System.out.println("Edited:\n" + s1 + "\nto:\n" + s2);
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

    public void flipActivation() {
        if (active) {
            unitImage.setEffect(shadow);
        } else unitImage.setEffect(shadowActive);
        active = !active;
    }

    public boolean mouseIsOn(double mx, double my) {
        return unitImage.getBoundsInParent().contains(new Point2D(mx, my));
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
        if (x < this.x - speed) {
            dx = -speed;
            dir = 1;
        } else if (x > this.x + speed) {
            dx = speed;
            dir = -1;
        }
        if (y > this.y + speed) {
            dy = speed;
        } else if (y < this.y - speed) {
            dy = -speed;
        }
        move(dx, dy, dir);
    }

    public void autoMove() {

        if (active) return;

        if (processing) return;

        if (isEmptyAim()) {
            Main.getWorld().askWorldwhatToDo(this);
        } else {
            if (bigTarget.getMacroContainer().getBoundsInParent().contains(getUnitContainer().getBoundsInParent().getCenterX(), getUnitContainer().getBoundsInParent().getCenterY())) {
                clearAim();
            } else simpleMove(aimx, aimy);
        }
    }

    public void heal() {
        double healthNew = getUnitHealth() + 1;
        if (healthNew < MAX_HEALTH) {
            setUnitHealth(healthNew);
        } else setUnitHealth((double) MAX_HEALTH);
    }

    public boolean takeDamage(double damage) {
        double healthNew = getUnitHealth() - damage;
        if (healthNew > MIN_HEALTH) {
            setUnitHealth(healthNew);
            return false;
        } else {
            setUnitHealth((double) MIN_HEALTH);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Newbie{" +
                "name=" + getUnitName() +
                ", health=" + unitHealth +
                ", coins=" + coins.getCoinsCount() +
                ", team=" + unitTeam +
                ", x=" + x +
                ", y=" + y +
                ", active=" + active +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Newbie clone = (Newbie) super.clone();

        clone.coins = (Coins) this.coins.clone();

        clone.unitName = new Label();
        clone.unitImage = new ImageView(this.unitImage.getImage());
        clone.unitImage.setScaleX(this.unitImage.getScaleX());
        clone.healthBar = new Rectangle(0, 0, this.healthBar.getWidth(), this.healthBar.getHeight());
        clone.healthBarBackground = new Rectangle(0, 0, this.healthBarBackground.getWidth(), this.healthBarBackground.getHeight());
        clone.unitContainer = new Group();
        clone.shadow = new DropShadow();
        clone.shadowActive = new DropShadow();
        clone.setUnitName(this.getUnitName() + "-cl");
        clone.setUnitHealth(this.getUnitHealth());
        clone.setUnitTeam(this.getUnitTeam());
        clone.direction = direction;

        clone.initialize();

        clone.flipActivation();
        clone.flipActivation();

        clone.setX((int) parseValue(Double.toString(this.getX() + this.getUnitImage().getLayoutBounds().getMaxX() + 10), this.getX(), MIN_UNIT.x, MAX_UNIT.x));

        clone.spawnTransition();

        clone.unitContainer.getChildren().addAll(clone.unitName, clone.healthBarBackground, clone.healthBar, clone.unitImage, clone.coins.getCount());
        return clone;
    }

    public int compareTo(Newbie o) {
        return this.unitName.getText().compareTo(o.unitName.getText());
    }

    public static class HealthComparator implements Comparator<Newbie> {
        @Override
        public int compare(Newbie f1, Newbie f2) {
            return Double.compare(f2.unitHealth, f1.unitHealth);
        }
    }
}
