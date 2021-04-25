package ch.heigvd.pro.model;

public class ModelTableCours {
    int idEvenement;

    String titre, dateDebut, dateEcheance, description, acronyme;

    public ModelTableCours(int idEvenement, String titre, String dateDebut, String dateEcheance, String description, String acronyme) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.description = description;
        this.acronyme = acronyme;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
