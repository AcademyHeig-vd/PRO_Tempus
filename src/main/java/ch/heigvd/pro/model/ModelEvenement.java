package ch.heigvd.pro.model;


import ch.heigvd.pro.connexion.dbConnexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


//TODO mettre en simpleStringProperty lors de l'initialisation pour pouvoir passer une string au constructeur
public class ModelEvenement {
    private final int id;
    private String titre;
    private Date echeance;
    private String heure;
    private String descritpion;
    private String contenu;
    private String lien;
    private String typeEvenement;


    public ModelEvenement(int id, String titre, Date echeance, String heure, String descritpion, String contenu, String lien){
        this.id = id;
        this.descritpion = descritpion;
        this.titre = titre;
        this.echeance = echeance;
        this.heure = heure;
        this.contenu = contenu;
        this.lien = lien;
    }

    public static ArrayList<ModelEvenement> getAllEvenementPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> evenements = ModelTableRappel.selectRappelPerDay(day);
        for(ModelEvenement evenement : evenements) {
            evenement.typeEvenement = "Rappel";
        }
        for(ModelEvenement evenement : getEvenementFromCoursAndPeriode(day))
        {
            evenement.typeEvenement = "Période";
            evenements.add(evenement);
        }
        return evenements;
    }

    private static ArrayList<ModelEvenement> getEvenementFromCoursAndPeriode(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> evenements = new ArrayList<>();
        ArrayList<ModelTablePeriode> periodes = ModelTablePeriode.getAllPeriodeIn(day);
        ModelTablePeriode.Jour jour;
        for (ModelTablePeriode periode : periodes){
            jour = ModelTablePeriode.Jour.LUNDI;
            jour = jour.getJour(periode.getJourSemaine());
            //getDayOfWeek from 1 to 7 and jour.ordinal 0 to 6
            if (jour != null && day.toLocalDate().getDayOfWeek().getValue() == jour.ordinal() + 1){
                evenements.add(new ModelEvenement(periode.getId(), periode.getNom(), day,
                        periode.getHeureDebut() + " à " + periode.getHeureFin(),
                        "Salle : " + periode.getSalle(), null, null));
            }
        }
        return evenements;
    }

    public int getId() {
        return id;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getEcheance() {
        return echeance;
    }

    public void setEcheance(Date echeance) {
        this.echeance = echeance;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
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

    public String getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }


    public boolean isRappel() {
        return typeEvenement.equals("Rappel");
    }
    /*

    public static void deleteRappel(int id) throws SQLException, ClassNotFoundException {
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        //suppression du rappel
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Evenement where idEvenement = ?");
        stmt.setInt(1, id);
        stmt.execute();

    }

    public static List<Evenement> initializeAllRappel(Date date) throws SQLException, ClassNotFoundException {
        List<Evenement> rappels = new ArrayList<>();
        String SQL = "SELECT * FROM pro.Rappel INNER JOIN Evenement ON Rappel.idEvenement = Evenement.idEvenement";

        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(SQL);
        while(rs.next()){
            rappels.add(new Evenement(rs.getInt("idEvenement"), rs.getString("titre"), rs.getDate("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return rappels;

    }*/

}
