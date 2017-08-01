package Service;

import Models.LinieProductie;
import Models.Produs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPermutationsGeneratorService {

    public static int timpAsamblareMinim = Integer.MAX_VALUE;

    public static List<Produs> genereazaPermutareaRandomMinima(int numarIteratii, List<Produs> listaProduse, LinieProductie linieProductie) {
        List<Produs> permutareFinala = new ArrayList<>();
        Random produsAleator = new Random();

        for (int i = 0; i < numarIteratii; i++) {
            List<Produs> copieListaProduse = new ArrayList<>();
            copieListaProduse.addAll(listaProduse);

            List<Produs> permutare = new ArrayList<>();

            while (!copieListaProduse.isEmpty()) {
                int indexProdusAleator = produsAleator.nextInt(copieListaProduse.size());
                permutare.add(copieListaProduse.get(indexProdusAleator));

                copieListaProduse.remove(indexProdusAleator);
            }
            int timpAsamblare = DefaultService.asambleaza(permutare, linieProductie);
            if (timpAsamblareMinim > timpAsamblare){
                permutareFinala = permutare;
                timpAsamblareMinim = timpAsamblare;
            }
        }
        return permutareFinala;
    }
}
