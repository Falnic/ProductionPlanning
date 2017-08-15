package PSO;

import Models.Produs;
import Service.DefaultService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import static PSO.PSOConstants.*;
import static Service.DefaultService.asambleaza;
import static Service.DefaultService.linieProductie;

public class PSOProcesare {
    public List<Particula> roi = new ArrayList<>();
    // pBest = lista valori fitness
    private int[] pBest = new int[DIMENSIUNE_ROI];
    private List<List<Integer>> pBestLocation = new ArrayList<>();
    private int gBest;
    private List<Integer> gBestLocation;

    Random produsAleator = new Random();


    public void executa(List<Produs> listaTotalaProduse){
        /*For each particle
              Initialize particle
          END*/
        initializareRoi(listaTotalaProduse);
        int iteratie = 0;

        while(iteratie < ITERATII_MAXIME) {

            // Pasul 1

            // Pentru fiecare particula
            // Calculeaza valoare fitness
            for (Particula particula : roi) {
                particula.setValoareFitness(asambleaza(particula.getPermutare(), linieProductie));

                // If the fitness value is better than the best fitness value (pBest) in history
                // set current value as the new pBest
                if (particula.getValoareFitness() < particula.getCelMaiBunFitness()) {
                    particula.setCelMaiBunFitness(particula.getValoareFitness());
                    particula.setCeaMaiBunaSolutie(particula.getLocatie());
                }
            }

            // Pasul 2 - update gBest
            // Choose the particle with the best fitness value of all the particles as the gBest
            Particula particulaCuFitnessMinim = PSOUtilitati.getParticulaCuFitnessMinim(roi);
            if (iteratie == 0 || particulaCuFitnessMinim.getCelMaiBunFitness() < gBest) {
                gBest = particulaCuFitnessMinim.getCelMaiBunFitness();
                gBestLocation = particulaCuFitnessMinim.getLocatie();
            }

            for (Particula particula : roi){
                // Pasul 3 -  Calculeaza viteza noua a particulei
                List<Integer> vitezaNoua = new ArrayList<>();
                for (int j = 0; j < particula.getLocatie().size(); j++){
                    int x1 = particula.getCeaMaiBunaSolutie().get(j);
                    int x2 = particula.getLocatie().get(j);
                    vitezaNoua.add(Math.abs(x1 - x2));
                }
                particula.setViteza(vitezaNoua);
                // Pasul 4 - Schimba pozitia particulei (Schimba valoarea permutarii)
                // Pasul 5 - Evalueaza daca noua particula este corecta
                // Pasul 6 - Genereaza o structura asemanatoare cu o tupla care sa contina
                // (constrangeri incalcate, timp de asamblare pentru fiecare particula)
                // (1,100) < (2,10)
                // (1,100) > (1,10)
                
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
            }

        }
    }

    public void initializareRoi(List<Produs> listaTotalaProduse){
        for (int i = 0; i < DIMENSIUNE_ROI; i++){
            Particula particula = new Particula();
            List<Produs> copieListaTotalaProduse = new ArrayList<>();
            copieListaTotalaProduse.addAll(listaTotalaProduse);

            List<Produs> permutare = new ArrayList<>(listaTotalaProduse.size());
            while (!copieListaTotalaProduse.isEmpty()){
                Produs produs = copieListaTotalaProduse.get(produsAleator.nextInt(copieListaTotalaProduse.size()));
                copieListaTotalaProduse.remove(produs);
                permutare.add(produs);
            }
            particula.setValoareFitness(asambleaza(permutare, DefaultService.linieProductie));
            particula.setPermutare(permutare);

            List<Integer> X = new ArrayList<>();
            for (Produs produs : permutare){
                if (produs.getTimpIntrareLinie() != null){
                    X.add(produs.getTimpIntrareLinie());
                } else {
                    X.add(0);
                }
            }
            particula.setLocatie(X);
            // initializam cel mai bun fitness cu prima valoarea fitness
            particula.setCelMaiBunFitness(particula.getValoareFitness());
            // initializam cea mai buna solutie cu prima locatie
            particula.setCeaMaiBunaSolutie(particula.getLocatie());
            roi.add(particula);
        }
    }
}

//            w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
//