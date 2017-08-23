package Start;

import Models.*;
import PSO.PSOProcesare;
import PSO.Particula;

import java.util.*;

public class Main {

    public static List<Componenta> listaComponente;
    public static List<Produs> listaProduse;
    public static LinieProductie linieProductie;

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

    public static Integer asambleaza(List<Produs> listaProduse, LinieProductie linieProductie){
        // linkedMapMasinarieProdus este harta cu produsele asignate fiecarei masinarii
        Map<Produs, Masinarie> produseAsignateLaMasinarie = new LinkedHashMap<>();
        Map<Produs, List<Componenta>> componenteAsamblateProdus = new HashMap<>();

        List<Masinarie> masinarii = linieProductie.getListaMasinarii();
        Integer timpAsamblare = 0;

        List<Produs> copieListaProduse = new ArrayList<>();
        copieListaProduse.addAll(listaProduse);

        Produs primulProdus = copieListaProduse.get(0);
        for (Masinarie masinarie : masinarii){
            Componenta componentaMasinarie = masinarie.getComponenta();
            for (Componenta componentaProdus : primulProdus.getListaComponente()){
                if (componentaProdus.equals(componentaMasinarie)){
                    primulProdus.setTimpAsamblare(primulProdus.getTimpAsamblare() + componentaProdus.getTimpDeMontare());
                    masinarie.setRuleaza(true);
                    primulProdus.setSeAsambleaza(true);
                    primulProdus.getComponenteAsamblate().add(componentaProdus);

                    List<Componenta> componenteAsamblate = new ArrayList<>();
                    componenteAsamblate.add(componentaProdus);
                    componenteAsamblateProdus.put(primulProdus, componenteAsamblate);

                    break;
                }
            }
            if (primulProdus.isSeAsambleaza()){
                primulProdus.setSeAsambleaza(false);

                produseAsignateLaMasinarie.put(primulProdus, masinarie);
                break;
            }
        }
        copieListaProduse.remove(primulProdus);

        Masinarie masinarieIdle = new Masinarie(0, "Masinarie IDLE", null);

        while (!produseAsignateLaMasinarie.isEmpty()) {

            if (!copieListaProduse.isEmpty()){
                Produs produs = copieListaProduse.get(0);
                copieListaProduse.remove(produs);

                Iterator iterator = produseAsignateLaMasinarie.keySet().iterator();
                Produs ultimulPropusAsignatLaMasinarie = (Produs) iterator.next();

                produs.setTimpIntrareLinie(ultimulPropusAsignatLaMasinarie.getTimpAsamblare());

                produseAsignateLaMasinarie.put(produs, masinarieIdle);
            }

            Map<Produs, Masinarie> copieProduseAsignateLaMasinarie = new LinkedHashMap<>();
            copieProduseAsignateLaMasinarie.putAll(produseAsignateLaMasinarie);

            int i = -1;
            Iterator iterator = produseAsignateLaMasinarie.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry masinarieProdusEntry = (Map.Entry) iterator.next();
                Produs produs = (Produs) masinarieProdusEntry.getKey();
                if (componenteAsamblateProdus.get(produs) != null
                        && componenteAsamblateProdus.get(produs).containsAll(produs.getListaComponente())){
                    timpAsamblare += ((Produs) masinarieProdusEntry.getKey()).getTimpAsamblare();
                    ((Masinarie) masinarieProdusEntry.getValue()).setRuleaza(false);
                    ((Produs) masinarieProdusEntry.getKey()).setTimpAsamblare(0);
                    ((Produs) masinarieProdusEntry.getKey()).setComponenteAsamblate(new ArrayList<>());
                    copieProduseAsignateLaMasinarie.remove(masinarieProdusEntry.getKey());
                    continue;
                }
                i++;
                for (Masinarie masinarie : masinarii) {
                    boolean linieOcupata = false;
                    if (i != 0){
                        linieOcupata = verificaMasinariiLinie(copieProduseAsignateLaMasinarie, linieProductie.getListaMasinarii(), masinarie, produs);
                    }

                    if (!produs.isSeAsambleaza() && !linieOcupata){
                        Componenta componentaMasinarie = masinarie.getComponenta();
                        for (Componenta componenta : produs.getListaComponente()) {
                            if (componenta.equals(componentaMasinarie)
                                    && masinarii.indexOf(masinarieProdusEntry.getValue()) < masinarii.indexOf(masinarie)
                                    && !masinarie.getRuleaza()) {

                                ((Masinarie) masinarieProdusEntry.getValue()).setRuleaza(false);
                                masinarie.setRuleaza(true);
                                copieProduseAsignateLaMasinarie.replace(produs, (Masinarie) masinarieProdusEntry.getValue(), masinarie);

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

            produseAsignateLaMasinarie.clear();
            produseAsignateLaMasinarie.putAll(copieProduseAsignateLaMasinarie);
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
            for (int i = 0; i <= permutare.size(); i++){
                List<Produs> permutareNoua = new ArrayList<>(permutare.subList(0, i));
                permutareNoua.add(produs);
                permutareNoua.addAll(i + 1, permutare.subList(i, permutare.size()));

                permutari.add(permutareNoua);
            }
        }

        return permutari;
    }

    public static List<Componenta> generareListaComponente(){
        //Creare Obiecte id, nume, timpMontare
        final Componenta C1 = new Componenta(1,"C1", 10);
        final Componenta C2 = new Componenta(2,"C2", 20);
        final Componenta C3 = new Componenta(3,"C3", 30);
        final Componenta C4 = new Componenta(4,"C4", 40);
//        final Componenta C5 = new Componenta(5,"C5", 50);
        
        return Arrays.asList(C1, C2, C3, C4);
    }

    public static LinieProductie generareLinieProductie(List<Componenta> listaComponente){
        // id, nume
        // fiecare masinarie asambleaza un singur tip de componenta
        final Masinarie M1 = new Masinarie(1,"M1", listaComponente.get(0));
        final Masinarie M2 = new Masinarie(2,"M2", listaComponente.get(1));
        final Masinarie M3 = new Masinarie(3,"M3", listaComponente.get(2));
        final Masinarie M4 = new Masinarie(4,"M4", listaComponente.get(3));

        //Linia de Productie contine masinariile M1, M2, M3 si M4
        final LinieProductie linieProductie = new LinieProductie(0, new ArrayList<Masinarie>(){{add(M1); add(M2); add(M3); add(M4);}});

        return linieProductie;
    }

    public static List<Produs> generareListaProduse(List<Componenta> listaComponente){
        
        //Un produs e format din mai multe componente C1, C2 ...
        final Produs P1 = new Produs(0, "P1", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(2));}}, new ArrayList<>());
        final Produs P2 = new Produs(1, "P2", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(3));}}, new ArrayList<>());
        final Produs P3 = new Produs(2, "P3", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(1));}}, new ArrayList<>());

        final Produs P4 = new Produs(3, "P4", new ArrayList<Componenta>(){{add(listaComponente.get(1)); add(listaComponente.get(2)); add(listaComponente.get(3));}}, new ArrayList<>());
        final Produs P5 = new Produs(4, "P5", new ArrayList<Componenta>(){{add(listaComponente.get(1)); add(listaComponente.get(2)); add(listaComponente.get(0));}}, new ArrayList<>());
        final Produs P6 = new Produs(5, "P6", new ArrayList<Componenta>(){{add(listaComponente.get(1)); add(listaComponente.get(2)); add(listaComponente.get(0));}}, new ArrayList<>());

        final Produs P7 = new Produs(6, "P7", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(3)); add(listaComponente.get(0));}}, new ArrayList<>());
        final Produs P8 = new Produs(7, "P8", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(1)); add(listaComponente.get(0));}}, new ArrayList<>());
        final Produs P9 = new Produs(8, "P9", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(1));}}, new ArrayList<>());
        final Produs P10 = new Produs(10, "P10", new ArrayList<Componenta>(){{add(listaComponente.get(3)); add(listaComponente.get(2)); add(listaComponente.get(0));}}, new ArrayList<>());

//        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3); add(P4); add(P5);
//                                                             add(P6); add(P7); add(P8); add(P9); add(P10);}};

        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3); add(P4); add(P5);
                                                             add(P6); add(P7); add(P8); add(P9); add(P10);}};

        return listaProduse;
    }

    public static void main(String[] args){
        listaComponente = generareListaComponente();
        listaProduse = generareListaProduse(listaComponente);
        linieProductie = generareLinieProductie(listaComponente);

        PSOProcesare psoProcesare = new PSOProcesare();
        Particula particula = psoProcesare.executa(listaProduse);
        System.out.println("Cea mai buna solutie este");
        for (int i = 0; i < particula.getPermutare().size(); i++){
                System.out.print(particula.getPermutare().get(i).getNume() + " ");
        }
        System.out.println();
        System.out.println("Timpul de asamblare minim este " + particula.getCelMaiBunFitness());
    }
}