package sample;

import sample.objects.macro.Macro;
import sample.objects.micro.Enjoyer;
import sample.objects.micro.Newbie;
import sample.objects.micro.Pro;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Serialization {

    public static void serializeNow(File file) {
        XMLEncoder encoder;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));

            List<Newbie> units = Main.getWorld().getUnits();
            List<Map<String, Object>> unitMaps = new ArrayList<>();
            for (Newbie unit : units) {
                Map<String, Object> unitMap = new HashMap<>();
                unitMap.put("type", unit.getType());
                unitMap.put("name", unit.getUnitName());
                unitMap.put("health", unit.getUnitHealth());
                unitMap.put("coins", unit.getIntCoins());
                unitMap.put("team", unit.getUnitTeam());
                unitMap.put("x", unit.getX());
                unitMap.put("y", unit.getY());
                unitMap.put("active", unit.isActive());
                unitMap.put("direction", unit.getDirection());
                unitMaps.add(unitMap);
            }

            List<Integer> coins = new ArrayList<>();
            coins.add(Main.getWorld().getMacros().get(0).getCoins());
            coins.add(Main.getWorld().getMacros().get(1).getCoins());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("units", unitMaps);
            hashMap.put("macros", Main.getWorld().getMacros());
            hashMap.put("coins", coins);
            encoder.writeObject(hashMap);
            encoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }

    public static void deserializeNow(File file){
        XMLDecoder decoder;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));

            for (int i = 0; i<Main.getWorld().getUnits().size(); i++){
                Newbie unit = Main.getWorld().getUnits().get(i--);
                Main.getWorld().deleteUnit(unit);
            }
            for (int i = 0; i<Main.getWorld().getMacros().size(); i++){
                Macro macro = Main.getWorld().getMacros().get(i--);
                Main.getWorld().deleteMacro(macro);
            }

            System.out.println();

            HashMap<String, Object> hashMap = (HashMap<String, Object>)decoder.readObject();

            List<Integer> coins = new ArrayList<>((ArrayList<Integer>) hashMap.get("coins"));
            for (Macro macro : (ArrayList<Macro>)hashMap.get("macros")){
                Main.getWorld().addNewMacro(macro);
            }
            Main.getWorld().getMacros().get(0).setCoins(coins.get(0));
            Main.getWorld().getMacros().get(1).setCoins(coins.get(1));

            ArrayList<HashMap<String, Object>> unitMaps = (ArrayList<HashMap<String, Object>>)hashMap.get("units");
            for (HashMap<String, Object> unitMap : unitMaps) {
                String type = (String)unitMap.get("type");
                Newbie newbie;
                Enjoyer enjoyer;
                Pro pro;
                switch (type) {
                    case "Newbie" -> {
                        newbie = new Newbie((String) unitMap.get("name"), (Double) unitMap.get("health"), (Integer) unitMap.get("coins"), (String) unitMap.get("team"), (Integer) unitMap.get("x"), (Integer) unitMap.get("y"), (Boolean) unitMap.get("active"));
                        newbie.setActive((Boolean) unitMap.get("active"));
                        newbie.setDirection((Integer)unitMap.get("direction"));
                        Main.getWorld().addNewUnit(newbie);
                    }
                    case "Enjoyer" -> {
                        enjoyer = new Enjoyer((String) unitMap.get("name"), (Double) unitMap.get("health"), (Integer) unitMap.get("coins"), (String) unitMap.get("team"), (Integer) unitMap.get("x"), (Integer) unitMap.get("y"), (Boolean) unitMap.get("active"));
                        enjoyer.setActive((Boolean) unitMap.get("active"));
                        enjoyer.setDirection((Integer)unitMap.get("direction"));
                        Main.getWorld().addNewUnit(enjoyer);
                    }
                    case "Pro"  -> {
                        pro = new Pro((String) unitMap.get("name"), (Double) unitMap.get("health"), (Integer) unitMap.get("coins"), (String) unitMap.get("team"), (Integer) unitMap.get("x"), (Integer) unitMap.get("y"), (Boolean) unitMap.get("active"));
                        pro.setActive((Boolean) unitMap.get("active"));
                        pro.setDirection((Integer)unitMap.get("direction"));
                        Main.getWorld().addNewUnit(pro);
                    }
                }
            }

            decoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Помилка відкриття файлу");
        }
    }
}
