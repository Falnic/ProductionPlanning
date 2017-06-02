package Service;

import Models.*;

import java.util.*;

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

        for (int i = 0; i < listaProduse.size(); i++){
            Produs produs = listaProduse.get(i);
            for (int j = 0; j < nrMasinarii; j++){
                Masinarie masinarie = linieProductie.getListaMasinarii().get(j);
                Componenta componentaMasinarie = masinarie.getComponenta();
                for (Componenta componenta : produs.getListaComponente()){
                    if (componenta.equals(componentaMasinarie)){
                        Boolean seMonteazaPiesaPeMasinarieUrmatoare = false;

                        for (int k = j; k < nrMasinarii; k++){
                            Masinarie masinariaUrmatoare = linieProductie.getListaMasinarii().get(k);
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
                        break;
                    }
                }
            }
        }

        return timpAsamblare;
    }

    private static Integer calculeazaTimpAsamblare(List<Masinarie> masinarii, Masinarie masinarie){
        for (int i = masinarii.indexOf(masinarie) + 1; i < masinarii.size(); i++){
            if (masinarii.get(i).getRuleaza()
                    && masinarie.getComponenta().getTimpDeMontare() < masinarii.get(i).getComponenta().getTimpDeMontare()){
                return masinarii.get(i).getComponenta().getTimpDeMontare();
            }
        }
        return masinarie.getComponenta().getTimpDeMontare();
    }

    private static boolean verificaMasinariiLinie(Map<Produs, Masinarie> tempProdusMasinarieLinkedHashMap, List<Masinarie> masinarii, Masinarie masinarie, Produs produs){

        boolean linieOcupata = false;
        Iterator iteratorProduseLinieProductie = tempProdusMasinarieLinkedHashMap.entrySet().iterator();
        while (iteratorProduseLinieProductie.hasNext()){
            Map.Entry masinarieProdusEntryVerificare = (Map.Entry) iteratorProduseLinieProductie.next();
            Masinarie masinarieLinie = (Masinarie) masinarieProdusEntryVerificare.getValue();

            if (masinarii.indexOf(masinarieLinie) >= 0
                    && !masinarieProdusEntryVerificare.getKey().equals(produs)
                    && masinarii.indexOf(masinarieLinie) < masinarii.indexOf(masinarie)){
                linieOcupata = true;
                break;
            }
        }

        return linieOcupata;
    }

    private static Integer asambleaza2(List<Produs> listaProduse, LinieProductie linieProductie){
        Map<Produs, Masinarie> linkedMapMasinarieProdus = new LinkedHashMap<Produs, Masinarie>();
        Map<Produs, List<Componenta>> componenteAsamblateProdus = new HashMap<Produs, List<Componenta>>();

        List<Masinarie> masinarii = linieProductie.getListaMasinarii();

        Integer timpAsamblare = 0;

        Produs primulProdus = listaProduse.get(0);
        for (Masinarie masinarie : masinarii){
            Componenta componentaMasinarie = masinarie.getComponenta();
            for (Componenta componenta : primulProdus.getListaComponente()){
                if (componenta.equals(componentaMasinarie)){
                    primulProdus.setTimpAsamblare(primulProdus.getTimpAsamblare() + componenta.getTimpDeMontare());
                    masinarie.setRuleaza(true);
                    primulProdus.setSeAsambleaza(true);
                    primulProdus.getComponenteAsamblate().add(componenta);

                    List<Componenta> componenteAsamblate = new ArrayList<>();
                    componenteAsamblate.add(componenta);
                    componenteAsamblateProdus.put(primulProdus, componenteAsamblate);

                    break;
                }
            }
            if (primulProdus.isSeAsambleaza()){
                primulProdus.setSeAsambleaza(false);

                linkedMapMasinarieProdus.put(primulProdus, masinarie);
                break;
            }
        }
        listaProduse.remove(primulProdus);

        Masinarie masinarieIdle = new Masinarie(0, "Masinarie IDLE", null);

        while (!linkedMapMasinarieProdus.isEmpty()) {

            if (!listaProduse.isEmpty()){
                Produs produs = listaProduse.get(0);
                listaProduse.remove(produs);

                linkedMapMasinarieProdus.put(produs, masinarieIdle);
            }

            Map<Produs, Masinarie> tempProdusMasinarieLinkedHashMap = new LinkedHashMap<>();
            tempProdusMasinarieLinkedHashMap.putAll(linkedMapMasinarieProdus);
            int i = -1;
            Iterator iterator = linkedMapMasinarieProdus.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry masinarieProdusEntry = (Map.Entry) iterator.next();
                Produs produs = (Produs) masinarieProdusEntry.getKey();
                if (componenteAsamblateProdus.get(produs) != null
                        && componenteAsamblateProdus.get(produs).containsAll(produs.getListaComponente())){
                    timpAsamblare += ((Produs) masinarieProdusEntry.getKey()).getTimpAsamblare();
                    ((Masinarie) masinarieProdusEntry.getValue()).setRuleaza(false);
                    tempProdusMasinarieLinkedHashMap.remove(masinarieProdusEntry.getKey());
                    continue;
                }
                i++;
                for (Masinarie masinarie : masinarii) {
                    boolean linieOcupata = false;
                    if (i != 0){
                        linieOcupata = verificaMasinariiLinie(tempProdusMasinarieLinkedHashMap, linieProductie.getListaMasinarii(), masinarie, produs);
                    }

                    if (!produs.isSeAsambleaza() && !linieOcupata){
                        Componenta componentaMasinarie = masinarie.getComponenta();
                        for (Componenta componenta : produs.getListaComponente()) {
                            if (componenta.equals(componentaMasinarie)
                                    && masinarii.indexOf(masinarieProdusEntry.getValue()) < masinarii.indexOf(masinarie)
                                    && !masinarie.getRuleaza()) {

                                ((Masinarie) masinarieProdusEntry.getValue()).setRuleaza(false);
                                masinarie.setRuleaza(true);
                                tempProdusMasinarieLinkedHashMap.replace(produs, (Masinarie) masinarieProdusEntry.getValue(), masinarie);

                                produs.getComponenteAsamblate().add(componenta);
                                componenteAsamblateProdus.put(produs, produs.getComponenteAsamblate());

                                produs.setSeAsambleaza(true);
                                produs.setTimpAsamblare(produs.getTimpAsamblare() + calculeazaTimpAsamblare(masinarii, masinarie));
                                break;
                            }
                        }
                    }
                }
                produs.setSeAsambleaza(false);
            }

            linkedMapMasinarieProdus.clear();
            linkedMapMasinarieProdus.putAll(tempProdusMasinarieLinkedHashMap);
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
        Produs P1 = new Produs(0, "P1", new ArrayList<Componenta>(){{add(C1); add(C2); add(C3);}}, new ArrayList<Componenta>());
        Produs P2 = new Produs(1, "P2", new ArrayList<Componenta>(){{add(C1); add(C2); add(C4);}}, new ArrayList<Componenta>());
        Produs P3 = new Produs(2, "P3", new ArrayList<Componenta>(){{add(C2); add(C3); add(C4);}}, new ArrayList<Componenta>());

        // id, nume
        // fiecare masinarie asambleaza un singur tip de componenta
        Masinarie M1 = new Masinarie(1,"M1", C1);
        Masinarie M2 = new Masinarie(2,"M2", C2);
        Masinarie M3 = new Masinarie(3,"M3", C3);
        Masinarie M4 = new Masinarie(4,"M4", C4);

        //Linia de Productie contine masinariile M1, M2, M3 si M4
        LinieProductie linieProductie = new LinieProductie(0, new ArrayList<Masinarie>(){{add(M1); add(M2); add(M3); add(M4);}});

        Fabrica F1 = new Fabrica(0,"F1");

        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3);}};

        System.out.print("\nTimp de asamblare total " + asambleaza2(listaProduse, linieProductie) + "\n");


    }
}
