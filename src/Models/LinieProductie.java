package Models;

import java.util.*;

public class LinieProductie {

    private Integer id;
    private List<Masinarie> listaMasinarii;
    private Map<Masinarie, Produs> coadaProdusePerMasinarie;

    public LinieProductie(Integer id, List<Masinarie> listaMasinarii) {
        this.id = id;
        this.listaMasinarii = listaMasinarii;
        this.coadaProdusePerMasinarie = new LinkedHashMap<Masinarie, Produs>();
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

    public Map<Masinarie, Produs> getCoadaProdusePerMasinarie() {
        return coadaProdusePerMasinarie;
    }

    public void setCoadaProdusePerMasinarie(Map<Masinarie, Produs> coadaProdusePerMasinarie) {
        this.coadaProdusePerMasinarie = coadaProdusePerMasinarie;
    }
}
