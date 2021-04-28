package ch.heigvd.pro.model;


import javafx.beans.property.StringProperty;

import java.sql.Date;


//TODO mettre en simpleStringProperty lors de l'initialisation pour pouvoir passer une string au constructeur
public class ModelEvenement {
    private final int id;
    private StringProperty titre;
    private Date echeance;
    private String heure;
    private String descritpion;
    private String contenu;
    private String lien;

    public ModelEvenement(int id, StringProperty titre, Date echeance, String heure, String descritpion, String contenu, String lien){
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

    public StringProperty getTitre() {
        return titre;
    }

    public void setTitre(StringProperty titre) {
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
    /*

    public static void deleteRappel(int id) throws SQLException, ClassNotFoundException {
        // Connexion a la database
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnexion();

        //suppression du rappel
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Evenement where idEvenement = ?");
        stmt.setInt(1, id);
        stmt.execute();

    }

    public static List<Evenement> initializeAllRappel(Date date) throws SQLException, ClassNotFoundException {
        List<Evenement> rappels = new ArrayList<>();
        String SQL = "SELECT * FROM pro.Rappel INNER JOIN Evenement ON Rappel.idEvenement = Evenement.idEvenement";

        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(SQL);
        while(rs.next()){
            rappels.add(new Evenement(rs.getInt("idEvenement"), rs.getString("titre"), rs.getDate("dateEcheance"), rs.getString("heure"), rs.getString("description"), rs.getString("contenu"), rs.getString("lien")));
        }
        return rappels;

    }*/


}
