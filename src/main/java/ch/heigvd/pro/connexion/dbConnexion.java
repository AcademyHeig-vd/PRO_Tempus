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
    private static final String BDD_URL = "jdbc:mysql://192.168.99.100:3306/PRO?serverTimezone=UTC&useSSL=false";
    private static final String BDD_UTILISATEUR = "root";
    private static final String BDD_MDP = "root";
    /* FIN PARAMETRE CONNEXION

    /* REQUETES POUR COURS */
    public static final String REQUETE_INSERTION_COURS = "INSERT INTO Cours (idEvenement,acronyme) VALUES (?, ?)";
    public static final String REQUETE_INSERTION_EVENEMENT_COURS = "INSERT INTO Evenement (idEvenement,titre,dateDebut,dateEcheance,description) VALUES (?, ?, ?, ?, ?)";
    public static final String REQUETE_SUPPRESSION_COURS = "DELETE FROM Evenement WHERE idEvenement = ?";
    public static final String REQUETE_SUPPRESSION_TOUS_COURS =
            "SELECT * FROM Cours " +
            "INNER JOIN Evenement " +
            "   ON Cours.idEvenement = Evenement.idEvenement " +
            "INNER JOIN Professeur " +
            "   ON Cours.acronyme = Professeur.acronyme";
    public static final String REQUETE_MAJ_COURS =
            "UPDATE Cours " +
            "SET acronyme = ? " +
            "WHERE idEvenement = ?";

    /* FIN REQUETES COURS */

    /* REQUETES POUR PROFESSEUR */
    public static final String REQUETE_SELECTION_ACRONYME_PROF = "SELECT acronyme FROM Professeur";
    public static final String REQUETE_SELECTION_ACRONYME_UN_PROF = "SELECT * FROM Professeur where acronyme = ?";
    public static final String REQUETE_INSERTION_PROF = "INSERT INTO Professeur (acronyme,nom,prenom,mail) VALUES (?, ?, ?, ?)";
    public static final String REQUETE_SUPPRESSION_PROF = "DELETE FROM Professeur where acronyme = ?";
    public static final String REQUETE_SELECTION_TOUS_PROFS = "SELECT * FROM Professeur";
    public static final String REQUETE_MAJ_PROF =
            "UPDATE Professeur " +
            "SET acronyme = ?, nom = ?, prenom = ?, mail = ?" +
            "WHERE acronyme = ?";
    /* FIN REQUETES PROFESSEUR */

    /* REQUETES POUR PERIODE */
    public static final String REQUETE_INSERTION_PERIODE =
            "INSERT INTO Periode (idCours,jourSemaine,heureDebut,heureFin,salle) VALUES (?, ?, ?, ?, ?)";
    public static final String REQUETE_SUPPRESSION_PERIODE = "DELETE FROM Periode where idPeriode = ?";
    public static final String REQUETE_SELECTION_TOUS_PERIODES =
            "SELECT * FROM Periode " +
            "INNER JOIN Evenement " +
            "   ON Periode.idCours = Evenement.idEvenement";

    public static final String REQUETE_SELECTION_TOUS_PERIODES_ENTRE =
            "SELECT * FROM Periode " +
                    "INNER JOIN Evenement " +
                    "   ON Periode.idCours = Evenement.idEvenement " +
                    "WHERE ? BETWEEN dateDebut AND dateEcheance";
    public static final String REQUETE_MAJ_PERIODE =
            "UPDATE Periode " +
            "SET idCours = ?, jourSemaine = ?, heureDebut = ?, heureFin = ?, salle = ? " +
            "WHERE idPeriode = ?";
    /* FIN REQUETES PERIODE */


    /* REQUETES POUR EVENEMENT */
    public static final String REQUETE_INSERTION_EVENEMENT =
            "INSERT INTO Evenement (titre,dateDebut,dateEcheance,description) VALUES (?, ?, ?, ?)";
    public static final String REQUETE_SELECTION_TOUS_EVENEMENT_COURS =
            "SELECT * FROM Evenement " +
            "INNER JOIN Cours " +
                    "ON Evenement.idEvenement = Cours.idEvenement";
    public static final String REQUETE_MAJ_EVENEMENT =
            "UPDATE Evenement " +
                    "SET titre = ?, dateDebut = ?, dateEcheance = ?, description = ? " +
                    "WHERE idEvenement = ?";
    /* FIN REQUETES EVENEMENT */

    /* REQUETE POUR RAPPEL */
    public static final String REQUETE_INSERTION_RAPPEL = "INSERT INTO Rappel (idEvenement,contenu,lien,heure) VALUES (?, ?, ?, ?)";
    public static final String REQUETE_SUPPRESSION_RAPPEL = "DELETE FROM Evenement where idEvenement = ?";
    public static final String REQUETE_SELECTION_TOUS_RAPPELS =
                    "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                            "ON Rappel.idEvenement = Evenement.idEvenement";
    public static final String REQUETE_SELECTION_RAPPEL_PAR_JOUR =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE dateDebut = ?";
    public static final String REQUETE_MAJ_RAPPEL =
            "UPDATE Rappel " +
            "SET contenu = ?, lien = ?, heure = ? " +
            "WHERE idEvenement = ?";
    public static final String REQUETE_SELECTION_RAPPEL_PAR_MOIS =
            "SELECT * FROM Rappel " +
                    "INNER JOIN Evenement " +
                    "   ON Rappel.idEvenement = Evenement.idEvenement "+
                    "WHERE MONTH(dateDebut) = ?";
    /* FIN REQUETE RAPPEL */

    /**
     * Méthode d'obtention d'une connexion à la base de données
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Connection obtConnexion() throws SQLException, ClassNotFoundException {
        return DriverManager.getConnection(BDD_URL, BDD_UTILISATEUR, BDD_MDP);
    }

    /**
     * Méthode d'insertion d'une entrée dans la table Cours
     * @param idEvenement
     * @param acronyme
     */
    public void insertionEntreeCours(int idEvenement, String acronyme){
        // Etape 1: Etabliseement d'une connexion
        // try-with-resource va fermer automatiquement la connexion.
        //cnx
        try (Connection connexion = obtConnexion();

             // Etape 2: Creation d'un état utilisant l'objet connexion
             PreparedStatement etatPrepare = connexion.prepareStatement(REQUETE_INSERTION_COURS)) {
            etatPrepare.setInt(1, idEvenement);
            etatPrepare.setString(2, acronyme);

            // Etape 3: Execution de la requete
            etatPrepare.executeUpdate();
        } catch (SQLException e) {
            // Affiche l'exception SQL
            afficheExceptionSQL(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode d'insertion d'une entrée dans la table Evenement
     * @param titre
     * @param dateDebut
     * @param dateEcheance
     * @param description
     * @return
     */
    public int insertionEntreeEvenement(String titre, String dateDebut, String dateEcheance, String description){
        // Etape 1: Etablissement d'une connexion
        // try-with-resource va fermer automatiquement la connexion.
        try (Connection connexion = obtConnexion();

             // Etape 2: Creation d'un état utilisant l'objet connexion
             PreparedStatement etatPrepare = connexion.prepareStatement(REQUETE_INSERTION_EVENEMENT, Statement.RETURN_GENERATED_KEYS)) {
            etatPrepare.setString(1, titre);
            etatPrepare.setString(2, dateDebut);
            etatPrepare.setString(3, dateEcheance);
            etatPrepare.setString(4, description);


            System.out.println(etatPrepare);
            // Etape 3: Execution de la requete
            etatPrepare.executeUpdate();
            ResultSet rs = etatPrepare.getGeneratedKeys();
            int idGenere = 0;
            if (rs.next()) {
                idGenere = rs.getInt(1);
            }
            return idGenere;
        } catch (SQLException e) {
            // Affiche l'exception SQL
            afficheExceptionSQL(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Méthode d'impression d'une exception SQL
     * @param ex
     */
    public static void afficheExceptionSQL(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("Etat SQL: " + ((SQLException) e).getSQLState());
                System.err.println("Code d'erreur: " + ((SQLException) e).getErrorCode());
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