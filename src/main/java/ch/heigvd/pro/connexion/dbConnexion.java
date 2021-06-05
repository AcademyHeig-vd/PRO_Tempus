/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : dbConnexion.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 19.05.2021
 But         : Classe gérant les connexions avec la base de données
 Remarque(s) : https://www.sourcecodeexamples.net/2020/04/javafx-jdbc-mysql-database-example.html
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.connexion;

import java.sql.*;

public class dbConnexion {
    /* PARAMETRES CONNEXION */
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/PRO?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";
    /* FIN PARAMETRE CONNEXION

    /* REQUETES POUR COURS */
    public static final String INSERT_QUERY_LESSON = "INSERT INTO Cours (idEvenement,acronyme) VALUES (?, ?)";
    public static final String INSERT_QUERY_EVENT_LESSON = "INSERT INTO Evenement (idEvenement,titre,dateDebut,dateEcheance,description) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_QUERY_LESSON = "DELETE FROM Evenement WHERE idEvenement = ?";
    public static final String SELECT_QUERY_ALL_LESSONS =
            "SELECT * FROM Cours " +
            "INNER JOIN Evenement " +
            "   ON Cours.idEvenement = Evenement.idEvenement " +
            "INNER JOIN Professeur " +
            "   ON Cours.acronyme = Professeur.acronyme";
    public static final String UPDATE_QUERY_LESSON =
            "UPDATE Cours " +
                    "SET acronyme = ? " +
                    "WHERE idEvenement = ?";

    /* FIN REQUETES COURS */

    /* REQUETES POUR PROFESSEUR */
    public static final String SELECT_QUERY_ACRONYM_PROF = "SELECT acronyme FROM Professeur";
    public static final String SELECT_QUERY_ACRONYM_ONE_PROF = "SELECT * FROM Professeur where acronyme = ?";
    public static final String INSERT_QUERY_PROF = "INSERT INTO Professeur (acronyme,nom,prenom,mail) VALUES (?, ?, ?, ?)";
    public static final String DELETE_QUERY_PROF = "DELETE FROM Professeur where acronyme = ?";
    public static final String SELECT_QUERY_ALL_PROF = "SELECT * FROM Professeur";
    public static final String UPDATE_QUERY_PROF =
            "UPDATE Professeur " +
            "SET acronyme = ?, nom = ?, prenom = ?, mail = ?" +
            "WHERE acronyme = ?";
    /* FIN REQUETES PROFESSEUR */

    /* REQUETES POUR PERIODE */
    public static final String INSERT_QUERY_PERIOD =
            "INSERT INTO Periode (idCours,jourSemaine,heureDebut,heureFin,salle) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_QUERY_PERIOD = "DELETE FROM Periode where idPeriode = ?";
    public static final String SELECT_QUERY_ALL_PERIODS =
            "SELECT * FROM Periode " +
                    "INNER JOIN Evenement " +
                    "   ON Periode.idCours = Evenement.idEvenement";

    public static final String SELECT_QUERY_ALL_PERIODS_BETWEEN =
            "SELECT * FROM Periode " +
                    "INNER JOIN Evenement " +
                    "   ON Periode.idCours = Evenement.idEvenement " +
                    "WHERE ? BETWEEN dateDebut AND dateEcheance";
    public static final String UPDATE_QUERY_PERIOD =
            "UPDATE Periode " +
            "SET idCours = ?, jourSemaine = ?, heureDebut = ?, heureFin = ?, salle = ? " +
            "WHERE idPeriode = ?";
    /* FIN REQUETES PERIODE */

    /* REQUETES POUR EVENEMENT */
    public static final String INSERT_QUERY_EVENT =
            "INSERT INTO Evenement (titre,dateDebut,dateEcheance,description) VALUES (?, ?, ?, ?)";
    public static final String SELECT_QUERY_ALL_EVENTS_LESSON =
            "SELECT * FROM Evenement " +
                    "INNER JOIN Cours " +
                    "ON Evenement.idEvenement = Cours.idEvenement";
    public static final String UPDATE_QUERY_EVENT =
            "UPDATE Evenement " +
                    "SET titre = ?, dateDebut = ?, dateEcheance = ?, description = ? " +
                    "WHERE idEvenement = ?";
    /* FIN REQUETES EVENEMENT */

    /* REQUETE POUR RAPPEL */
    public static final String INSERT_QUERY_REMINDER = "INSERT INTO Rappel (idEvenement,contenu,lien,heure) VALUES (?, ?, ?, ?)";
    public static final String DELETE_QUERY_REMINDER = "DELETE FROM Evenement where idEvenement = ?";
    public static final String SELECT_QUERY_ALL_REMINDERS =
                    "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                            "ON Rappel.idEvenement = Evenement.idEvenement";
    public static final String SELECT_QUERY_REMINDER_PER_DAY =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE dateDebut = ?";
    public static final String UPDATE_QUERY_REMINDER =
            "UPDATE Rappel " +
            "SET contenu = ?, lien = ?, heure = ? " +
            "WHERE idEvenement = ?";
    public static final String SELECT_QUERY_REMINDER_PER_MONTH =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE MONTH(dateDebut) = ?";
    /* FIN REQUETE RAPPEL */

    /**
     * Méthode d'obtention d'une connexion à la base de données
     * @return - connexion
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    /**
     * Méthode d'insertion d'une entrée dans la table Cours
     * @param idEvent - id de l'événement
     * @param acronym - acronyme du prof
     */
    public void insertEntryLesson(int idEvent, String acronym){
        // Etape 1: Etabliseement d'une connexion
        // try-with-resource va fermer automatiquement la connexion.
        //cnx
        try (Connection connection = getConnection();

             // Etape 2: Creation d'un état utilisant l'objet connection
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_LESSON)) {
            preparedStatement.setInt(1, idEvent);
            preparedStatement.setString(2, acronym);

            // Etape 3: Execution de la requete
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Affiche l'exception SQL
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Méthode d'insertion d'une entrée dans la table Evenement
     * @param title - titre de l'événement
     * @param dateBegin - date de début de l'événement
     * @param dateEnd - date de fin de l'événement
     * @param description - description de l'événement
     * @return - id généré ou erreur -1
     */
    public int insertEntryEvent(String title, String dateBegin, String dateEnd, String description){
        // Etape 1: Etablissement d'une connexion
        // try-with-resource va fermer automatiquement la connexion.
        try (Connection connection = getConnection();

             // Etape 2: Creation d'un état utilisant l'objet connection
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_EVENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, dateBegin);
            preparedStatement.setString(3, dateEnd);
            preparedStatement.setString(4, description);


            System.out.println(preparedStatement);
            // Etape 3: Execution de la requete
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int generatedId = 0;
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            return generatedId;
        } catch (SQLException e) {
            // Affiche l'exception SQL
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Méthode d'impression d'une exception SQL
     * @param ex - exception reçue
     */
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQL state: " + ((SQLException) e).getSQLState());
                System.err.println("Error code: " + ((SQLException) e).getErrorCode());
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
