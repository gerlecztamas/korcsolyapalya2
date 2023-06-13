package View;

import org.json.JSONArray;
import org.json.JSONObject;

public class JegyView {

    public static String showJegyek(JSONArray jegyek) {
        String jegyekToString = "Jegyeink jelenleg átdolgozás alatt vannak!";

        if(jegyek.length()!=0){
            jegyekToString = "A következő jegyeink vannak:\n\n";

            for(int i = 0; i < jegyek.length(); i++){
                JSONObject jegy = jegyek.getJSONObject(i);
                jegyekToString += showJegy(jegy);
            }
        }

        return jegyekToString;
    }
    public static String showJegy(JSONObject jegy){
        String jegyToString = "";
        jegyToString += "Típus: " + jegy.getString("nev") + "\n";
        jegyToString += "Ár: " + jegy.getString("ar") + " Ft\n";
        if (jegy.length() == 3) {
            jegyToString += "Megjegyzés: " + jegy.getString("megjegyzes") + "\n\n";
            return jegyToString;
        }
        jegyToString += "\n";

        return jegyToString;
    }
}
