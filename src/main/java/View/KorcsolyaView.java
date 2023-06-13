package View;

import org.json.JSONArray;
import org.json.JSONObject;
public class KorcsolyaView {

    public static String showKorcsolyak(JSONArray korcsolyak) {
        String korcsolyakToString = "Nincsenek korcsolyáink jelenleg!";

        if(korcsolyak.length()!=0){
            korcsolyakToString = "A következő korcsolyáink vannak:\n\n";

            for(int i = 0; i < korcsolyak.length(); i++){
                JSONObject korcsolya = korcsolyak.getJSONObject(i);
                korcsolyakToString += showKorcsolya(korcsolya);
            }
        }

        return korcsolyakToString;
    }
    public static String showKorcsolya(JSONObject korcsolya){
        String korcsolyaToString = "";

        korcsolyaToString += "Típus: " + korcsolya.getString("tipus") + "\n";
        korcsolyaToString += "Méret: " + korcsolya.getString("meret") + "\n";
        korcsolyaToString += "Szín: " + korcsolya.getString("szin") + "\n\n";

        return korcsolyaToString;
    }
}