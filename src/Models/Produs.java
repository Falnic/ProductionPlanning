package Models;


import java.util.List;

public class Produs {

    private Integer id;
    private String nume;
    private List<Componenta> listaComponente;

    public Produs(Integer id, String nume, List<Componenta> listaComponente) {
        this.id = id;
        this.nume = nume;
        this.listaComponente = listaComponente;
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

    public List<Componenta> getListaComponente() {
        return listaComponente;
    }

    public void setListaComponente(List<Componenta> listaComponente) {
        this.listaComponente = listaComponente;
    }
}
