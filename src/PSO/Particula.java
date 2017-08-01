package PSO;

import Models.Produs;

import java.util.ArrayList;
import java.util.List;

public class Particula {
    private double valoareFitness;
    private List<Produs> permutare = new ArrayList<>();

    public Particula(){
        super();
    }

    public Particula(double valoareFitness, List<Produs> permutare) {
        this.valoareFitness = valoareFitness;
        this.permutare = permutare;
    }

    public double getValoareFitness() {
        return valoareFitness;
    }

    public void setValoareFitness(double valoareFitness) {
        this.valoareFitness = valoareFitness;
    }

    public List<Produs> getPermutare() {
        return permutare;
    }

    public void setPermutare(List<Produs> permutare) {
        this.permutare = permutare;
    }
}
