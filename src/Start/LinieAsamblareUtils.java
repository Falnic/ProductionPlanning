package Start;

import Models.Componenta;
import Models.IteratieLinieAsamblare;
import Models.Masinarie;
import Models.Produs;

import java.util.List;

public class LinieAsamblareUtils {


    public static List<IteratieLinieAsamblare> asambleazaLiniaCuIteratiileCurente(List<IteratieLinieAsamblare> linieAsamblare, List<Masinarie> masinarii){

        for (int i = linieAsamblare.size() - 1; i > -1; i--){
            IteratieLinieAsamblare iteratieLinieAsamblare = linieAsamblare.get(i);
            Produs produs = iteratieLinieAsamblare.getProdus();
            if (produs != null){
                // Verificam daca toate componentele au fost asamblate pe produs
                // Daca nu au fost asamblate atunci punem produsul la urmatoarea masinarie
                // Altfel stergem produsul de pe linia de asamblare
                if (produs.getComponenteAsamblate().size() < produs.getListaComponente().size()){
                    puneProdusPeLinie(linieAsamblare, masinarii, produs);
                }
                // Sterge Produsul de pe iteratia veche
                iteratieLinieAsamblare.setProdus(null);
            }
        }
        return linieAsamblare;
    }

    public static List<IteratieLinieAsamblare> puneProdusPeLinie(List<IteratieLinieAsamblare> linieAsamblare, List<Masinarie> masinarii, Produs produs) {
        for (IteratieLinieAsamblare iteratieLinieAsamblare : linieAsamblare) {
            if (iteratieLinieAsamblare.getProdus() == null) {
                Masinarie masinarieIteratie = iteratieLinieAsamblare.getMasinarie();
                Componenta componenta = masinarieIteratie.getComponenta();

                if (produs.getListaComponente().contains(componenta)
                        && !produs.getComponenteAsamblate().contains(componenta)
                        && linieAsamblare.indexOf(iteratieLinieAsamblare) <= masinarii.indexOf(masinarieIteratie)) {

                    // Fiecare produs e pus pe linia de asamblare si asteapta pana masinaria care urmeaza e libera
                    int timpAsteptare = calculeazaTimpAsteptare(linieAsamblare, iteratieLinieAsamblare);

                    produs.setTimpAsamblare(produs.getTimpAsamblare() +  timpAsteptare);
                    produs.getComponenteAsamblate().add(componenta);
                    produs.setSeAsambleaza(true);
                    iteratieLinieAsamblare.setProdus(produs);
                    break;
                }
            }
        }
        return linieAsamblare;
    }

    public static boolean esteLiniaDeAsamblareGoala(List<IteratieLinieAsamblare> linieAsamblare){
        for (IteratieLinieAsamblare iteratieLinieAsamblare : linieAsamblare) {
            if (iteratieLinieAsamblare.getProdus() != null){
                return false;
            }
        }
        return true;
    }

    public static int seteazaTimpIntrare(List<IteratieLinieAsamblare> linieAsamblare){
        for (IteratieLinieAsamblare iteratieLinieAsamblare : linieAsamblare) {
            Produs produsLinie = iteratieLinieAsamblare.getProdus();
            if (produsLinie != null){
                return produsLinie.getTimpAsamblare();
            }
        }
        return 0;
    }

    public static int calculeazaTimpAsteptare(List<IteratieLinieAsamblare> linieAsamblare, IteratieLinieAsamblare iteratieLinieAsamblare){
        int timpAsamblare = iteratieLinieAsamblare.getMasinarie().getComponenta().getTimpDeMontare();
        for (int i = linieAsamblare.indexOf(iteratieLinieAsamblare) + 1; i < linieAsamblare.size(); i++){
            if (linieAsamblare.get(i).getProdus() != null){
                // returnam timpul de montare al urmatoarei componente din lista de asamblare
                int timpAsteptare = linieAsamblare.get(i).getMasinarie().getComponenta().getTimpDeMontare();
                if (timpAsteptare > timpAsamblare){
                    timpAsamblare = timpAsteptare;
                }
            }
        }
        return timpAsamblare;
    }

}