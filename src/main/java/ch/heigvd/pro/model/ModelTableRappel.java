/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ModelTableRappel.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Modèle pour les rappels
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.*;
import java.util.ArrayList;

public class ModelTableRappel {
    int idEvenement;

    String titre, dateEcheance, heure, description, contenu, lien;

    /**
     * Constructeur
     * @param idEvent
     * @param title
     * @param date
     * @param hour
     * @param description
     * @param content
     * @param link
     */
    public ModelTableRappel(int idEvent, String title, String date, String hour, String description, String content, String link) {
        this.idEvenement = idEvent;
        this.titre = title;
        String[] dateEndSplitted = date.split("-");
        date = dateEndSplitted[2] + "." + dateEndSplitted[1] + "." + dateEndSplitted[0];
        this.dateEcheance = date;
        String[] hourSplitted = hour.split(":");
        hour = hourSplitted[0] + ":" + hourSplitted[1];
        this.heure = hour;
        this.description = description;
        this.contenu = content;
        this.lien = link;
    }

    /**
     * Mise à jour d'un rappel dans la bdd
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateFromDB() throws SQLException, ClassNotFoundException {
        new ModelTableEvenement(idEvenement,titre,dateEcheance,dateEcheance,description).updateFromDB();
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnection();

        // Suppression database
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.UPDATE_QUERY_REMINDER);
        stmt.setString(1, contenu);
        stmt.setString(2, lien);
        stmt.setString(3, heure);
        stmt.setInt(4, idEvenement);
        stmt.execute();
    }
    /**
     * Supprime le rappel de la base de donnée
     * @throws SQLException echec de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public boolean deleteFromDB()  {
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnection();
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_REMINDER);
            stmt.setInt(1, idEvenement);
            stmt.execute();
        }catch (Exception e){
            return  false;
        }
        return true;
    }

    /**
     * Liste tous les rappels de l'application avec toute les information de l'événements
     * @return ArrayList<ModelTableRappel>
     * @throws SQLException echec de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public static ArrayList<ModelTableRappel> selectAllRappelFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableRappel> reminders = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnection();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_REMINDERS);
        while(rs.next()){
            reminders.add(new ModelTableRappel(rs.getInt("idEvenement"), rs.getString("titre"), rs.getString("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return reminders;
    }

    /**
     * Méthode permettant d'obtenir les rappels d'un jour donné
     * @param day
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<ModelEvenement> selectRemindersPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> reminders = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(dbConnexion.SELECT_QUERY_REMINDER_PER_DAY);
        preparedStatement.setString(1, day.toString()); //date du debut

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            reminders.add(new ModelEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre"),
                    rs.getDate("dateEcheance"),
                    rs.getString("heure"),
                    rs.getString("description"),
                    rs.getString("contenu"),
                    rs.getString("lien")));
        }
        return reminders;
    }

    /**
     * Insertion d'un rappel dans la base de donnée
     * @param idEvent idEvenement
     * @param content contenu
     * @param link lien
     * @param hour heure
     * @return vrai si l'insertion a réussi
     */
    public static boolean insertRecordReminder(int idEvent, String content, String link, String hour){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnection();

             // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_REMINDER);
            preparedStatement.setInt(1, idEvent);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, link);
            preparedStatement.setString(4, hour);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // print SQL exception information
            dbConnexion.printSQLException(e);
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Getters et Setters
     */
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
