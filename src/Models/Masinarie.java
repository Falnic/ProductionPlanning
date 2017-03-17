package Models;


public class Masinarie {

    private Integer id;
    private String nume;
    private Componenta componenta;

    public Masinarie(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
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

    public Componenta getComponenta() {
        return componenta;
    }

    public void setComponenta(Componenta componenta) {
        this.componenta = componenta;
    }
}
