package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Kolcsonzes extends Tranzakcio implements WriterInterface{

    @GetterFunctionName(name="getKorcsolya")
    private final Integer korcsolyaId;
    @GetterFunctionName(name="getDatum")
    private final LocalDate datum;

    public Kolcsonzes(int id, String vezeteknev, String keresztnev, String email, Integer korcsolyaId, LocalDate datum) {
        super(id, vezeteknev, keresztnev, email);
        this.korcsolyaId = korcsolyaId;
        this.datum = datum;
    }

    public Integer getKorcsolya() {
        return korcsolyaId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    @Override
    public String toString(){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        String kolcsonzes = "Kölcsönző neve: " + this.getVezeteknev() + " " + this.getKeresztnev() + "\n";
        kolcsonzes += "Email címe: " + this.getEmail() + "\n";
        kolcsonzes += "Kölcsönzés dátuma: " + FORMATTER.format(this.datum) + "\n";
        kolcsonzes += "Kölcsönzött korcsolya:\n\n";
        JSONArray korcsolyak = XmlReader.read(System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyak.xml");
        for(int i = 0; i < korcsolyak.length(); i++){
            JSONObject korcsolya = korcsolyak.getJSONObject(i);
            if(Integer.valueOf(korcsolya.getString("id")).equals(this.korcsolyaId)){
                Korcsolya ujKorcsolya = new Korcsolya(Integer.valueOf(korcsolya.getString("id")), KorcsolyaTipusEnum.valueOf(korcsolya.getString("tipus")), Integer.valueOf(korcsolya.getString("meret")), korcsolya.getString("szin"));
                kolcsonzes += ujKorcsolya + "\n\n\n";
            }
        }
        return kolcsonzes;
    }

    @Override
    public void writer() {
        XmlWriter<Kolcsonzes> t = new XmlWriter<>();
        t.writer(this, System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyakolcsonzes.xml");
    }
}
