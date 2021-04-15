package ch.heigvd.pro;

public class ModelTableCoursEvenement {
    private int idEvenement;
    private String titre;

    public ModelTableCoursEvenement(int idEvenement, String titre) {
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
