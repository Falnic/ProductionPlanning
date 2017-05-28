package Models;


import java.util.List;

public class Produs {

    private Integer id;
    private String nume;
    private List<Componenta> listaComponente;
    private boolean seAsambleaza;

    public Produs(Integer id, String nume, List<Componenta> listaComponente) {
        this.id = id;
        this.nume = nume;
        this.listaComponente = listaComponente;
        this.seAsambleaza = false;
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

    public Boolean getSeAsambleaza() {
        return seAsambleaza;
    }

    public void setSeAsambleaza(Boolean seAsambleaza) {
        this.seAsambleaza = seAsambleaza;
    }
}
