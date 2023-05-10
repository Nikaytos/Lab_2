package sample.dlg.requestsWindow;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.Main;
import sample.objects.macro.Macro;
import sample.objects.micro.Newbie;

import java.util.ArrayList;

import static sample.dlg.requestsWindow.RequestsWindow.getWindow;

public class RequestsWindowController {


    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;
    @FXML
    Button fourthButton;
    @FXML
    Button back;
    @FXML
    Button okButton;

    @FXML
    TextField textField;

    @FXML
    ListView<String> listFirst;
    @FXML
    ListView<String> listSecond;

    @FXML
    GridPane container1;
    @FXML
    GridPane container2;
    @FXML
    GridPane container3;

    @FXML
    Label name;
    @FXML
    Label x;
    @FXML
    Label y;
    @FXML
    Label health;
    @FXML
    Label team;
    @FXML
    Label active;
    @FXML
    Label lMacro;

    String request;
    int count;
    ArrayList<Newbie> allUnits;
    Macro macro;

    @FXML
    void initialize() {
        allUnits = new ArrayList<>();

        firstButton.setOnMouseEntered(e -> firstButton.setTooltip(new Tooltip("Знайти мікрооб’єкт з вказаними параметрами")));
        secondButton.setOnMouseEntered(e -> secondButton.setTooltip(new Tooltip("Видати перелік мікрооб’єктів, які належать вказаному макрооб’єкту")));
        thirdButton.setOnMouseEntered(e -> thirdButton.setTooltip(new Tooltip("Видати перелік юнітів, які не належать жодному макрооб’єкту")));
        fourthButton.setOnMouseEntered(e -> fourthButton.setTooltip(new Tooltip("Запити по підрахуванню мікрооб'єктів")));

        firstButton.setOnAction(e -> {
            count = 1;
            listFirst.getItems().clear();
            for (String name : Main.getWorld().getAllUnitsNames())
                listFirst.getItems().add(count++ + ". " + name);
            if (Main.getWorld().getAllUnitsNames().isEmpty()) {
                listFirst.getItems().add("No one");
            }
            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            container2.setVisible(true);
            request = "first";
        });
        secondButton.setOnAction(e -> {
            listFirst.getItems().clear();
            count = 1;
            for (String name : Main.getWorld().getMacrosNames())
                listFirst.getItems().add(count++ + ". " + name);
            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            container2.setVisible(true);
            request = "second";
        });
        thirdButton.setOnAction(e -> {
            listSecond.getItems().clear();
            count = 1;
            for (String name : Main.getWorld().getUnitsNames())
                listSecond.getItems().add(count++ + ". " + name);
            if (Main.getWorld().getUnits().isEmpty()) {
                listSecond.getItems().add("Немає");
            }
            name.setText("Юніти, які не макрооб’єктах");

            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            okButton.setVisible(false);
            container2.setVisible(true);
            listFirst.setVisible(false);
            listSecond.setVisible(true);
            container3.setVisible(true);

            request = "third";
        });
        fourthButton.setOnAction(e -> {
            name.setText("Запити по підрахуванню мікрооб'єктів");

            listSecond.getItems().clear();
            count = 0;
            for (Newbie unit : Main.getWorld().getAllUnits())
                if (unit.isActive()) count++;
            listSecond.getItems().add("Кількість активних мікрооб'єктів: " + count);
            count = 0;
            for (Newbie unit : Main.getWorld().getAllUnits())
                if (unit.getUnitHealth() > Newbie.MAX_HEALTH/2.0) count++;
            listSecond.getItems().add("Кількість мікрооб'єктів з рівнем життя більше половини: " + count);
            count = 0;
            for (Newbie unit : Main.getWorld().getAllUnits())
                if (unit.getUnitTeam().equals("GOOD")) count++;
            listSecond.getItems().add("Кількість мікрооб'єктів альянсу Добрих: " + count);
            count = 0;
            for (Newbie unit : Main.getWorld().getAllUnits())
                if (unit.getUnitTeam().equals("BAD")) count++;
            listSecond.getItems().add("Кількість мікрооб'єктів лагерю Поганих: " + count);
            count = 0;
            for (Newbie unit : Main.getWorld().getAllUnits())
                if (unit.getInMacro().equals("Base Good")) count++;
            listSecond.getItems().add("Кількість мікрооб'єктів, які належать макрооб'єкту Base Good: " + count);
            count = 0;
            for (Newbie unit : Main.getWorld().getAllUnits())
                if (unit.getInMacro().equals("Base Bad")) count++;
            listSecond.getItems().add("Кількість мікрооб'єктів, які належать макрооб'єкту Base Bad: " + count);

            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            okButton.setVisible(false);
            container2.setVisible(true);
            listFirst.setVisible(false);
            listSecond.setVisible(true);
            container3.setVisible(true);

            request = "fourth";
        });

        textField.setOnKeyTyped(e -> {
            switch (request) {
                case "first" -> setListViewText(Main.getWorld().getAllUnitsNames(), listFirst);
                case "second" -> {
                    if (listFirst.isVisible()) {
                        setListViewText(Main.getWorld().getMacrosNames(), listFirst);
                    } else {
                        if (macro.getUnitsIn() != null) {
                            setListViewText(macro.getNames(), listSecond);
                        }
                    }
                }
                case "third" -> setListViewText(Main.getWorld().getUnitsNames(), listSecond);
            }
        });
        okButton.setOnAction(e -> {
            if (listFirst.getSelectionModel().getSelectedItem() != null && !listFirst.getSelectionModel().getSelectedItem().equals("[]")) {
                if (request.equals("first")) {
                    String[] strChoice = listFirst.getSelectionModel().getSelectedItem().split(". ");
                    Newbie unit = Main.getWorld().getAllUnits().get(Integer.parseInt(strChoice[0]) - 1);
                    name.setText(unit.getUnitName());
                    x.setText(String.valueOf(unit.getX()));
                    y.setText(String.valueOf(unit.getY()));
                    health.setText(String.valueOf(unit.getUnitHealth()));
                    team.setText(unit.getUnitTeam());
                    active.setText(String.valueOf(unit.isActive()));

                    lMacro.setText(unit.getInMacro());
                    if (lMacro.getText() == null) lMacro.setText("null");

                    listFirst.setVisible(false);
                    container3.setVisible(true);
                } else if (request.equals("second")) {
                    String[] strChoice = listFirst.getSelectionModel().getSelectedItem().split(". ");
                    macro = Main.getWorld().getMacros().get(Integer.parseInt(strChoice[0]) - 1);
                    name.setText("Юніти в " + macro.getName());

                    listSecond.getItems().clear();
                    count = 1;
                    if (macro.getUnitsIn() != null) {
                        if (macro.getUnitsIn().isEmpty())
                            listSecond.getItems().add("Макрооб'єкт пустий");
                        else for (String name : macro.getNames())
                            listSecond.getItems().add(count++ + ". " + name);
                    } else
                        listSecond.getItems().add("Макрооб'єкт пустий");
                    listSecond.setVisible(true);
                    listFirst.setVisible(false);
                    container3.setVisible(true);
                }
            }
        });
        back.setOnAction(e -> {
            if (request.equals("third") || request.equals("fourth")) {
                okButton.setVisible(true);
                container2.setVisible(false);
                listFirst.setVisible(true);
                listSecond.setVisible(false);
                container3.setVisible(false);
                getWindow().setWidth(542);
                getWindow().setHeight(200);
                container1.setVisible(true);
                listSecond.getItems().clear();
                return;
            }

            if (!listFirst.isVisible()) {
                listFirst.setVisible(true);
                container3.setVisible(false);
                listSecond.setVisible(false);
            } else {
                container2.setVisible(false);
                getWindow().setWidth(542);
                getWindow().setHeight(200);
                container1.setVisible(true);
                listFirst.getItems().clear();
            }
        });
    }

    void setListViewText(ArrayList<String> list, ListView<String> listView) {
        listView.getItems().clear();

        if (request.equals("third") && Main.getWorld().getUnits().isEmpty()) {
            listView.getItems().add("Немає");
            return;
        }

        if (request.equals("first") && Main.getWorld().getAllUnits().isEmpty()) {
            listView.getItems().add("Немає");
            return;
        }

        if (listView == listSecond && macro.getUnitsIn().isEmpty()) {
            listSecond.getItems().add("Макрооб'єкт пустий");
            return;
        }

        ArrayList<String> tmp = new ArrayList<>();
        for (String name : list)
            if (name.contains(textField.getCharacters()))
                tmp.add(name);
        count = 1;
        for (String name : tmp)
            listView.getItems().add(count++ + ". " + name);

    }
}