package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelTableProfTest {

    static private ModelTableProf modelTableProf;
    @BeforeAll
    static void setUp(){
        modelTableProf= new ModelTableProf("CRS","Curry","Steph","steph.curry@nba.com");

    }
    @Test
    @DisplayName("Ensure correct prof insert")
    @Order(1)
    void insertProfInDB() {
        boolean cond = ModelTableProf.insertProfInDB("CRS","Curry","Steph","steph.curry@nba.com");
        assertTrue(cond ,"professor not inserted correctly");
    }

    @Test
    @DisplayName("verify update method")
    @Order(2)
    void updateFromDB() throws SQLException, ClassNotFoundException {
        modelTableProf.setAcronyme("PRR");
       modelTableProf.updateFromDB();
       boolean cond =ModelTableProf.findByAcro("PRR");
       assertTrue(cond,"update method not working well");
    }

    @Test
    @DisplayName("Ensure select method ")
    @Order(3)
    void selectAllProfFromDB() throws SQLException, ClassNotFoundException {
        int lengh=ModelTableProf.selectAllProfFromDB().size();
        boolean cond = lengh>=1;
        assertTrue(cond,"Verify our select all prof method ");
    }

    @Test
    @Order(4)
    @DisplayName("ensure acronyme")
    void getAcronyme() {
       assertEquals("PRR",modelTableProf.acronyme,"wrong acronyme insered");
    }


    @Test
    @Order(5)
    @DisplayName("Ensure getter name")
    void getNom() {
        assertEquals("Curry",modelTableProf.nom,"wrong name insered");
    }

    @Test
    @Order(6)
    @DisplayName("Ensure last name getter")
    void getPrenom() {
        assertEquals("Steph",modelTableProf.prenom,"wrong last name insered");
    }

    @Test
    @Order(7)
    @DisplayName("Ensure mail getter")
    void getMail() {
        assertEquals("steph.curry@nba.com",modelTableProf.mail,"wrong mail insered");
    }
    @Test
    @Order(8)
    @DisplayName(" Ensure delete from professor table")
    void deleteFromDB()  {
        boolean cond=ModelTableProf.deleteFromDB(modelTableProf.acronyme);
        assertTrue(cond,"problem with delete professor");
    }
}