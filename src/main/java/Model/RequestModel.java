package Model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalTime;


public class RequestModel {

    public static String kolcsonzes(JSONObject igeny){
        String result = "Nincs az igényeknek megfelelő korcsolya!";

        if(igeny.getString("tipus").equals("") || igeny.getString("meret").equals("") || igeny.getString("vezeteknev").equals("") || igeny.getString("keresztnev").equals("") || igeny.getString("email").equals("") || igeny.getString("datum").equals("")){
            return "Minden adatmezőt ki kell töltenie!";
        }

        if(LocalDate.parse(igeny.getString("datum")).isBefore(LocalDate.now().plusDays(1))){
            return "Minimum egy nappal előre szükséges a korcsolya kölcsönzési igényeket leadni!";
        }

        JSONArray korcsolyak = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyak.xml");
        JSONArray kolcsonzottek = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyakolcsonzes.xml");

        for(int i = 0; i < korcsolyak.length(); i++){
            JSONObject korcsolya = korcsolyak.getJSONObject(i);
            try{
                if(korcsolya.getString("meret").equals(igeny.getString("meret")) &&
                        korcsolya.getString("tipus").equals(igeny.getString("tipus"))){

                    if(kolcsonzottek.length() == 0){
                        result = makeKolcsonzes(korcsolya, igeny, kolcsonzottek);
                        return result;
                    }
                    else {
                        boolean szabad = true;
                        for(int j = 0; j < kolcsonzottek.length(); j++){
                            JSONObject kolcsonzottKorcsolya = kolcsonzottek.getJSONObject(j);

                            if(kolcsonzottKorcsolya.getString("korcsolyaId").equals(korcsolya.getString("id")) &&
                                    kolcsonzottKorcsolya.getString("datum").equals(igeny.getString("datum"))){
                                result = "Már minden korcsolya ezzel a paraméterrel ki van kölcsönözve az adott dátumra!";
                                szabad = false;
                            }

                        }
                        if(szabad){
                            result = makeKolcsonzes(korcsolya, igeny, kolcsonzottek);
                            return result;
                        }

                    }
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                result = "Hibás bemenő paraméterek!";
                return result;
            }
        }
        return result;
    }

    public static String makeKolcsonzes(JSONObject korcsolya, JSONObject igeny, JSONArray kolcsonzottek){
        String result;
        Korcsolya kolcsonzott = new Korcsolya(Integer.valueOf(korcsolya.getString("id")),
                KorcsolyaTipusEnum.valueOf(korcsolya.getString("tipus")), Integer.valueOf(korcsolya.getString("meret")),
                korcsolya.getString("szin"));

        Kolcsonzes kolcsonzes = new Kolcsonzes(kolcsonzottek.length(), igeny.getString("keresztnev"),
                igeny.getString("vezeteknev"), igeny.getString("email"), Integer.valueOf(korcsolya.getString("id")),
                LocalDate.parse(igeny.getString("datum")));
        kolcsonzes.writer();

        result = "A következő korcsolyát sikeresen kikölcsönözte: \n"+ kolcsonzott + "\naz alábbi napra: "
                + kolcsonzes.getDatum().toString() + " (" + kolcsonzes.getDatum().getDayOfWeek() + ")";

        return result;
    }

    public static String foglalas(JSONObject igeny){
        String result;

        if(igeny.getString("kezdet").equals("") || igeny.getString("veg").equals("") || igeny.getString("vezeteknev").equals("") || igeny.getString("keresztnev").equals("") || igeny.getString("email").equals("") || igeny.getString("datum").equals("")){
            return "Minden adatmezőt ki kell töltenie!";
        }

        if(LocalTime.parse(igeny.getString("veg")).isBefore(LocalTime.parse(igeny.getString("kezdet")))){
            return "A pályafoglalás befejezése nem lehet a kezdete előtti időpont!";
        }

        if(LocalTime.parse("23:00").isBefore(LocalTime.parse(igeny.getString("veg")))){
            return "23:00-nál tovább nem tart nyitva a pálya!";
        }

        if(LocalTime.parse(igeny.getString("kezdet")).isBefore(LocalTime.parse("06:00"))){
            return "06:00-nál előbb nem tart nyitva a pálya!";
        }

        if(LocalDate.parse(igeny.getString("datum")).isBefore(LocalDate.now().plusDays(1))){
            return "Minimum egy nappal előre szükséges a pályafoglalási igényeket leadni!";
        }

        JSONArray foglalasok = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\palyafoglalas.xml");

        try {
            for (int i = 0; i < foglalasok.length(); i++) {
                JSONObject foglalas = foglalasok.getJSONObject(i);

                //x1 <= y2 && y1 <= x2
                if (LocalDate.parse(igeny.getString("datum")).isEqual(LocalDate.parse(foglalas.getString("datum")))) {
                    if (LocalTime.parse(igeny.getString("kezdet")).isBefore(LocalTime.parse(foglalas.getString("veg")))
                            && LocalTime.parse(foglalas.getString("kezdet")).isBefore(LocalTime.parse(igeny.getString("veg")))) {
                        result = "Az adott időpontra már foglalt a pálya!";
                        return result;
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            result = "Hibás bemenő paraméterek!";
            return result;
        }

        result = makeFoglalas(igeny, foglalasok);
        return result;
    }

    public static String makeFoglalas(JSONObject igeny, JSONArray foglalasok){
        String result;

        Foglalas foglalas = new Foglalas(foglalasok.length(), igeny.getString("vezeteknev"),
                igeny.getString("keresztnev"), igeny.getString("email"),
                LocalDate.parse(igeny.getString("datum")), LocalTime.parse(igeny.getString("kezdet")),
                LocalTime.parse(igeny.getString("veg")));
        foglalas.writer();

        result = "Sikeresen lefoglalta a pályát " + foglalas + "!\n";

        return result;
    }

    public static String addKorcsolya(JSONObject korcsolya){
        String result;

        if(korcsolya.getString("tipus").equals("") || korcsolya.getString("meret").equals("") || korcsolya.getString("szin").equals("")){
            return "Minden adatmezőt ki kell töltenie!";
        }

        try {
            JSONArray korcsolyak = XmlReader.read(System.getProperty("user.home") +
                    "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyak.xml");
            String tipus = korcsolya.getString("tipus");
            ArrayList<String> tipusLista = new ArrayList<>();
            for (KorcsolyaTipusEnum tipusEnum : KorcsolyaTipusEnum.values()) {
                String tipusString = tipusEnum.name();
                tipusLista.add(tipusString);
            }
            if (!tipusLista.contains(tipus)) {
                return "Nem létező korcsolyatípust adott meg";
            }
            Korcsolya ujKorcsolya = new Korcsolya(korcsolyak.length(),
                    KorcsolyaTipusEnum.valueOf(korcsolya.getString("tipus")), Integer.valueOf(korcsolya.getString("meret")),
                    korcsolya.getString("szin"));
            ujKorcsolya.writer();
            result = "A következő korcsolyát sikeresen hozzáadtuk a rendszerhez:\n\n" + ujKorcsolya;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return "Hibás bemenő paraméterek!";
        }

        return result;

    }

    public static String getKolcsonzesek(){
        String result = "";
        JSONArray kolcsonzesek = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyakolcsonzes.xml");

        for(int i = 0; i < kolcsonzesek.length(); i++){
            JSONObject kolcson = kolcsonzesek.getJSONObject(i);
            Kolcsonzes kolcsonzes = new Kolcsonzes(Integer.parseInt(kolcson.getString("id")), kolcson.getString("keresztnev"),
                    kolcson.getString("vezeteknev"), kolcson.getString("email"),
                    Integer.valueOf(kolcson.getString("korcsolyaId")), LocalDate.parse(kolcson.getString("datum")));


            if(kolcsonzes.getDatum().isAfter(LocalDate.now().minusDays(1))){
                result += kolcsonzes + "\n";
            }
        }

        if(result.equals("")){
            return "Nincsenek aktív kölcsönzések!";
        }
        else{
            return "Az alábbi aktív kölcsönzések vannak a rendszerben:\n\n" + result;
        }
    }

    public static String getFoglalasok(){
        String result = "";
        JSONArray foglalasok = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\palyafoglalas.xml");

        for(int i = 0; i < foglalasok.length(); i++){
            JSONObject fogl = foglalasok.getJSONObject(i);
            Foglalas foglalas = new Foglalas(Integer.parseInt(fogl.getString("id")), fogl.getString("vezeteknev"),
                    fogl.getString("keresztnev"), fogl.getString("email"), LocalDate.parse(fogl.getString("datum")),
                    LocalTime.parse(fogl.getString("kezdet")), LocalTime.parse(fogl.getString("veg")));


            if(foglalas.getDatum().isAfter(LocalDate.now().minusDays(1))){
                result += foglalas + "\n";
            }
        }

        if(result.equals("")){
            return "Nincsenek aktív pályafoglalások!";
        }
        else{
            return "Az alábbi aktív pályafoglalások vannak a rendszerben:\n\n" + result;
        }
    }

}

