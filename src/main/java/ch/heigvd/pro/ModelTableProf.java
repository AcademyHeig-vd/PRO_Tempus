package ch.heigvd.pro;

public class ModelTableProf {
    String acronyme, nom, prenom, mail;

    public ModelTableProf(String acronyme, String nom, String prenom, String mail) {
        this.acronyme = acronyme;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
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
