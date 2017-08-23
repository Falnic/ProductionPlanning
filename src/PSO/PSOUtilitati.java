package PSO;

import Models.Produs;

import java.util.ArrayList;
import java.util.List;

public class PSOUtilitati {
    public static Particula getParticulaCuFitnessMinim(List<Particula> roi) {

        int valoareMinima = roi.get(0).getCelMaiBunFitness();
        Particula particulaGBest = roi.get(0);

        for (Particula particula : roi){
            if (particula.getCelMaiBunFitness() < valoareMinima){
                valoareMinima = particula.getCelMaiBunFitness();
                particulaGBest = particula;
            }
        }
        return particulaGBest;
    }

    public static List<Integer> calculeazaLocatie(List<Produs> permutare){
        List<Integer> X = new ArrayList<>();
        for (Produs produs : permutare){
            if (produs.getTimpIntrareLinie() != null){
                X.add(produs.getTimpIntrareLinie());
            } else {
                X.add(0);
            }
        }
        return X;
    }
}
