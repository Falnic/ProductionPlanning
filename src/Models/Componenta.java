package Models;


public class Componenta {

    private Integer id;
    private String nume;
    //timpul de montare e calculat in minute
    private Integer timpDeMontare;
    private Integer timpLivrare;

    public Componenta(Integer id, String nume, Integer timpDeMontare, Integer timpLivrare) {
        this.id = id;
        this.nume = nume;
        this.timpDeMontare = timpDeMontare;
        this.timpLivrare = timpLivrare;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getTimpDeMontare() {
        return timpDeMontare;
    }

    public void setTimpDeMontare(Integer timpDeMontare) {
        this.timpDeMontare = timpDeMontare;
    }

    public Integer getTimpLivrare() {
        return timpLivrare;
    }

    public void setTimpLivrare(Integer timpLivrare) {
        this.timpLivrare = timpLivrare;
    }
}
