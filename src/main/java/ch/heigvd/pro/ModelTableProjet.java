package ch.heigvd.pro;

public class ModelTableProjet {
    private ModelTableEvenement evenement;
    String contenu;

    public void ModelTableCours(ModelTableEvenement evenement, String contenu){
        this.contenu = contenu;
        this.evenement = evenement;
    }

    public void setEvenement(ModelTableEvenement evenement) {
        this.evenement = evenement;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public ModelTableEvenement getEvenement() {
        return evenement;
    }

    public String getContenu() {
        return contenu;
    }
}
