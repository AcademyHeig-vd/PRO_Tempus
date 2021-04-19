package ch.heigvd.pro;

public class ModelTableEvenement {
    private int idEvenement;
    private String titre;

    public ModelTableEvenement(int idEvenement, String titre) {
        this.idEvenement = idEvenement;
        this.titre = titre;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return titre;
    }
}
