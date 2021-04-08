package ch.heigvd.pro;

public class ModelTableProf {
    int idProfesseur;
    String nom, prenom, mail;

    public ModelTableProf(int idProfesseur, String nom, String prenom, String mail) {
        this.idProfesseur = idProfesseur;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }

    public int getIdProfesseur() {
        return idProfesseur;
    }

    public void setIdProfesseur(int idProfesseur) {
        this.idProfesseur = idProfesseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
