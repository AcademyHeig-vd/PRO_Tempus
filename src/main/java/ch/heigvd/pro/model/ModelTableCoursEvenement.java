package ch.heigvd.pro.model;

import ch.heigvd.pro.Connexion.dbConnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableCoursEvenement {
    private int idEvenement;
    private String titre;

    public ModelTableCoursEvenement(int idEvenement, String titre) {
        this.idEvenement = idEvenement;
        this.titre = titre;
    }

    /**
     * récupère tous les cours de la base de donnée avec les donnée d'événement aussi
     * @return liste des événement de style cours
     * @throws SQLException erreur de la base de donnée lors de la récupération
     * @throws ClassNotFoundException classe not found
     */
    public static ArrayList<ModelTableCoursEvenement> selectAllFromDB() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableCoursEvenement> coursEvenements = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ALL_EVENEMENT_COURS);

        while(rs.next()){
            coursEvenements.add(new ModelTableCoursEvenement(rs.getInt("idEvenement"),
                    rs.getString("titre")));
        }
        return coursEvenements;
    }

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
