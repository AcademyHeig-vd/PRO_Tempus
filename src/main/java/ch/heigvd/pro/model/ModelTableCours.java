package ch.heigvd.pro.model;

import ch.heigvd.pro.Connexion.dbConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelTableCours {
    int idEvenement;

    String titre, dateDebut, dateEcheance, description, acronyme;

    public ModelTableCours(int idEvenement, String titre, String dateDebut, String dateEcheance, String description, String acronyme) {
        this.idEvenement = idEvenement;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.description = description;
        this.acronyme = acronyme;
    }

    public static ObservableList<ModelTableCours> getAllCours() throws SQLException, ClassNotFoundException {
        ObservableList<ModelTableCours> oblist = FXCollections.observableArrayList();

        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        String SQL = "SELECT * FROM Cours " +
                "INNER JOIN Evenement " +
                "   ON Cours.idEvenement = Evenement.idEvenement " +
                "INNER JOIN Professeur " +
                "   ON Cours.acronyme = Professeur.acronyme";

        System.out.println("Table name query: \"" + SQL + "\"\n");
        ResultSet rs = conn.createStatement().executeQuery(SQL);


        while (rs.next()) {
            oblist.add(new ModelTableCours(rs.getInt("idEvenement"), rs.getString("titre"), rs.getString("dateDebut"), rs.getString("dateEcheance"), rs.getString("description"), rs.getString("acronyme")));
        }
        return oblist;
    }

    public void deleteFromDB() throws SQLException, ClassNotFoundException {
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        // Suppression database
        PreparedStatement stmt = null;
        stmt = connection.prepareStatement("DELETE FROM Evenement where idEvenement = ?");
        stmt.setInt(1, idEvenement);
        stmt.execute();
    }

    public static void insertCoursInDB(int idEvenement, String acronyme)  {
        try {
            dbConnexion db = new dbConnexion();
            Connection connection = db.getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement(dbConnexion.INSERT_QUERY_COURS);
            preparedStatement.setInt(1, idEvenement);
            preparedStatement.setString(2, acronyme);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
