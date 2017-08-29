package Models;


import java.util.ArrayList;
import java.util.List;

public class Produs {

    private Integer id;
    private String nume;
    private List<Componenta> listaComponente;
    private List<Componenta> componenteAsamblate;
    private boolean seAsambleaza;
    private Integer timpAsamblare;
    private Integer timpIntrareLinie;

    public Produs(){
        super();
        this.seAsambleaza = false;
        this.timpAsamblare = 0;
        this.timpIntrareLinie = 0;
    }

    public Produs(Integer id, String nume, List<Componenta> listaComponente, List<Componenta> componenteAsamblate) {
        this.id = id;
        this.nume = nume;
        this.listaComponente = listaComponente;
        this.componenteAsamblate = componenteAsamblate;
        this.seAsambleaza = false;
        this.timpAsamblare = 0;
        this.timpIntrareLinie = 0;
    }

    public Produs(Integer id, String nume, List<Componenta> listaComponente, List<Componenta> componenteAsamblate,
                    Integer timpAsamblare, Integer timpIntrareLinie) {
        this.id = id;
        this.nume = nume;
        this.listaComponente = listaComponente;
        this.componenteAsamblate = componenteAsamblate;
        this.seAsambleaza = false;
        this.timpAsamblare = timpAsamblare;
        this.timpIntrareLinie = timpIntrareLinie;
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

    public boolean isSeAsambleaza() {
        return seAsambleaza;
    }

    public void setSeAsambleaza(boolean seAsambleaza) {
        this.seAsambleaza = seAsambleaza;
    }

    public Integer getTimpAsamblare() {
        return timpAsamblare;
    }

    public void setTimpAsamblare(Integer timpAsamblare) {
        this.timpAsamblare = timpAsamblare;
    }

    public List<Componenta> getComponenteAsamblate() {
        return componenteAsamblate;
    }

    public void setComponenteAsamblate(List<Componenta> componenteAsamblate) {
        this.componenteAsamblate = componenteAsamblate;
    }

    public Integer getTimpIntrareLinie() {
        return timpIntrareLinie;
    }

    public void setTimpIntrareLinie(Integer timpIntrareLinie) {
        this.timpIntrareLinie = timpIntrareLinie;
    }

    public Produs cloneazaProdusCuDateEsentiale(){
        Produs produsFinal = new Produs();

        produsFinal.setNume(this.nume);
        produsFinal.setListaComponente(this.listaComponente);
        produsFinal.setComponenteAsamblate(new ArrayList<>());

        return produsFinal;
    }
}
