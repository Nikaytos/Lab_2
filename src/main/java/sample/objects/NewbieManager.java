package sample.objects;

import sample.objects.Newbie;

import java.util.ArrayList;

public class NewbieManager {
//---------------------------------------------------------
        public static ArrayList<Newbie> newbies = new ArrayList<>();
//---------------------------------------------------------
        public static ArrayList<String> getParamsToChange( int index ){
            Newbie n = newbies.get(index);
            ArrayList<String> arr= new ArrayList<>();
            arr.add( n.getName() );
            arr.add( n.getHealth() );
            arr.add( n.getTeam() );
            arr.add( n.getX() );
            arr.add( n.getY() );
            return arr;
        }
//---------------------------------------------------------
        public static ArrayList<String> getNames() {
            ArrayList<String> arr = new ArrayList<>();
            newbies.forEach(n -> arr.add(n.toString()));
            return arr;
        }
}
