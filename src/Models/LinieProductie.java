package Models;

import java.util.List;

public class LinieProductie {

    private Integer id;
    private List<Masinarie> listaMasinarii;

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
}
