/**
 * https://www.sourcecodeexamples.net/2020/04/javafx-jdbc-mysql-database-example.html
 */

package ch.heigvd.pro.Connexion;

import java.sql.*;


public class dbConnexion {

    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/pro?serverTimezone=UTC&useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String INSERT_QUERY_COURS = "INSERT INTO Periode (idCours,nom,jourSemaine,heureDebut,heureFin,salle) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_QUERY_PROF = "INSERT INTO Professeur (nom,prenom,mail) VALUES (?, ?, ?)";
    private static final String INSERT_QUERY_RAPPEL = "INSERT INTO Rappel (idEvenement,contenu,lien) VALUES (?, ?, ?)";

    public Connection getConnexion() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        return connection;
    }

    public void insertRecordCours(int idCours, String nom, String jourSemaine, String heureDebut, String heureFin, String salle){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();
             // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_COURS)) {
            preparedStatement.setInt(1, idCours);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, jourSemaine);
            preparedStatement.setString(4, heureDebut);
            preparedStatement.setString(5, heureFin);
            preparedStatement.setString(6, salle);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertRecordProf(String nom, String prenom, String mail){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_PROF)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, mail);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertRecordRappel(int idEvenement, String contenu, String lien){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_RAPPEL)) {
            preparedStatement.setInt(1, idEvenement);
            preparedStatement.setString(2, contenu);
            preparedStatement.setString(3, lien);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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