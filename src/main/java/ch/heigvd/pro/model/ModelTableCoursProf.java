package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelTableCoursProf {
    private String acronyme;

    public ModelTableCoursProf(String acronyme) {
        this.acronyme = acronyme;
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
        Connection conn = db.getConnexion();

        ResultSet rs = conn.createStatement().executeQuery(dbConnexion.SELECT_QUERY_ACRONYM_PROF);
        while(rs.next()){
            oblist.add(new ModelTableCoursProf(rs.getString("acronyme")));
        }
        return oblist;
    }

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
