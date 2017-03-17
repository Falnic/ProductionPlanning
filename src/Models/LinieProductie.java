package Models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinieProductie {

    private Integer id;
    private List<Masinarie> listaMasinarii;
    private Produs produs;
    private Map<Integer, ArrayList<Produs>> produseContruitePeOra = new HashMap<>();

    public LinieProductie(Integer id, List<Masinarie> listaMasinarii) {
        this.id = id;
        this.listaMasinarii = listaMasinarii;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Masinarie> getListaMasinarii() {
        return listaMasinarii;
    }

    public void setListaMasinarii(List<Masinarie> listaMasinarii) {
        this.listaMasinarii = listaMasinarii;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }
}
