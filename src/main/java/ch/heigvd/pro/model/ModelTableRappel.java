package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;
import javafx.beans.property.SimpleStringProperty;

import java.sql.*;
import java.util.ArrayList;

public class ModelTableRappel {
    int idEvenement;

    String titre, dateEcheance, heure, description, contenu, lien;

    public ModelTableRappel(int idEvenement, String titre, String date, String heure, String description, String contenu, String lien) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        this.dateEcheance = date;
        this.heure = heure;
        this.description = description;
        this.contenu = contenu;
        this.lien = lien;
    }

    /**
     * Supprime le rappel de la base de donnée
     * @throws SQLException echec de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public void deleteFromDB() throws SQLException, ClassNotFoundException {
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_RAPPEL);
        stmt.setInt(1, idEvenement);
        stmt.execute();
    }

    /**
     * Liste tous les rappels de l'application avec toute les information de l'événements
     * @return ArrayList<ModelTableRappel>
     * @throws SQLException echec de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public static ArrayList<ModelTableRappel> selectAllRappelFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableRappel> rappels = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_RAPPEL);
        while(rs.next()){
            rappels.add(new ModelTableRappel(rs.getInt("idEvenement"), rs.getString("titre"), rs.getString("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return rappels;
    }

    public static ArrayList<ModelEvenement> selectRappelPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> rappels = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        PreparedStatement preparedStatement = conn.prepareStatement(dbConnexion.SELECT_QUERY_RAPPEL_PER_DAY);
        preparedStatement.setDate(1, java.sql.Date.valueOf(day.toString())); //date du debut

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            rappels.add(new ModelEvenement(rs.getInt("idEvenement"),
                    new SimpleStringProperty(rs.getString("titre")),
                    rs.getDate("dateEcheance"),
                    rs.getString("heure"),
                    rs.getString("description"),
                    rs.getString("contenu"),
                    rs.getString("lien")));
        }
        return rappels;
    }

    /**
     * Insertion d'un rappel dans la base de donnée
     * @param idEvenement idEvenement
     * @param contenu contenu
     * @param lien lien
     * @param heure heure
     * @return vrai si l'insertion a réussi
     */
    public static boolean insertRecordRappel(int idEvenement, String contenu, String lien, String heure){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

             // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_RAPPEL);
            preparedStatement.setInt(1, idEvenement);
            preparedStatement.setString(2, contenu);
            preparedStatement.setString(3, lien);
            preparedStatement.setString(4, heure);

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
