package PSO;

import Models.Produs;

import java.util.ArrayList;
import java.util.List;

public class Particula {
    private Integer valoareFitness;
    private List<Integer> locatie = new ArrayList<>();
    private List<Produs> permutare = new ArrayList<>();

    public Particula(){
        super();
    }

    public Particula(Integer valoareFitness, List<Integer> locatie, List<Produs> permutare) {
        this.valoareFitness = valoareFitness;
        this.locatie = locatie;
        this.permutare = permutare;
    }

    public double getValoareFitness() {
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
}
