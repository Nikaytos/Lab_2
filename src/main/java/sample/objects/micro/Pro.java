package sample.objects.micro;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import sample.Main;

import java.util.Objects;

import static sample.Main.getRandom;

public class Pro extends Enjoyer{

    public Pro(String name, double health, int iCoins, String team, int x, int y, boolean active) {
        super(name,  health,  iCoins ,  team, x, y, active);

        type = "Pro";

        unitName = new Label();
        Image img = new Image(Objects.requireNonNull(Main.class.getResource("images/pro.png")).toString(), IMAGE_SIZE, IMAGE_SIZE, false, true);
        unitImage = new ImageView(img);
        healthBar = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        healthBarBackground = new Rectangle(0, 0, IMAGE_SIZE, HEALTH_HEIGHT);
        coins = new Coins(iCoins);
        unitContainer = new Group();
        shadow = new DropShadow();
        shadowActive = new DropShadow();

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

    public Pro() {
        this(NAMES[getRandom().nextInt(NAMES.length)],
                defaultValueHealth(),
                defaultValueCoins(),
                TEAMS[getRandom().nextInt(TEAMS.length)],
                defaultValueX(),
                defaultValueY(),
                false);
        System.out.print("Random pro appeared: " + this + "\n");
    }

    @Override
    public void heal() {
        double healthNew = getUnitHealth() + 3;
        if (healthNew < MAX_HEALTH) {
            setUnitHealth(healthNew);
        }
        else setUnitHealth((double) MAX_HEALTH);
    }

    @Override
    public boolean takeDamage() {
        double healthNew = getUnitHealth() - 1;
        if (healthNew > MIN_HEALTH) {
            setUnitHealth(healthNew);
            return false;
        }
        else {
            setUnitHealth((double) MIN_HEALTH);
            return true;
        }
    }

    @Override
    public String toString() {
        return "Pro{" +
                "name=" + getUnitName() +
                ", health=" + unitHealth +
                ", coins=" + coins.getCoinsCount() +
                ", team=" + unitTeam +
                ", x=" + x +
                ", y=" + y +
                ", active=" + active +
                '}';
    }
}
