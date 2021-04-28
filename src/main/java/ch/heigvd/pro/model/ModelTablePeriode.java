package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTablePeriode {
    int id;
    String nom, jourSemaine, heureDebut, heureFin, salle;

    public ModelTablePeriode(int id, String nom, String jourSemaine, String heureDebut, String heureFin, String salle) {
        this.id = id;
        this.nom = nom;
        this.jourSemaine = jourSemaine;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
    }

    /**
     * Supprimer la période
     * @throws SQLException erreur avec la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public void deleteFromDB() throws SQLException, ClassNotFoundException {
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_PERIODE);
        stmt.setInt(1, id);
        stmt.execute();
    }

    /**
     * Liste toutes les périodes de la base de donnée
     * @return liste des périodes
     * @throws SQLException erreur avec la requête
     * @throws ClassNotFoundException classe non trouvée
     */
    public static ArrayList<ModelTablePeriode> getAllPeriodeFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTablePeriode> periodes = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_PERIODE);
        while (rs.next()) {
            periodes.add(new ModelTablePeriode(rs.getInt("idPeriode"),
                    rs.getString("titre"),
                    rs.getString("jourSemaine"),
                    rs.getString("heureDebut"),
                    rs.getString("heureFin"),
                    rs.getString("salle")));
        }
        return periodes;
    }

    /**
     * Insertion d'un cours dans la base de donnée
     * @param idCours idCours
     * @param jourSemaine jourSemaine
     * @param heureDebut heureDebut
     * @param heureFin heureFin
     * @param salle salle
     * @return vrai si l'insertion a réussi.
     */
    public static boolean insertRecordPeriode(int idCours, String jourSemaine, String heureDebut, String heureFin, String salle) {
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_PERIODE);
            preparedStatement.setInt(1, idCours);
            preparedStatement.setString(2, jourSemaine);
            preparedStatement.setString(3, heureDebut);
            preparedStatement.setString(4, heureFin);
            preparedStatement.setString(5, salle);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            dbConnexion.printSQLException(e);
            return false;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getJourSemaine() {
        return jourSemaine;
    }

    public void setJourSemaine(String jourSemaine) { this.jourSemaine = jourSemaine; }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }
}
