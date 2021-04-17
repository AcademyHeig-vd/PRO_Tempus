package ch.heigvd.pro;

public class ModelTableCoursProf {
    private int idProfesseur;
    private String nom;

    public ModelTableCoursProf(int idProfesseur, String nom) {
        this.idProfesseur = idProfesseur;
        this.nom = nom;
    }

    public int getIdProfesseur() {
        return idProfesseur;
    }

    public void setIdProfesseur(int idEvenement) {
        this.idProfesseur = idEvenement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
