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
    public boolean updateFromDB() throws SQLException, ClassNotFoundException {
        try {
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
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
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
    public boolean insertEvenementInDB() {
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_EVEN);
            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, this.titre);
            preparedStatement.setString(3, this.dateDebut);
            preparedStatement.setString(4, this.dateEcheance);
            preparedStatement.setString(5, this.description);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            dbConnexion.printSQLException(e);
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteFromDB()  {
        try {
            // Connexion a la database
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();
            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_COURS);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
