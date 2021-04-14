package ch.heigvd.pro;

public class ModelTableRappel {
    int idEvenement;
    String contenu, lien;

    public ModelTableRappel(int idEvenement, String contenu, String lien) {
        this.idEvenement = idEvenement;
        this.contenu = contenu;
        this.lien = lien;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
}
