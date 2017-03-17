package Models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fabrica {
    private Integer id = 0;
    private String nume;
    private List<LinieProductie> listaLiniiProductie;
    private Map<Componenta, Integer> stockPerComponenta;
    private Map<Produs, Integer> nrProduseConstruite = new HashMap<>();

    public Fabrica(Integer id, String nume, Map<Componenta, Integer> stockPerComponenta) {
        this.id = id;
        this.nume = nume;
        this.stockPerComponenta = stockPerComponenta;
    }
}