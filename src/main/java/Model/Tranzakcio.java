package Model;

public class Tranzakcio {
    @GetterFunctionName(name="getId")
    private int id;
    @GetterFunctionName(name="getVezeteknev")
    private String vezeteknev;
    @GetterFunctionName(name="getKeresztnev")
    private String keresztnev;
    @GetterFunctionName(name="getEmail")
    private String email;


    public Tranzakcio() {
    }
    public Tranzakcio(int id, String vezeteknev, String keresztnev, String email) {
        this.id = id;
        this.vezeteknev = vezeteknev;
        this.keresztnev = keresztnev;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getVezeteknev() {
        return vezeteknev;
    }

    public void setVezeteknev(String vezeteknev) {
        this.vezeteknev = vezeteknev;
    }

    public String getKeresztnev() {
        return keresztnev;
    }

    public void setKeresztnev(String keresztnev) {
        this.keresztnev = keresztnev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

