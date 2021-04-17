package ch.heigvd.pro;

public class ModelTableRappel {
    int idEvenement;

    String titre, dateEcheance, heure, description, contenu, lien;

    public ModelTableRappel(int idEvenement, String titre, String date, String heure, String description, String contenu, String lien) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        this.dateEcheance = date;
        this.heure = heure;
        this.description = description;
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

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String date) {
        this.dateEcheance = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
