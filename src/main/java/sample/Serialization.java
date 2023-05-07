package sample;

import sample.objects.macro.Macro;
import sample.objects.micro.Newbie;

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
                unitMap.put("name", unit.getUnitName());
                unitMap.put("health", unit.getUnitHealth());
                unitMap.put("team", unit.getUnitTeam());
                unitMap.put("x", unit.getX());
                unitMap.put("y", unit.getY());
                unitMap.put("active", unit.isActive());
                unitMap.put("order", unit.isOrder());
                unitMap.put("bigTarget", unit.getBigTarget());
                unitMap.put("direction", unit.getDirection());
                unitMaps.add(unitMap);
            }

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("units", unitMaps);
            hashMap.put("macros", Main.getWorld().getMacros());
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

            for (Macro macro : (ArrayList<Macro>)hashMap.get("macros")){
                Main.getWorld().addNewMacro(macro);
            }
            ArrayList<HashMap<String, Object>> unitMaps = (ArrayList<HashMap<String, Object>>)hashMap.get("units");
            for (HashMap<String, Object> unitMap : unitMaps) {
                Newbie unit = new Newbie((String)unitMap.get("name"), (Double) unitMap.get("health"), (String)unitMap.get("team"), (Integer)unitMap.get("x"), (Integer)unitMap.get("y"), (Boolean)unitMap.get("active"));
                unit.setOrder((Boolean)unitMap.get("order"));
                unit.setBigTarget((Macro) unitMap.get("bigTarget"));
                unit.setDirection((Integer)unitMap.get("direction"));
                Main.getWorld().addNewUnit(unit);
            }
            decoder.close();
        } catch (FileNotFoundException e) {
            System.out.println("Помилка відкриття файлу");
        }
    }
}
