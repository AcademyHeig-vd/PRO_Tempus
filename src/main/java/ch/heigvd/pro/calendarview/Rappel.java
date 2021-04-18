package ch.heigvd.pro.calendarview;

import ch.heigvd.pro.Connexion.dbConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class Rappel {
    final int id;
    String titre;
    Date echeance;
    String heure;
    String descritpion;
    String contenu;
    String lien;

    public Rappel(int id, String titre, Date echeance, String heure, String descritpion,String contenu, String lien){
        this.id = id;
        this.descritpion = descritpion;
        this.titre = titre;
        this.echeance = echeance;
        this.heure = heure;
        this.contenu = contenu;
        this.lien = lien;
    }

    public int getId() {
        return id;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getEcheance() {
        return echeance;
    }

    public void setEcheance(Date echeance) {
        this.echeance = echeance;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public static void deleteRappel(int id) throws SQLException, ClassNotFoundException {
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        //suppression du rappel
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Evenement where idEvenement = ?");
        stmt.setInt(1, id);
        stmt.execute();

    }

    public static List<Rappel> initializeAllRappel(Date date) throws SQLException, ClassNotFoundException {
        List<Rappel> rappels = new ArrayList<>();
        String SQL = "SELECT * FROM pro.Rappel INNER JOIN Evenement ON Rappel.idEvenement = Evenement.idEvenement";

        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(SQL);
        while(rs.next()){
            rappels.add(new Rappel(rs.getInt("idEvenement"), rs.getString("titre"), rs.getDate("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return rappels;

    }


}
