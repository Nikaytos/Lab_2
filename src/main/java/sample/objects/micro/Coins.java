package sample.objects.micro;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Coins implements Cloneable {
    private Label count;

    public Coins (int f, int x, int y) {
        String str= Integer.toString(f);

        count = new Label(str);
        count.setTextFill(Color.YELLOW);

        setCoordinates(x, y);
    }

    public Coins (int f) {
        String str= Integer.toString(f);

        count = new Label(str);
        count.setTextFill(Color.YELLOW);
    }

    public void setCoordinates(int x, int y){
        count.setLayoutX(x);
        count.setLayoutY(y);
    }
    public void setCount(Label count) {
        this.count = count;
    }

    public Label getCount() {
        return count;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        String str = count.getText();
        int f = Integer.parseInt(str);
        int x= (int) count.getLayoutX();
        int y= (int) count.getLayoutY();

        Coins tmp = new Coins(f, x, y);
        return tmp;
    }
}
