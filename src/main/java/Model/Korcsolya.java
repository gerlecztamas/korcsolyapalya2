package Model;


public class Korcsolya implements WriterInterface {
    @GetterFunctionName(name="getId")
    private final Integer id;
    @GetterFunctionName(name="getTipus")
    private KorcsolyaTipusEnum tipus;
    @GetterFunctionName(name="getMeret")
    private Integer meret;
    @GetterFunctionName(name="getSzin")
    private String szin;

    public Korcsolya(Integer id, KorcsolyaTipusEnum tipus, Integer meret, String szin) {
        this.id = id;
        this.tipus = tipus;
        this.meret = meret;
        this.szin = szin;
    }

    public Integer getId() {
        return id;
    }

    public KorcsolyaTipusEnum getTipus() {
        return tipus;
    }

    public void setTipus(KorcsolyaTipusEnum tipus) {
        this.tipus = tipus;
    }

    public Integer getMeret() {
        return meret;
    }

    public void setMeret(Integer meret) {
        this.meret = meret;
    }

    public String getSzin() {
        return szin;
    }

    @Override
    public String toString(){
        String korcsolya = "Korcsolya típusa: " + this.tipus;
        korcsolya += "\nMérete: " + this.meret;
        korcsolya += "\nSzíne: " + this.szin;
        return korcsolya;
    }

    @Override
    public void writer() {
        XmlWriter<Korcsolya> t = new XmlWriter<>();
        t.writer(this, System.getProperty("user.home") +
                "\\IdeaProjects\\korcsolyapalya2\\src\\main\\resources\\korcsolyak.xml");
    }
}
