package Service;

import Models.*;

import java.util.*;

public class DefaultService {

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

    private static Integer asambleaza(List<Produs> listaProduse, LinieProductie linieProductie){
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

    private static List<List<Produs>> permuta (List<Produs> listaProduse){
        if (listaProduse.size() == 1){
            return new ArrayList<List<Produs>>() {{add(listaProduse);}};
        }

        List<List<Produs>> permutari = new ArrayList<List<Produs>>();

        Produs produs = listaProduse.get(0);
        for (List<Produs> permutare : permuta(listaProduse.subList(1, listaProduse.size()))){
            for (int i = 0; i <= listaProduse.size(); i++){
                List permutareNoua = Arrays.asList(listaProduse.subList(0, i));
                permutareNoua.add(produs);
                permutareNoua.addAll(i + 1, listaProduse.subList(i + 1, listaProduse.size()));

                permutari.add(permutareNoua);
            }
        }

        return permutari;
    }

    public static void main(String[] args){

        //Creare Obiecte id, nume, timpMontare
        final Componenta C1 = new Componenta(1,"C1", 10);
        final Componenta C2 = new Componenta(2,"C2", 20);
        final Componenta C3 = new Componenta(3,"C3", 30);
        final Componenta C4 = new Componenta(4,"C4", 40);
        final Componenta C5 = new Componenta(5,"C5", 50);

        //Un produs e format din mai multe componente C1, C2 ...
        final Produs P1 = new Produs(0, "P1", new ArrayList<Componenta>(){{add(C1); add(C2); add(C3);}}, new ArrayList<Componenta>());
        final Produs P2 = new Produs(1, "P2", new ArrayList<Componenta>(){{add(C1); add(C2); add(C4);}}, new ArrayList<Componenta>());
        final Produs P3 = new Produs(2, "P3", new ArrayList<Componenta>(){{add(C2); add(C3); add(C4);}}, new ArrayList<Componenta>());


        final Produs P11 = new Produs(0, "P11", new ArrayList<Componenta>(){{add(C1); add(C2); add(C3);}}, new ArrayList<Componenta>());
        final Produs P12 = new Produs(0, "P12", new ArrayList<Componenta>(){{add(C1); add(C2); add(C3);}}, new ArrayList<Componenta>());

        // id, nume
        // fiecare masinarie asambleaza un singur tip de componenta
        final Masinarie M1 = new Masinarie(1,"M1", C1);
        final Masinarie M2 = new Masinarie(2,"M2", C2);
        final Masinarie M3 = new Masinarie(3,"M3", C3);
        final Masinarie M4 = new Masinarie(4,"M4", C4);

        //Linia de Productie contine masinariile M1, M2, M3 si M4
        final LinieProductie linieProductie = new LinieProductie(0, new ArrayList<Masinarie>(){{add(M1); add(M2); add(M3); add(M4);}});

        final Fabrica F1 = new Fabrica(0,"F1");

        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3);}};

        for (List<Produs> produsList : permuta(listaProduse)){
            for (Produs produs : produsList){
                System.out.print(produs.getNume() + " ");
            }
            System.out.println();
        }

        // Metoda asambleaza are cateva bug-uri. Acestea se vor rezolva in viitor
        System.out.print("\nTimp de asamblare total " + asambleaza(listaProduse, linieProductie) + "\n");


    }
}
