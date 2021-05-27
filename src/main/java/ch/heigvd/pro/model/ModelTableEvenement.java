/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ModelTableEvenement.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Modèle pour les événements de tout type
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModelTableEvenement {

    private final int id;
    private String titre;
    private String dateDebut;
    private String dateEcheance;
    private String description;

    /**
     * Constructeur
     * @param id
     * @param title
     * @param dateBegin
     * @param dateEnd
     * @param description
     */
    public ModelTableEvenement(int id, String title, String dateBegin, String dateEnd, String description) {
        this.id = id;
        this.titre = title;
        this.dateDebut = dateBegin;
        this.dateEcheance = dateEnd;
        this.description = description;
    }

    /**
     * Méthode permettant de mettre à jour un événement
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean updateFromDB() throws SQLException, ClassNotFoundException {
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnection();

            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(dbConnexion.UPDATE_QUERY_EVENT);

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

    /**
     * Getters et Setters
     */
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

    /**
     * Méthode permettant d'insérer un événement dans la bdd
     * @return
     */
    public boolean insertEventInDB() {
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_EVENT_LESSON);
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

    /**
     * Méthode permettant de supprimer un événement de la base de données
     * @return
     */
    public boolean deleteFromDB()  {
        try {
            // Connexion a la database
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnection();
            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_LESSON);
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
