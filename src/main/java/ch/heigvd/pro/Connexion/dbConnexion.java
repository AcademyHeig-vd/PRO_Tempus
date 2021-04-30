/**
 * https://www.sourcecodeexamples.net/2020/04/javafx-jdbc-mysql-database-example.html
 */

package ch.heigvd.pro.Connexion;

import java.sql.*;


public class dbConnexion {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PRO?serverTimezone=UTC&useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    public static final String INSERT_QUERY_PERIODE = "INSERT INTO Periode (idCours,jourSemaine,heureDebut,heureFin,salle) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_QUERY_EVENEMENT = "INSERT INTO Evenement (titre,dateDebut,dateEcheance,description) VALUES (?, ?, ?, ?)";
    public static final String INSERT_QUERY_RAPPEL = "INSERT INTO Rappel (idEvenement,contenu,lien,heure) VALUES (?, ?, ?, ?)";

    /* QUERY POUR COURS */
    public static final String INSERT_QUERY_COURS = "INSERT INTO Cours (idEvenement,acronyme) VALUES (?, ?)";
    public static final String DELETE_QUERY_COURS = "DELETE FROM Evenement WHERE idEvenement = ?";
    public static final String SELECT_QUERY_ALL_COURS = "SELECT * FROM Cours " +
            "INNER JOIN Evenement " +
            "   ON Cours.idEvenement = Evenement.idEvenement " +
            "INNER JOIN Professeur " +
            "   ON Cours.acronyme = Professeur.acronyme";
    /* FIN QUERY COURS */

    /* QUERY POUR PROFESSEUR */
    public static final String SELECT_QUERY_ACRONYM_PROF = "SELECT acronyme FROM Professeur";
    public static final String INSERT_QUERY_PROF = "INSERT INTO Professeur (acronyme,nom,prenom,mail) VALUES (?, ?, ?, ?)";
    /* FIN QUERY PROFESSEUR */

    /* QUERY POUR PERIODE */
    public static final String DELETE_QUERY_PERIODE = "DELETE FROM Periode where idPeriode = ?";
    public static final String SELECT_QUERY_ALL_PERIODE =
            "SELECT * FROM Periode " +
            "INNER JOIN Evenement " +
            "   ON Periode.idCours = Evenement.idEvenement";

    /* FIN QUERY PERIODE */

    public static final String SELECT_QUERY_RAPPEL_PER_DAY =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE Evenement.dateDebut = ?";



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

    public void insertRecordPeriode(int idCours, String jourSemaine, String heureDebut, String heureFin, String salle){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_PERIODE)) {
            preparedStatement.setInt(1, idCours);
            preparedStatement.setString(2, jourSemaine);
            preparedStatement.setString(3, heureDebut);
            preparedStatement.setString(4, heureFin);
            preparedStatement.setString(5, salle);


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

    public void insertRecordProf(String acronyme, String nom, String prenom, String mail){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_PROF)) {
            preparedStatement.setString(1, acronyme);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, mail);


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

    public void insertRecordRappel(int idEvenement, String contenu, String lien, String heure){
        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnexion();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_RAPPEL)) {
            preparedStatement.setInt(1, idEvenement);
            preparedStatement.setString(2, contenu);
            preparedStatement.setString(3, lien);
            preparedStatement.setString(4, heure);


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
