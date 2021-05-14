package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ModelTableProfTest {

    static private ModelTableProf modelTableProf;
    @BeforeAll
    static void setUp(){
        modelTableProf= new ModelTableProf("CRS","Curry","Steph","steph.curry@nba.com");

    }
    @Test
    @DisplayName("Ensure correct prof insert")
    void insertProfInDB() {
        boolean cond = ModelTableProf.insertProfInDB("CRS","Curry","Steph","steph.curry@nba.com");
        assertTrue(cond ,"professor not inserted correctly");
    }

    @Test
    @DisplayName("verify update method")
    void updateFromDB() throws SQLException, ClassNotFoundException {
        modelTableProf.setAcronyme("PRR");
       modelTableProf.updateFromDB();
       boolean cond =ModelTableProf.findByAcro("PRR");
       assertTrue(cond,"update method not working well");
    }

    @Test
    @DisplayName("Ensure select method ")
    void selectAllProfFromDB() throws SQLException, ClassNotFoundException {
        int lengh=ModelTableProf.selectAllProfFromDB().size();
        boolean cond = lengh>1;
        assertTrue(cond,"Verify our select all prof method ");
    }

    @Test
    @DisplayName("ensure acronyme")
    void getAcronyme() {
       assertEquals("PRR",modelTableProf.acronyme,"wrong acronyme insered");
    }


    @Test
    @DisplayName("Ensure getter name")
    void getNom() {
        assertEquals("Curry",modelTableProf.nom,"wrong name insered");
    }

    @Test
    @DisplayName("Ensure last name getter")
    void getPrenom() {
        assertEquals("Steph",modelTableProf.prenom,"wrong last name insered");
    }

    @Test
    @DisplayName("Ensure mail getter")
    void getMail() {
        assertEquals("steph.curry@nba.com",modelTableProf.mail,"wrong mail insered");
    }
    @Test
    @DisplayName(" Ensure delete from professor table")
    void deleteFromDB() throws SQLException, ClassNotFoundException {
        boolean cond=ModelTableProf.deleteFromDB(modelTableProf.acronyme);
        assertTrue(cond,"problem with delete professor");
    }
}