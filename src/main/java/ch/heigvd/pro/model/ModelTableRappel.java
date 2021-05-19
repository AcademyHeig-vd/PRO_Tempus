package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.*;
import java.util.ArrayList;

public class ModelTableRappel {
    int idEvenement;

    String titre, dateEcheance, heure, description, contenu, lien;

    public ModelTableRappel(int idEvenement, String titre, String date, String heure, String description, String contenu, String lien) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        String[] dateEcheanceSeparee = date.split("-");
        date = dateEcheanceSeparee[2] + "." + dateEcheanceSeparee[1] + "." + dateEcheanceSeparee[0];
        this.dateEcheance = date;
        String[] heureSeparee = heure.split(":");
        heure = heureSeparee[0] + ":" + heureSeparee[1];
        this.heure = heure;
        this.description = description;
        this.contenu = contenu;
        this.lien = lien;
    }

    public void updateFromDB() throws SQLException, ClassNotFoundException {
        new ModelTableEvenement(idEvenement,titre,dateEcheance,dateEcheance,description).updateFromDB();
        dbConnexion db = new dbConnexion();
        Connection connection = db.obtConnexion();

        // Suppression database
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.REQUETE_MAJ_RAPPEL);
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
            Connection connection = db.obtConnexion();
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(dbConnexion.REQUETE_SUPPRESSION_RAPPEL);
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
        ArrayList<ModelTableRappel> rappels = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.obtConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.REQUETE_SELECTION_TOUS_RAPPELS);
        while(rs.next()){
            rappels.add(new ModelTableRappel(rs.getInt("idEvenement"), rs.getString("titre"), rs.getString("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return rappels;
    }

    public static ArrayList<ModelEvenement> selectRappelPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> rappels = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.obtConnexion();

        PreparedStatement preparedStatement = conn.prepareStatement(dbConnexion.REQUETE_SELECTION_RAPPEL_PAR_JOUR);
        preparedStatement.setString(1, day.toString()); //date du debut

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            rappels.add(new ModelEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre"),
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
            Connection connection = db.obtConnexion();

             // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.REQUETE_INSERTION_RAPPEL);
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
            dbConnexion.afficheExceptionSQL(e);
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
