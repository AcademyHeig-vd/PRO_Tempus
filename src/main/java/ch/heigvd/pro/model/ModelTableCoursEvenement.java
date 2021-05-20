/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ModelTableCoursEvenement.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Modèle pour les événements de type cours
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableCoursEvenement {
    private int idEvenement;
    private String titre;

    /**
     * Constructeur
     * @param idEvent
     * @param title
     */
    public ModelTableCoursEvenement(int idEvent, String title) {
        this.idEvenement = idEvent;
        this.titre = title;
    }

    /**
     * récupère tous les cours de la base de donnée avec les donnée d'événement aussi
     * @return liste des événement de style cours
     * @throws SQLException erreur de la base de donnée lors de la récupération
     * @throws ClassNotFoundException classe not found
     */
    public static ArrayList<ModelTableCoursEvenement> selectAllFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableCoursEvenement> eventsLessons = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnection();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_EVENTS_LESSON);

        while(rs.next()){
            eventsLessons.add(new ModelTableCoursEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre")));
        }
        return eventsLessons;
    }

    /**
     * Getters et Setters
     */
    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return titre;
    }

}
