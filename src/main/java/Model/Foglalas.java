package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Foglalas extends Tranzakcio implements WriterInterface{

    @GetterFunctionName(name="getDatum")
    private final LocalDate datum;
    @GetterFunctionName(name="getKezdet")
    private final LocalTime kezdet;
    @GetterFunctionName(name="getVeg")
    private final LocalTime veg;

    public Foglalas(int id, String vezeteknev, String keresztnev, String email, LocalDate datum, LocalTime kezdet, LocalTime veg) {
        super(id, vezeteknev, keresztnev, email);
        this.datum = datum;
        this.kezdet = kezdet;
        this.veg = veg;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public LocalTime getKezdet() {
        return kezdet;
    }

    public LocalTime getVeg() {
        return veg;
    }



    @Override
    public String toString(){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String foglalas = FORMATTER.format(this.datum) + "-én/-án, ";
        foglalas += this.kezdet + "-tól/-től ";
        foglalas += this.veg + "-ig ";
        foglalas += this.getVezeteknev() + " " + this.getKeresztnev() + " néven";
        return foglalas;
    }

    @Override
    public void writer() {
        XmlWriter<Foglalas> t = new XmlWriter<>();
        t.writer(this, System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\palyafoglalas.xml");
    }
}
