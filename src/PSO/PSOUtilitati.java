package PSO;

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
}
