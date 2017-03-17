package Service;

import Models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultService {

    private static Produs planifica(List<Produs> listaProduse, Map<Componenta, Integer> stockComponente){

        Map<Produs, Integer> nrPieseCarePotFiMontate = new HashMap<>();
        Produs produsFinal = listaProduse.get(0);

        //planificam ce produs cream si in ce ordine
        for (int i = 0; i < listaProduse.size(); i++){
            //parcurgem produsele
            Produs produs = listaProduse.get(i);
            int nrComponentePerProdus = produs.getListaComponente().size();
            Map<Componenta, Integer> componenteInStockPerProdus = new HashMap<>(nrComponentePerProdus);

            for (int j = 0; j < nrComponentePerProdus; j++){
                //parcurgem componentele
                Componenta componenta = produs.getListaComponente().get(j);
                componenteInStockPerProdus.put(componenta, stockComponente.get(componenta));
            }
            //calculam cate produse pot fi montate cu componentele din stoc dupa numarul minim
            int nrProduseMinime = 100;
            for (Map.Entry<Componenta, Integer> nrComponenteProdus : componenteInStockPerProdus.entrySet()) {
                if (nrComponenteProdus.getValue() < nrProduseMinime){
                    nrProduseMinime = nrComponenteProdus.getValue();
                }
            }
            nrPieseCarePotFiMontate.put(produs, nrProduseMinime);
        }
        //Alegem Produsul cu numarul cel mai mare de piese care pot fi montate
        int nrProduseMaxime = 0;
        for (Map.Entry<Produs, Integer> nrMaximProduseCarePotFiCreate : nrPieseCarePotFiMontate.entrySet()) {
            if (nrMaximProduseCarePotFiCreate.getValue() > nrProduseMaxime){
                nrProduseMaxime = nrMaximProduseCarePotFiCreate.getValue();
                produsFinal = nrMaximProduseCarePotFiCreate.getKey();
            }
        }
        return produsFinal;
    }

    public static void main(String[] args){

        //Creare Obiecte
        Componenta C1 = new Componenta(0,"C1", 10, 8);
        Componenta C2 = new Componenta(1,"C2", 20, 8);
        Componenta C3 = new Componenta(2,"C3", 30, 8);
        Componenta C4 = new Componenta(2,"C4", 40, 12);
        Componenta C5 = new Componenta(2,"C5", 50, 15);

        Produs P1 = new Produs(0, "P1", new ArrayList<Componenta>(){{add(C1); add(C2); add(C3);}});
        Produs P2 = new Produs(1, "P2", new ArrayList<Componenta>(){{add(C1); add(C2); add(C4); add(C5);}});
        Produs P3 = new Produs(2, "P3", new ArrayList<Componenta>(){{add(C2); add(C3); add(C4);}});

        Masinarie M1 = new Masinarie(0,"M1");
        Masinarie M2 = new Masinarie(1,"M2");
        Masinarie M3 = new Masinarie(2,"M3");
        Masinarie M4 = new Masinarie(3,"M4");

        LinieProductie linieProductie1 = new LinieProductie(0, new ArrayList<Masinarie>(){{add(M1); add(M2); add(M3); add(M4);}});

        Map<Componenta, Integer> stockComponente = new HashMap<>();
        stockComponente.put(C1, 50); stockComponente.put(C2, 25); stockComponente.put(C3, 50);
        stockComponente.put(C4, 0);  stockComponente.put(C5, 0);

        Fabrica F1 = new Fabrica(0,"F1", stockComponente);

        //Algoritm planificare
        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3);}};
        Map<Componenta, Integer> componenteLivrate = new HashMap<>();
        componenteLivrate.put(C4, 50); componenteLivrate.put(C5, 50);

        for (int ora = 8; ora < 24; ora++) {

            for (Map.Entry<Componenta, Integer> componenta : componenteLivrate.entrySet()) {
                if (componenta.getKey().getTimpLivrare() == ora) {
                    stockComponente.put(componenta.getKey(), componenta.getValue());
                }
            }
            Produs produs = planifica(listaProduse, stockComponente);
            System.out.println("Produsul " + produs.getNume() + " va fi pus pe linia de productie");

            for (Map.Entry<Componenta, Integer> componentaStock : stockComponente.entrySet()) {
                System.out.println(componentaStock.getKey().getNume() + " " + componentaStock.getValue());
            }
        }
    }
}
