package Start;

import Models.*;
import PSO.PSOProcesare;
import PSO.Particula;

import java.util.*;

public class Main {

    public static List<Componenta> listaComponente;
    public static List<Produs> listaProduse;
    public static LinieProductie linieProductie;

    public static Integer asambleaza(List<Produs> listaProduse, LinieProductie linieProductie){

        List<IteratieLinieAsamblare> linieAsamblare = initializareLinieAsamblare();
        List<Masinarie> masinarii = linieProductie.getListaMasinarii();

        // Pasul 1
        // Adaugam Produsele pe linia de asamblare
        // Pentru a putea fi adaugate toate e nevoie sa asamblam in acest timp o parte din produse
        for (Produs produs : listaProduse){
            do{
                produs.setSeAsambleaza(false);
                produs.setTimpIntrareLinie(LinieAsamblareUtils.seteazaTimpIntrare(linieAsamblare));

                // Asamblam linia curenta de produse
                linieAsamblare = LinieAsamblareUtils.asambleazaLiniaCuIteratiileCurente(linieAsamblare, masinarii);
                // Cautam masinarie pentru produsul care vrea sa intre pe linie
                linieAsamblare = LinieAsamblareUtils.puneProdusPeLinie(linieAsamblare, masinarii, produs);
            } while (!produs.isSeAsambleaza());
        }

        do{
            linieAsamblare = LinieAsamblareUtils.asambleazaLiniaCuIteratiileCurente(linieAsamblare, linieProductie.getListaMasinarii());

        } while (!LinieAsamblareUtils.esteLiniaDeAsamblareGoala(linieAsamblare));

        // Pentru a calcula timpul total de asamblare parcurgem toate produsele si luam timpul de asamblare al fiecarui produs
        return calculeazaTimpTotalDeAsamblare(listaProduse);
    }
/*     Dupa initializare  ar trebui sa arate asa
       Punem primul produs pe linia de productie intr-o stare intermediara inainte sa fie procesat
         M1   M2   M3   M4
         NULL NULL NULL NULL*/
    private static List<IteratieLinieAsamblare> initializareLinieAsamblare(){
        List<IteratieLinieAsamblare> linieAsamblare = new ArrayList<>();

        for (Masinarie masinarie : linieProductie.getListaMasinarii()){
            linieAsamblare.add(new IteratieLinieAsamblare(masinarie, null));
        }
        return  linieAsamblare;
    }

    private static int calculeazaTimpTotalDeAsamblare(List<Produs> listaProduse){
        int timpTotalAsamblare = 0;

        for (Produs produs : listaProduse){
            timpTotalAsamblare += produs.getTimpAsamblare();
        }

        return timpTotalAsamblare;
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
        final Componenta C5 = new Componenta(5,"C5", 50);
        
        return Arrays.asList(C1, C2, C3, C4, C5);
    }

    public static LinieProductie generareLinieProductie(List<Componenta> listaComponente){
        // id, nume
        // fiecare masinarie asambleaza un singur tip de componenta
        final Masinarie M1 = new Masinarie(1,"M1", listaComponente.get(0));
        final Masinarie M2 = new Masinarie(2,"M2", listaComponente.get(1));
        final Masinarie M3 = new Masinarie(3,"M3", listaComponente.get(2));
        final Masinarie M4 = new Masinarie(4,"M4", listaComponente.get(3));
        final Masinarie M5 = new Masinarie(5,"M5", listaComponente.get(4));
        //Linia de Productie contine masinariile M1, M2, M3 si M4
        final LinieProductie linieProductie = new LinieProductie(0, new ArrayList<Masinarie>(){{add(M1); add(M2); add(M3); add(M4); add(M5); }});

        return linieProductie;
    }

    public static List<Produs> generareListaProduse(List<Componenta> listaComponente){
        
        //Un produs e format din mai multe componente C1, C2 ...
        final Produs P1 = new Produs(0, "P1", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(2));}}, new ArrayList<>());
        final Produs P2 = new Produs(1, "P2", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(3));}}, new ArrayList<>());
        final Produs P3 = new Produs(2, "P3", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(4));}}, new ArrayList<>());
        final Produs P4 = new Produs(3, "P4", new ArrayList<Componenta>(){{add(listaComponente.get(1)); add(listaComponente.get(2)); add(listaComponente.get(3));}}, new ArrayList<>());
        final Produs P5 = new Produs(4, "P5", new ArrayList<Componenta>(){{add(listaComponente.get(1)); add(listaComponente.get(2)); add(listaComponente.get(4));}}, new ArrayList<>());
        final Produs P6 = new Produs(5, "P6", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(3)); add(listaComponente.get(4));}}, new ArrayList<>());
//
//        final Produs P7 = new Produs(6, "P7", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(3)); add(listaComponente.get(0));}}, new ArrayList<>());
//        final Produs P8 = new Produs(7, "P8", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(1)); add(listaComponente.get(0));}}, new ArrayList<>());
//        final Produs P9 = new Produs(8, "P9", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(1));}}, new ArrayList<>());
//        final Produs P10 = new Produs(10, "P10", new ArrayList<Componenta>(){{add(listaComponente.get(3)); add(listaComponente.get(2)); add(listaComponente.get(0));}}, new ArrayList<>());

//        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3); add(P4); add(P5);
//                                                             add(P6); add(P7); add(P8); add(P9); add(P10);}};

//        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P1); add(P2); add(P3); add(P4); add(P5); add(P6);}};
        List<Produs> listaProduse = new ArrayList<Produs>(){{add(P2); add(P6); add(P3); add(P4); add(P1); add(P5);}};

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
            Produs produs = particula.getPermutare().get(i);
            System.out.print(produs.getNume() + " " + produs.getTimpAsamblare() + " " + produs.getTimpIntrareLinie());
            System.out.println();
        }
        System.out.println("Timpul de asamblare minim este " + particula.getCelMaiBunFitness());

//        System.out.println(asambleaza(listaProduse, linieProductie));
//        for (Produs produs : listaProduse){
//            System.out.println(produs.getNume()  + " " + produs.getTimpAsamblare() + " " + produs.getTimpIntrareLinie());
//        }
    }
}
