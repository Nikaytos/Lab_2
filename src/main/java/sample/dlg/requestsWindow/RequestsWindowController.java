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
    ListView<String> listView;
    @FXML
    ListView<String> listMacro;

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
            listView.getItems().clear();
            for (String name : Main.getWorld().getAllUnitsNames())
                listView.getItems().add(count++ + ". " + name);
            if (Main.getWorld().getAllUnitsNames().isEmpty()) {
                listView.getItems().add("No one");
            }
            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            container2.setVisible(true);
            request = "first";
        });
        secondButton.setOnAction(e -> {
            listView.getItems().clear();
            count = 1;
            for (String name : Main.getWorld().getMacrosNames())
                listView.getItems().add(count++ + ". " + name);
            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            container2.setVisible(true);
            request = "second";
        });
        thirdButton.setOnAction(e -> {
            listMacro.getItems().clear();
            count = 1;
            for (String name : Main.getWorld().getUnitsNames())
                listMacro.getItems().add(count++ + ". " + name);
            if (Main.getWorld().getUnits().isEmpty()) {
                listMacro.getItems().add("No one");
            }
            name.setText("Units that are not in macro");
            container1.setVisible(false);
            getWindow().setWidth(700);
            getWindow().setHeight(400);
            container2.setVisible(true);
            listView.setVisible(false);
            listMacro.setVisible(true);
            container3.setVisible(true);
            request = "third";
        });

        textField.setOnKeyTyped(e -> {
            switch (request) {
                case "first" -> setListViewText(Main.getWorld().getAllUnitsNames(), listView);
                case "second" -> {
                    if (listView.isVisible()) {
                        setListViewText(Main.getWorld().getMacrosNames(), listView);
                    } else {
                        if (macro.getUnitsIn() != null) {
                            setListViewText(macro.getNames(), listMacro);
                        }
                    }
                }
                case "third" -> setListViewText(Main.getWorld().getUnitsNames(), listMacro);
            }
        });
        okButton.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItem() != null && !listView.getSelectionModel().getSelectedItem().equals("[]")) {
                if (request.equals("first")) {
                    String[] strChoice = listView.getSelectionModel().getSelectedItem().split(". ");
                    Newbie unit = Main.getWorld().getAllUnits().get(Integer.parseInt(strChoice[0]) - 1);
                    name.setText(unit.getUnitName());
                    x.setText(String.valueOf(unit.getX()));
                    y.setText(String.valueOf(unit.getY()));
                    health.setText(String.valueOf(unit.getUnitHealth()));
                    team.setText(unit.getUnitTeam());
                    active.setText(String.valueOf(unit.isActive()));

                    lMacro.setText(unit.getInMacro());
                    if (lMacro.getText() == null) lMacro.setText("none");

                    listView.setVisible(false);
                    container3.setVisible(true);
                } else if (request.equals("second")) {
                    String[] strChoice = listView.getSelectionModel().getSelectedItem().split(". ");
                    macro = Main.getWorld().getMacros().get(Integer.parseInt(strChoice[0]) - 1);
                    name.setText("Units in " + macro.getName());

                    listMacro.getItems().clear();
                    count = 1;
                    if (macro.getUnitsIn() != null) {
                        if (macro.getUnitsIn().isEmpty())
                            listMacro.getItems().add("Macro is empty");
                        else for (String name : macro.getNames())
                            listMacro.getItems().add(count++ + ". " + name);
                    } else
                        listMacro.getItems().add("Macro is empty");
                    listMacro.setVisible(true);
                    listView.setVisible(false);
                    container3.setVisible(true);
                }
            }
        });
        back.setOnAction(e -> {
            if (request.equals("third")) {
                container2.setVisible(false);
                listView.setVisible(true);
                listMacro.setVisible(false);
                container3.setVisible(false);
                getWindow().setWidth(542);
                getWindow().setHeight(200);
                container1.setVisible(true);
                listMacro.getItems().clear();
                return;
            }

            if (!listView.isVisible()) {
                listView.setVisible(true);
                container3.setVisible(false);
                listMacro.setVisible(false);
            } else {
                container2.setVisible(false);
                getWindow().setWidth(542);
                getWindow().setHeight(200);
                container1.setVisible(true);
                listView.getItems().clear();
            }
        });
    }

    void setListViewText(ArrayList<String> list, ListView<String> listView) {
        listView.getItems().clear();

        if (request.equals("third") && Main.getWorld().getUnits().isEmpty()) {
            listView.getItems().add("No one");
            return;
        }

        if (request.equals("first") && Main.getWorld().getAllUnits().isEmpty()) {
            listView.getItems().add("No one");
            return;
        }

        if (listView == listMacro && macro.getUnitsIn().isEmpty()) {
            listMacro.getItems().add("Macro is empty");
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
