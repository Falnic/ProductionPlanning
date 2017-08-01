package PSO;

import Models.Produs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static PSO.PSOConstants.*;

public class PSOProcesare {
    private double[] listaValoriFitness = new double[DIMENSIUNE_ROI];
    private Vector<Particula> roi = new Vector<>();
    private double[] pBest = new double[DIMENSIUNE_ROI];
    private Vector<List<Produs>> pBestLocation = new Vector<List<Produs>>();
    private double gBest;
    private List<Produs> gBestLocation;

    Random produsAleator = new Random();


    public void executa(List<Produs> listaTotalaProduse){
        initializareRoi(listaTotalaProduse);
        updateListaFitness();

        for (int i = 0; i < DIMENSIUNE_ROI; i++){
            pBest[i] = listaValoriFitness[i];
            pBestLocation.add(roi.get(i).getPermutare());
        }

        int iteratie = 0;
        double w;

        while(iteratie < ITERATII_MAXIME) {
            // step 1 - update pBest
            for(int i = 0; i < DIMENSIUNE_ROI; i++) {
                if(listaValoriFitness[i] < pBest[i]) {
                    pBest[i] = listaValoriFitness[i];
                    pBestLocation.set(i, roi.get(i).getPermutare());
                }
            }

            // step 2 - update gBest
            int bestParticleIndex = PSOUtilitati.getMinPos(listaValoriFitness);
            if(iteratie == 0 || listaValoriFitness[bestParticleIndex] < gBest) {
                gBest = listaValoriFitness[bestParticleIndex];
                gBestLocation = roi.get(bestParticleIndex).getPermutare();
            }

            // velocity function at bottom

        }


    }

    public void initializareRoi(List<Produs> listaTotalaProduse){
        for (int i = 0; i < DIMENSIUNE_ROI; i++){
            Particula particula = new Particula();

            List<Produs> permutare = new ArrayList<>(DIMENSIUNEA_PROBLEMEI);
            for (int j = 0; j < DIMENSIUNEA_PROBLEMEI; j++){
                Produs produs = listaTotalaProduse.get(produsAleator.nextInt(DIMENSIUNEA_PROBLEMEI));
                permutare.add(produs);
            }
            particula.setPermutare(permutare);
            roi.add(particula);
        }
    }

    public void updateListaFitness() {
        for(int i = 0; i < DIMENSIUNE_ROI; i++) {
            listaValoriFitness[i] = roi.get(i).getValoareFitness();
        }
    }
}

//            w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
//
//            for(int i=0; i<SWARM_SIZE; i++) {
//                double r1 = generator.nextDouble();
//                double r2 = generator.nextDouble();
//
//                Particle p = swarm.get(i);
//
//                // step 3 - update velocity
//                double[] newVel = new double[PROBLEM_DIMENSION];
//                newVel[0] = (w * p.getVelocity().getPos()[0]) +
//                        (r1 * C1) * (pBestLocation.get(i).getLoc()[0] - p.getLocation().getLoc()[0]) +
//                        (r2 * C2) * (gBestLocation.getLoc()[0] - p.getLocation().getLoc()[0]);
//                newVel[1] = (w * p.getVelocity().getPos()[1]) +
//                        (r1 * C1) * (pBestLocation.get(i).getLoc()[1] - p.getLocation().getLoc()[1]) +
//                        (r2 * C2) * (gBestLocation.getLoc()[1] - p.getLocation().getLoc()[1]);
//                Velocity vel = new Velocity(newVel);
//                p.setVelocity(vel);
//
//                // step 4 - update location
//                double[] newLoc = new double[PROBLEM_DIMENSION];
//                newLoc[0] = p.getLocation().getLoc()[0] + newVel[0];
//                newLoc[1] = p.getLocation().getLoc()[1] + newVel[1];
//                Location loc = new Location(newLoc);
//                p.setLocation(loc);
//            }
//
//            err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0
//
//
//            System.out.println("ITERATION " + t + ": ");
//            System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
//            System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
//            System.out.println("     Value: " + ProblemSet.evaluate(gBestLocation));
//
//            t++;
//            updateFitnessList();