/**
 * https://www.sourcecodeexamples.net/2020/04/javafx-jdbc-mysql-database-example.html
 */

package ch.heigvd.pro.connexion;

import java.sql.*;

public class dbConnexion {
    /* PARAMETRES CONNEXION */
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PRO?serverTimezone=UTC&useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    /* FIN PARAMETRE CONNEXION

    /* QUERY POUR COURS */
    public static final String INSERT_QUERY_COURS = "INSERT INTO Cours (idEvenement,acronyme) VALUES (?, ?)";
    public static final String DELETE_QUERY_COURS = "DELETE FROM Evenement WHERE idEvenement = ?";
    public static final String SELECT_QUERY_ALL_COURS =
            "SELECT * FROM Cours " +
            "INNER JOIN Evenement " +
            "   ON Cours.idEvenement = Evenement.idEvenement " +
            "INNER JOIN Professeur " +
            "   ON Cours.acronyme = Professeur.acronyme";
    public static final String UPDATE_QUERY_COURS =
            "UPDATE Cours " +
            "SET acronyme = ? " +
            "WHERE idEvenement = ?";

    /* FIN QUERY COURS */

    /* QUERY POUR PROFESSEUR */
    public static final String SELECT_QUERY_ACRONYM_PROF = "SELECT acronyme FROM Professeur";
    public static final String INSERT_QUERY_PROF = "INSERT INTO Professeur (acronyme,nom,prenom,mail) VALUES (?, ?, ?, ?)";
    public static final String DELETE_QUERY_PROF = "DELETE FROM Professeur where acronyme = ?";
    public static final String SELECT_QUERY_ALL_PROF = "SELECT * FROM Professeur";
    public static final String UPDATE_QUERY_PROF =
            "UPDATE Professeur " +
            "SET acronyme = ?, nom = ?, prenom = ?, mail = ?" +
            "WHERE acronyme = ?";
    /* FIN QUERY PROFESSEUR */

    /* QUERY POUR PERIODE */
    public static final String INSERT_QUERY_PERIODE =
            "INSERT INTO Periode (idCours,jourSemaine,heureDebut,heureFin,salle) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_QUERY_PERIODE = "DELETE FROM Periode where idPeriode = ?";
    public static final String SELECT_QUERY_ALL_PERIODE =
            "SELECT * FROM Periode " +
            "INNER JOIN Evenement " +
            "   ON Periode.idCours = Evenement.idEvenement";

    public static final String SELECT_QUERY_ALL_PERIODE_BETWEEN =
            "SELECT * FROM Periode " +
                    "INNER JOIN Evenement " +
                    "   ON Periode.idCours = Evenement.idEvenement " +
                    "WHERE ? BETWEEN dateDebut AND dateEcheance";
    public static final String UPDATE_QUERY_PERIODE =
            "UPDATE Periode " +
            "SET idCours = ?, jourSemaine = ?, heureDebut = ?, heureFin = ?, salle = ? " +
            "WHERE idPeriode = ?";
    /* FIN QUERY PERIODE */


    /* QUERY POUR EVENEMENT */
    public static final String INSERT_QUERY_EVENEMENT =
            "INSERT INTO Evenement (titre,dateDebut,dateEcheance,description) VALUES (?, ?, ?, ?)";
    public static final String SELECT_QUERY_ALL_EVENEMENT_COURS =
            "SELECT * FROM Evenement " +
            "INNER JOIN Cours " +
                    "ON Evenement.idEvenement = Cours.idEvenement";
    public static final String UPDATE_QUERY_EVENEMENT =
            "UPDATE Evenement " +
                    "SET titre = ?, dateDebut = ?, dateEcheance = ?, description = ? " +
                    "WHERE idEvenement = ?";
    /* FIN QUERY EVENEMENT */

    /* QUERY POUR RAPPEL */
    public static final String INSERT_QUERY_RAPPEL = "INSERT INTO Rappel (idEvenement,contenu,lien,heure) VALUES (?, ?, ?, ?)";
    public static final String DELETE_QUERY_RAPPEL = "DELETE FROM Evenement where idEvenement = ?";
    public static final String SELECT_QUERY_ALL_RAPPEL =
                    "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                            "ON Rappel.idEvenement = Evenement.idEvenement";
    public static final String SELECT_QUERY_RAPPEL_PER_DAY =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE dateDebut = ?";
    public static final String UPDATE_QUERY_RAPPEL =
            "UPDATE Rappel " +
            "SET contenu = ?, lien = ?, heure = ? " +
            "WHERE idEvenement = ?";
    /* FIN QUERY RAPPEL */
    public static final String SELECT_QUERY_RAPPEL_PER_MONTH =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE MONTH(dateDebut) = ?";
    /* FIN QUERY RAPPEL */

    public Connection getConnexion() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        return connection;
    }

    public void insertRecordCours(int idEvenement, String acronyme){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        //cnx
        try (Connection connection = getConnexion();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_COURS)) {
            preparedStatement.setInt(1, idEvenement);
            preparedStatement.setString(2, acronyme);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public int insertRecordEvenement(String titre, String dateDebut, String dateEcheance, String description){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_EVENEMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, dateDebut);
            preparedStatement.setString(3, dateEcheance);
            preparedStatement.setString(4, description);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedId = 0;
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            return generatedId;
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}