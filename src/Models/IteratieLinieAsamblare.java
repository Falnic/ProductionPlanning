package Models;

public class IteratieLinieAsamblare {
    Masinarie masinarie;
    Produs produs;

    public IteratieLinieAsamblare(Masinarie masinarie, Produs produs) {
        this.masinarie = masinarie;
        this.produs = produs;
    }

    public void setMasinarieProdus(Masinarie masinarie, Produs produs){
        this.masinarie = masinarie;
        this.produs = produs;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public Masinarie getMasinarie() {
        return masinarie;
    }

    public void setMasinarie(Masinarie masinarie) {
        this.masinarie = masinarie;
    }

}
