package PSO;

import Models.Produs;

import java.util.ArrayList;
import java.util.List;

public class Particula {
    private List<Produs> permutare = new ArrayList<>();
    // x-fitness
    private Integer valoareFitness;
    // p-fitness
    private Integer celMaiBunFitness;
    // X-vector in cazul nostru
    private List<Integer> locatie = new ArrayList<>();
    // P-vector
    private List<Integer> ceaMaiBunaSolutie = new ArrayList<>();
    // V-vector
    private List<Integer> viteza = new ArrayList<>();

    public Particula(){
        super();
    }

    public Particula(Integer valoareFitness, List<Integer> locatie, List<Produs> permutare) {
        this.valoareFitness = valoareFitness;
        this.locatie = locatie;
        this.permutare = permutare;
    }

    public Integer getValoareFitness() {
        return valoareFitness;
    }

    public void setValoareFitness(Integer valoareFitness) {
        this.valoareFitness = valoareFitness;
    }

    public List<Integer> getLocatie() {
        return locatie;
    }

    public void setLocatie(List<Integer> locatie) {
        this.locatie = locatie;
    }

    public List<Produs> getPermutare() {
        return permutare;
    }

    public void setPermutare(List<Produs> permutare) {
        this.permutare = permutare;
    }

    public Integer getCelMaiBunFitness() {
        return celMaiBunFitness;
    }

    public void setCelMaiBunFitness(Integer celMaiBunFitness) {
        this.celMaiBunFitness = celMaiBunFitness;
    }

    public List<Integer> getCeaMaiBunaSolutie() {
        return ceaMaiBunaSolutie;
    }

    public void setCeaMaiBunaSolutie(List<Integer> ceaMaiBunaSolutie) {
        this.ceaMaiBunaSolutie = ceaMaiBunaSolutie;
    }

    public List<Integer> getViteza() {
        return viteza;
    }

    public void setViteza(List<Integer> viteza) {
        this.viteza = viteza;
    }
}
