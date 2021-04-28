package ch.heigvd.pro.model;

import ch.heigvd.pro.Connexion.dbConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelTableProf {
    String acronyme, nom, prenom, mail;

    public ModelTableProf(String acronyme, String nom, String prenom, String mail) {
        this.acronyme = acronyme;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
    }

    /**
     * Supprime le prof de la base de donnée
     * @throws SQLException erreur lors de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public void deleteFromDB() throws SQLException, ClassNotFoundException {
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_PROF);
        stmt.setString(1, acronyme);
        stmt.execute();
    }

    /**
     * Récupère la liste de tous les profs
     * @return ArrayList de prof
     * @throws SQLException Erreur lors de la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public static ArrayList<ModelTableProf> selectAllProfFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableProf> profs = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_PROF);

        while(rs.next()){
            profs.add(new ModelTableProf(rs.getString("acronyme"), rs.getString("nom"), rs.getString("prenom"),
                    rs.getString("mail")));
        }
        return profs;
    }

    /**
     * Insertion d'un enseignant dans la base de donnée
     * @param acronyme acronyme
     * @param nom nom
     * @param prenom prenom
     * @param mail mail
     * @return vrai si l'intertion a réussi
     */
    public static boolean insertProfInDB(String acronyme, String nom, String prenom, String mail){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

             // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_PROF);
            preparedStatement.setString(1, acronyme);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, mail);

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
