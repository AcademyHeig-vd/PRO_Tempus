/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ModelTableCoursProf.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Modèle pour les profs liés à un cours
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableCoursProf {
    private String acronyme;

    /**
     * Constructeur
     * @param acronym
     */
    public ModelTableCoursProf(String acronym) {
        this.acronyme = acronym;
    }

    /**
     * Liste les Profs
     * @return ArrayList de ModelTableCoursProf pour récpérer les acronyme des profs
     * @throws SQLException erreur avec la bd
     * @throws ClassNotFoundException classe non trouvée
     */
    public static ArrayList<ModelTableCoursProf> getAllAcronymProf() throws SQLException, ClassNotFoundException {
        ArrayList<ModelTableCoursProf> oblist = new ArrayList<>();
        dbConnexion db = new dbConnexion();
        Connection conn = db.getConnection();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ACRONYM_PROF);
        while(rs.next()){
            oblist.add(new ModelTableCoursProf(rs.getString("acronyme")));
        }
        return oblist;
    }

    /**
     * Getters et Setters
     */
    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    @Override
    public String toString() {
        return acronyme;
    }
}
