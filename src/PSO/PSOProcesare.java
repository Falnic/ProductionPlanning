package PSO;

import Models.Produs;

import java.util.*;

import static PSO.PSOConstants.*;
import static PSO.PSOUtilitati.calculeazaLocatie;
import static PSO.PSOUtilitati.curataPermutare;
import static Business.Main.asambleaza;
import static Business.Main.linieProductie;

public class PSOProcesare {

    private List<Particula> roi = new ArrayList<>();
    private int gBest;
    private Particula gBestParticle;
    private List<Integer> gBestLocation;

    private Random produsAleator = new Random();


    public Particula executa(List<Produs> listaTotalaProduse){
        /*For each particle
              Initialize particle
          END*/
        initializareRoi(listaTotalaProduse);
        int iteratie = 0;

        while(iteratie < ITERATII_MAXIME) {
            System.out.println("Programul de afla la iteratia "  + iteratie);
            // Pasul 1

            // Pentru fiecare particula
            // Calculeaza valoare fitness
            for (Particula particula : roi) {
                // Curata produsele din permutare pentru o asamblare noua
                particula.setPermutare(curataPermutare(particula.getPermutare()));

                particula.setValoareFitness(asambleaza(particula.getPermutare(), linieProductie));
                particula.setLocatie(calculeazaLocatie(particula.getPermutare()));

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
                gBestParticle = particulaCuFitnessMinim;
                gBestLocation = particulaCuFitnessMinim.getLocatie();
            }

            // We implement a different algorithm when we are at the first iteration
            for (Particula particula : roi) {
                // Pasul 3 -  Calculeaza viteza noua a particulei
                List<Integer> vitezaNoua;
                if (iteratie != 0){
                    vitezaNoua = calculeazaViteza(particula, particula.getLocatie());
                } else {
                    vitezaNoua = calculeazaViteza(particula, gBestLocation);
                }
                particula.setViteza(vitezaNoua);

                List<Produs> pozitieNoua = schimbaPozitie(particula);
                curataPermutare(pozitieNoua);

                int fitnessNou = asambleaza(pozitieNoua, linieProductie);

                // Pasul 4 - Schimba pozitia particulei (Schimba valoarea permutarii)
                if (particula.getCelMaiBunFitness() > fitnessNou){
                    particula.setPermutare(pozitieNoua);
                    particula.setLocatie(calculeazaLocatie(pozitieNoua));
                }
            }
            iteratie++;
        }
        return gBestParticle;
    }
    public List<Integer> calculeazaViteza(Particula particula, List<Integer> locatie){
        List<Integer> vitezaNoua = new ArrayList<>();
        for (int j = 0; j < particula.getLocatie().size(); j++) {
            int x1 = particula.getCeaMaiBunaSolutie().get(j);
            int x2 = locatie.get(j);
            vitezaNoua.add(Math.abs(x1 - x2));
        }
        return vitezaNoua;
    }
    public List<Produs> schimbaPozitie(Particula particula){
        List<Produs> pozitie = new ArrayList<>();

        for (int i = 0; i < particula.getViteza().size(); i++){
            Integer vitezaMinima = Integer.MAX_VALUE;
            Integer indiceVitezaMinima = Integer.MAX_VALUE;
            for (int j = 0; j < particula.getViteza().size(); j++){
                if (vitezaMinima > particula.getViteza().get(j)
                        && !pozitie.contains(particula.getPermutare().get(j))){
                    vitezaMinima = particula.getViteza().get(j);
                    indiceVitezaMinima = j;
                }
            }
            pozitie.add(particula.getPermutare().get(indiceVitezaMinima));
        }
        return pozitie;

    }

    public void initializareRoi(List<Produs> listaTotalaProduse){
        for (int i = 0; i < listaTotalaProduse.size(); i++){
            Particula particula = new Particula();
            List<Produs> copieListaTotalaProduse = new ArrayList<>();
            copieListaTotalaProduse.addAll(listaTotalaProduse);

            List<Produs> permutare = new ArrayList<>(listaTotalaProduse.size());
            while (!copieListaTotalaProduse.isEmpty()){
                int indiceRandom = produsAleator.nextInt(copieListaTotalaProduse.size());
                Produs produs = copieListaTotalaProduse.get(indiceRandom).cloneazaProdusCuDateEsentiale();
                copieListaTotalaProduse.remove(indiceRandom);
                permutare.add(produs);
            }
            particula.setPermutare(permutare);
            particula.setLocatie(calculeazaLocatie(permutare));
            // initializam cel mai bun fitness cu prima valoarea fitness
            particula.setCelMaiBunFitness(Integer.MAX_VALUE);
            // initializam cea mai buna solutie cu prima locatie
            particula.setCeaMaiBunaSolutie(particula.getLocatie());
            if (roi.size() < listaTotalaProduse.size()){
                roi.add(particula);
            } else {
                roi.set(i, particula);
            }
        }
    }
}