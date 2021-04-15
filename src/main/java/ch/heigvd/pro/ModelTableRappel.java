package ch.heigvd.pro;

public class ModelTableRappel {
    int idEvenement;
    String contenu, lien, heure;

    public ModelTableRappel(int idEvenement, String contenu, String lien, String heure) {
        this.idEvenement = idEvenement;
        this.contenu = contenu;
        this.lien = lien;
        this.heure = heure;
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

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
