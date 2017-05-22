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
                //componenta aceasta este in numar de stockComponente.get(componenta) in stock
                componenteInStockPerProdus.put(componenta, stockComponente.get(componenta));
            }
            //calculam cate produse pot fi montate cu componentele din stoc
            //nr de produse va fi influentat de numarul componentelor din stock
            int nrProduseMinime = 100;
            for (Map.Entry<Componenta, Integer> nrComponenteProdus : componenteInStockPerProdus.entrySet()) {
                if (nrComponenteProdus.getValue() < nrProduseMinime){
                    nrProduseMinime = nrComponenteProdus.getValue();
                }
            }
            nrPieseCarePotFiMontate.put(produs, nrProduseMinime);
        }
        //Alegem Produsul cu numarul cel mai mare de piese care pot fi montate
        int nrPieseMaxime = 0;
        for (Map.Entry<Produs, Integer> nrMaximPieseCarePotFiCreate : nrPieseCarePotFiMontate.entrySet()) {
            if (nrMaximPieseCarePotFiCreate.getValue() > nrPieseMaxime){
                nrPieseMaxime = nrMaximPieseCarePotFiCreate.getValue();
                produsFinal = nrMaximPieseCarePotFiCreate.getKey();
            }
        }
        return produsFinal;
    }

    private static Integer asambleaza(List<Produs> listaProduse, LinieProductie linieProductie){

        Integer timpAsamblare = 0;
        final int nrMasinarii = linieProductie.getListaMasinarii().size();

        for (Produs produs : listaProduse) {
            for (int i = 0; i < nrMasinarii; i++){

                Masinarie masinarie = linieProductie.getListaMasinarii().get(i);

                Componenta componentaMasinarie = masinarie.getComponenta();
                for (Componenta componenta : produs.getListaComponente()){
                    if (componenta.equals(componentaMasinarie)){
                        Boolean seMonteazaPiesaPeMasinarieUrmatoare = false;

                        for (int j = i; j < nrMasinarii; j++){
                            Masinarie masinariaUrmatoare = linieProductie.getListaMasinarii().get(j);
                            int timpMontareComponentaUrmatoare = masinariaUrmatoare.getComponenta().getTimpDeMontare();
                            if (masinariaUrmatoare.getRuleaza() &&
                                    timpMontareComponentaUrmatoare > componenta.getTimpDeMontare()){
                                Integer timpAsamblareMasinariaUrmatoare = masinariaUrmatoare.getComponenta().getTimpDeMontare();
                                timpAsamblare += timpAsamblareMasinariaUrmatoare;
                                seMonteazaPiesaPeMasinarieUrmatoare = true;
                                break;
                            }
                        }
                        if (!seMonteazaPiesaPeMasinarieUrmatoare){
                            timpAsamblare += componenta.getTimpDeMontare();
                        }
                    }
                }
            }
        }

        return timpAsamblare;
    }

    public static void main(String[] args){

        //Creare Obiecte id, nume, timpMontare
        Componenta C1 = new Componenta(1,"C1", 10);
        Componenta C2 = new Componenta(2,"C2", 20);
        Componenta C3 = new Componenta(3,"C3", 30);
        Componenta C4 = new Componenta(4,"C4", 40);
        Componenta C5 = new Componenta(5,"C5", 50);

        //Un produs e format din mai multe componente C1, C2 ...
        Produs P1 = new Produs(0, "P1", new ArrayList<Componenta>(){{add(C1); add(C2); add(C3);}});
        Produs P2 = new Produs(1, "P2", new ArrayList<Componenta>(){{add(C1); add(C2); add(C4);}});
        Produs P3 = new Produs(2, "P3", new ArrayList<Componenta>(){{add(C2); add(C3); add(C4);}});

        // id, nume
        // fiecare masinarie asambleaza un singur tip de componenta
        Masinarie M1 = new Masinarie(0,"M1", C1);
        Masinarie M2 = new Masinarie(1,"M2", C2);
        Masinarie M3 = new Masinarie(2,"M3", C3);
        Masinarie M4 = new Masinarie(3,"M4", C4);

        //Linia de Productie contine masinariile M1, M2, M3 si M4
        LinieProductie linieProductie = new LinieProductie(0, new ArrayList<Masinarie>(){{add(M1); add(M2); add(M3); add(M4);}});

        Fabrica F1 = new Fabrica(0,"F1");

        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3);}};

        System.out.print("timp de asamblare total " + asambleaza(listaProduse, linieProductie));


    }
}
