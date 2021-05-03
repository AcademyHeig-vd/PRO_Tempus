package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableEvenement {

    private final int id;
    private String titre;
    private String dateDebut;
    private String dateEcheance;
    private String description;

    public ModelTableEvenement(int id, String titre, String dateDebut, String dateEcheance, String description) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.description = description;
    }
    public void updateFromDB() throws SQLException, ClassNotFoundException {
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        // Suppression database
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.UPDATE_QUERY_EVENEMENT);

        stmt.setString(1, titre);
        stmt.setString(2, dateDebut);
        stmt.setString(3, dateEcheance);
        stmt.setString(4, description);
        stmt.setInt(5, id);
        System.out.println(stmt);
        stmt.execute();
    }

    public int getId() {
        return id;
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
