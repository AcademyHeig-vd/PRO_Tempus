package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableCours {
    int idEvenement;

    String titre, dateDebut, dateEcheance, description, acronyme;

    public ModelTableCours(int idEvenement, String titre, String dateDebut, String dateEcheance, String description, String acronyme) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        String[] dateDebutSeparee = dateDebut.split("-");
        String[] dateEcheanceSeparee = dateEcheance.split("-");
        dateDebut = dateDebutSeparee[2] + "." + dateDebutSeparee[1] + "." + dateDebutSeparee[0];
        dateEcheance = dateEcheanceSeparee[2] + "." + dateEcheanceSeparee[1] + "." + dateEcheanceSeparee[0];
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.description = description;
        this.acronyme = acronyme;
    }

    /**
     * Récupère tous les cours de la dase de donnée
     * @return ArrayList des cours trouvé
     * @throws SQLException si la base de donnée est invalide
     * @throws ClassNotFoundException la non trouvée
     */
    public static ArrayList<ModelTableCours> getAllCoursFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableCours> cours = new ArrayList<>();

        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_COURS);

        while (rs.next()) {
            cours.add(new ModelTableCours(rs.getInt("idEvenement"), rs.getString("titre"), rs.getString("dateDebut"), rs.getString("dateEcheance"), rs.getString("description"), rs.getString("acronyme")));
        }
        return cours;
    }

    public boolean updateFromDB()  {
        try {
            new ModelTableEvenement(idEvenement, titre, dateDebut, dateEcheance, description).updateFromDB();
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();

            // Suppression database
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(dbConnexion.UPDATE_QUERY_COURS);
            stmt.setString(1, acronyme);
            stmt.setInt(2, idEvenement);
            stmt.execute();
            return true;
        }catch(Exception e){
            return false;
        }
    }



    /**
     * Supprime l'objet courrant de la base de donnée
     */
    public boolean deleteFromDB()  {
      try {
          // Connexion a la database
          dbConnexion db = new dbConnexion();
          Connection connection = db.getConnexion();
          // Suppression database
          PreparedStatement stmt = null;
          stmt = connection.prepareStatement(dbConnexion.DELETE_QUERY_COURS);
          stmt.setInt(1, idEvenement);
          stmt.execute();
          return true;
      }catch (Exception e){
          e.printStackTrace();
          return false;
      }
    }

    /**
     * Crée un cours dans la base de donnée avec un événement déjà existant
     * @param idEvenement id de l'événement parent
     * @param acronyme acronyme de l'enseignant du cours
     * @return vrai si l'insertion a réussi.
     */
    public static boolean insertCoursInDB(int idEvenement, String acronyme)  {
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_COURS);
            preparedStatement.setInt(1, idEvenement);
            preparedStatement.setString(2, acronyme);

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

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
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
