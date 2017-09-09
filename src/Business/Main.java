package Business;

import Models.*;
import PSO.PSOProcesare;
import PSO.Particula;

import java.util.*;

import static PSO.PSOConstants.ITERATII_MAXIME;
import static PSO.PSOConstants.ITERATII_POS;
import static PSO.PSOUtilitati.curataPermutare;

public class Main {

    public static List<Componenta> listaComponente;
    public static List<Produs> listaProduse;
    public static LinieProductie linieProductie;
    public static int timpAsamblarePermutare = 0;

    public static Integer asambleaza(List<Produs> listaProduse, LinieProductie linieProductie){

        List<IteratieLinieAsamblare> linieAsamblare = initializareLinieAsamblare();
        List<Masinarie> masinarii = linieProductie.getListaMasinarii();

        // Pasul 1
        // Adaugam Produsele pe linia de asamblare
        // Pentru a putea fi adaugate toate e nevoie sa asamblam in acest timp o parte din produse
        for (Produs produs : listaProduse){
            do{
                produs.setSeAsambleaza(false);
                produs.setTimpIntrareLinie(LinieAsamblareUtilitati.seteazaTimpIntrare(linieAsamblare));

                // Asamblam linia curenta de produse
                linieAsamblare = LinieAsamblareUtilitati.asambleazaLiniaCuIteratiileCurente(linieAsamblare, masinarii);
                // Cautam masinarie pentru produsul care vrea sa intre pe linie
                linieAsamblare = LinieAsamblareUtilitati.puneProdusPeLinie(linieAsamblare, masinarii, produs);
            } while (!produs.isSeAsambleaza());
        }

        do{
            linieAsamblare = LinieAsamblareUtilitati.asambleazaLiniaCuIteratiileCurente(linieAsamblare, linieProductie.getListaMasinarii());

        } while (!LinieAsamblareUtilitati.esteLiniaDeAsamblareGoala(linieAsamblare));

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

        List<List<Produs>> permutari = new ArrayList<>();

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

        List<Componenta> list = new ArrayList<Componenta>(){{
            add(C1); add(C2); add(C3); add(C4); add(C5);}};
        return list;
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

        final Produs P7 = new Produs(6, "P7", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(3)); add(listaComponente.get(0));}}, new ArrayList<>());
        final Produs P8 = new Produs(7, "P8", new ArrayList<Componenta>(){{add(listaComponente.get(2)); add(listaComponente.get(1)); add(listaComponente.get(0));}}, new ArrayList<>());
        final Produs P9 = new Produs(8, "P9", new ArrayList<Componenta>(){{add(listaComponente.get(0)); add(listaComponente.get(1)); add(listaComponente.get(1));}}, new ArrayList<>());
        final Produs P10 = new Produs(10, "P10", new ArrayList<Componenta>(){{add(listaComponente.get(3)); add(listaComponente.get(2)); add(listaComponente.get(0));}}, new ArrayList<>());

        List<Produs> listaProduse = new ArrayList<Produs>(){
            {
                add(P1);
                add(P2);
                add(P3);
                add(P4);
                add(P5);
//                add(P6);
//                add(P7);
//                add(P8);
//                add(P9);
//                add(P10);
            }};

        return listaProduse;
    }

    public static List<Produs> cloneazaPermutareCuProduse(List<Produs> permutare){
        List<Produs> permutareClonata = new ArrayList<>();
        for (Produs produs : permutare){
            Produs produsNou = new Produs(produs.getId(), produs.getNume(), produs.getListaComponente(),
                                            produs.getComponenteAsamblate(), produs.getTimpAsamblare(), produs.getTimpIntrareLinie());
            permutareClonata.add(produsNou);
        }
        return permutareClonata;
    }

    public static List<Produs> calculeazaCuPermutari(){
        long startTime = System.currentTimeMillis();

        List<List<Produs>> listaPermutari = permuta(listaProduse);
        List<Produs> ceaMaiBunaPermutare = listaPermutari.get(0);
        int timpAsamblareMinim = Integer.MAX_VALUE;
        for (List<Produs> permutare : listaPermutari){
            curataPermutare(permutare);
            int timpAsamblare = asambleaza(permutare, linieProductie);
            if (timpAsamblareMinim > timpAsamblare){
                timpAsamblareMinim = timpAsamblare;
                ceaMaiBunaPermutare = cloneazaPermutareCuProduse(permutare);
            }
        }
        System.out.println("Cea mai buna solutie ");
        System.out.println("Cu timpul de asamblare = " + timpAsamblareMinim);
        timpAsamblarePermutare = timpAsamblareMinim;

        for (int i = 0; i < ceaMaiBunaPermutare.size(); i++){
            System.out.print(ceaMaiBunaPermutare.get(i).getNume() + "\t");
        }
        System.out.println();
        for (Produs produs : ceaMaiBunaPermutare){
            System.out.print(produs.getTimpAsamblare() + "\t");
        }
        System.out.println();
        for (Produs produs : ceaMaiBunaPermutare){
            System.out.print(produs.getTimpIntrareLinie() + "\t");
        }
        System.out.println();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("timpul total de executie = " + totalTime);

        return ceaMaiBunaPermutare;
    }

    public static String getCeaMaiBunaPermutare(){
        List<Produs> permutare = calculeazaCuPermutari();
        String rezultat = "Backtracking\n";
        rezultat +="Timpul de asamblare = " + timpAsamblarePermutare + "\n";

        rezultat += permutare.get(0).getNume() + ",";
        for (int i = 1; i < permutare.size(); i++){
            if (i % 10 == 0){
                rezultat += permutare.get(i).getNume() + "\n";
            }
            rezultat += permutare.get(i).getNume() + ",";
        }
        return rezultat;
    }

    public static String getCeaMaiBunaSolutie(){
        Particula particula = calculeazaCuPOS();
        String rezultat = "Optimizarea roiului de particule\n";
        rezultat +="Timpul de asamblare = " + particula.getCelMaiBunFitness() + "\n";
        rezultat += particula.getPermutare().get(0).getNume() + ",";
        for (int i = 1; i < particula.getPermutare().size(); i++){
            Produs produs = particula.getPermutare().get(i);
            if (i % 10 == 0){
                rezultat += produs.getNume() + "\n";
            }
            rezultat += produs.getNume() + ",";
        }
        return rezultat;
    }

    public static Particula calculeazaCuPOS(){
        long startTime = System.currentTimeMillis();

        PSOProcesare psoProcesare = new PSOProcesare();
        Particula ceaMaiBunaParticula = new Particula();
        ceaMaiBunaParticula.setCelMaiBunFitness(Integer.MAX_VALUE);

        for (int i = 0; i < ITERATII_POS; i++){
            System.out.println("###################### Programul de afla la iteratia "  + i * ITERATII_MAXIME);
            Particula particula = psoProcesare.executa(listaProduse);
            if (ceaMaiBunaParticula.getCelMaiBunFitness() > particula.getCelMaiBunFitness()){
                ceaMaiBunaParticula = particula;
            }
        }

        System.out.println("Cea mai buna solutie este");
        for (int i = 0; i < ceaMaiBunaParticula.getPermutare().size(); i++){
            Produs produs = ceaMaiBunaParticula.getPermutare().get(i);
            System.out.print(produs.getNume() + "\t" + produs.getTimpAsamblare() + "\t" + produs.getTimpIntrareLinie());
            System.out.println();
        }
        System.out.println("Timpul de asamblare minim = " + ceaMaiBunaParticula.getCelMaiBunFitness());

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("timpul total de executie = " + totalTime);

        return ceaMaiBunaParticula;
    }

    public static void genereazaNumarMareProduse(int numarProduseDeGenerat){
        List<Produs> listaTotalaProduse = new ArrayList<>();
        if (numarProduseDeGenerat > listaProduse.size()){
            for (int i = 0; i < numarProduseDeGenerat; i++){
                listaTotalaProduse.add(listaProduse.get(i % listaProduse.size()).cloneazaProdusCuDateEsentiale());
            }
            listaProduse = listaTotalaProduse;
        }
    }

    public static String getNumeProduse(){
        String numeProduse ="";
        numeProduse += listaProduse.get(0).getNume() + ",";
        for (int i = 1; i < listaProduse.size(); i++){
            Produs produs = listaProduse.get(i);
            if (i % 10 == 0){
                numeProduse += produs.getNume() + "\n";
            }
            numeProduse += produs.getNume() + ",";
        }
        return numeProduse;
    }

    public static List<String> getNumeComponente(){
        List<String> listaStringuri = new ArrayList<>();
        for (Componenta componenta : listaComponente){
            listaStringuri.add(componenta.getNume());
        }
        return listaStringuri;
    }

    public static String getProdusePeComponente(){
        String rezultat = "Produs pe componente\n";
        for (Produs produs : listaProduse){
            rezultat += produs.getNume();
            rezultat += "-";
            for (Componenta componenta : produs.getListaComponente()){
                rezultat += componenta.toString() + " ";
            }
            rezultat += "\n";
        }
        return rezultat;
    }

    public static String getMasinarii(){
        String rezultat = "Masinarii\n";
        for (Masinarie masinarie : linieProductie.getListaMasinarii()){
            rezultat += masinarie.toString() + "\n";
        }
        return rezultat;
    }

    public static boolean creazaMasinarieNoua(Integer id, String nume, int indiceComponenta){

        Componenta componenta = listaComponente.get(indiceComponenta);
        Masinarie masinarie = new Masinarie(id, nume, componenta);
        linieProductie.getListaMasinarii().add(masinarie);
        return masinarie != null ? true : false;
    }

    public static boolean creazaComponentaNoua(Integer id, String nume, Integer timpMontare){
        Componenta componenta = new Componenta(id, nume, timpMontare);
        listaComponente.add(componenta);
        return componenta != null ? true : false;
    }

    public static boolean creazaProdusNou(Integer id, String nume, int indiciComponente[ ]){
        List<Componenta> listaComponenteProdusNou = new ArrayList<>();
        for (int i = 0; i < indiciComponente.length; i++){
            listaComponenteProdusNou.add(listaComponente.get(indiciComponente[i]));
        }
        Produs produs = new Produs(id, nume, listaComponenteProdusNou, new ArrayList<>());
        listaProduse.add(produs);
        return produs != null ? true : false;
    }

    public static void genereazaDateInitiale(){
        listaComponente = generareListaComponente();
        listaProduse = generareListaProduse(listaComponente);
        linieProductie = generareLinieProductie(listaComponente);
    }
}
