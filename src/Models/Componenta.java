package Models;


public class Componenta {

    private Integer id;
    private String nume;
    //timpul de montare e calculat in minute
    private Integer timpDeMontare;

    public Componenta(Integer id, String nume, Integer timpDeMontare) {
        this.id = id;
        this.nume = nume;
        this.timpDeMontare = timpDeMontare;
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

    @Override
    public String toString(){
        return nume + " " + timpDeMontare;
    }
}
