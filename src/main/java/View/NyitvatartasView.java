package View;

import org.json.JSONArray;
import org.json.JSONObject;

public class NyitvatartasView {

    public static String showNyitvatartasok(JSONArray nyitvatartasok) {
        String nyitvatartasokToString = "Nyitvatartásunk jelenleg átdolgozás alatt van!";

        if(nyitvatartasok.length()!=0){
            nyitvatartasokToString = "Pályánk a következő napokon és időpontokban van nyitva:\n\n";

            for(int i = 0; i < nyitvatartasok.length(); i++){
                JSONObject nyitvatartas = nyitvatartasok.getJSONObject(i);
                nyitvatartasokToString += showNyitvatartas(nyitvatartas);
            }
        }

        return nyitvatartasokToString;
    }
    public static String showNyitvatartas(JSONObject nyitvatartas){
        String nyitvatartasToString = "";
        nyitvatartasToString += nyitvatartas.getString("nap") + "\n";
        nyitvatartasToString += nyitvatartas.getString("nyitas") + " - " + nyitvatartas.getString("zaras") + "\n\n";


        return nyitvatartasToString;
    }
}
