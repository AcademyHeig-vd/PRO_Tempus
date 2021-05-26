/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ModelDay.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Modèle pour la page de vue par jour des rappels
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelDay {

    private Date date;
    private ObservableList<ModelEvenement> reminders;

    /**
     * Constructeur
     * @param date
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ModelDay(Date date) throws SQLException, ClassNotFoundException {
        if(date == null)
            throw new IllegalArgumentException("Date can't be null");
        this.date = date;
        this.reminders = FXCollections.observableArrayList();
        this.reminders.addAll(selectRappelPerDay(date));
    }

    /**
     * Constructeur
     * @param date
     * @param reminders
     */
    public ModelDay(Date date, ObservableList<ModelEvenement> reminders){
        this.date = date;
        this.reminders = reminders;
    }

    /**
     * Constructeur
     * @param date
     * @param reminders
     */
    public ModelDay(Date date, List<ModelEvenement> reminders){
        this.date = date;
        this.reminders = FXCollections.observableArrayList();
        this.reminders.addAll(reminders);
    }

    /**
     * Getters et Setters
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ObservableList<ModelEvenement> getReminders() {
        return reminders;
    }

    public boolean equals(ModelDay other){
        return date.equals(other.date);
    }

    public static ArrayList<ModelEvenement> selectRappelPerDay(Date day) throws SQLException, ClassNotFoundException {
        ArrayList<ModelEvenement> reminders = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement(dbConnexion.SELECT_QUERY_REMINDER_PER_DAY);

        preparedStatement.setString(1, day.toString()); //date du debut

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            reminders.add(new ModelEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre"),
                    rs.getDate("dateEcheance"),
                    rs.getString("heure"),
                    rs.getString("description"),
                    rs.getString("contenu"),
                    rs.getString("lien")));
        }
        return reminders;
    }
}
