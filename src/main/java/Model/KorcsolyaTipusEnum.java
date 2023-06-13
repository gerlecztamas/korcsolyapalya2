package Model;

public enum KorcsolyaTipusEnum {
    HockeyKorcsolya(1), Mukorcsolya(2);

    public final int id;

    KorcsolyaTipusEnum (int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
