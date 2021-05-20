package ch.heigvd.pro.model;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelTableCoursTest {


    static private ModelTableProf modelTableProf;
    static private ModelTableCours modelTableCours;
    static private ModelTableEvenement modelTableEvenement;
    static private  ModelTableCoursEvenement modelTableCoursEvenement;
    @BeforeAll
    static void setUp(){
        modelTableProf= new ModelTableProf("WLC","wilt","Chamberlin","wilt.chamberlin@nba.com");
        modelTableEvenement=new ModelTableEvenement(1001,"clouding","2020-10-10","2020-11-11","test cours");
        modelTableCours= new ModelTableCours(1001,"clouding","2020-10-10","2020-11-11","test cours","WLC");
        modelTableCoursEvenement = new ModelTableCoursEvenement(1001,"clouding");
    }
    @Test
    @Order(1)
    @DisplayName("Ensure good insertion")
    void insertCoursInDB() throws SQLException, ClassNotFoundException {
        //modelTableEvenement.updateFromDB();
        ModelTableProf.insertProfInDB(modelTableProf.acronyme,modelTableProf.nom,modelTableProf.prenom,modelTableProf.mail);
        modelTableEvenement.insertEvenementInDB();
        boolean cond= ModelTableCours.insertCoursInDB(modelTableCours.idEvenement,modelTableCours.acronyme);
        assertTrue(cond,"problem with course insertion");
    }


    @Test
    @Order(2)
    @DisplayName("Verify select all course result")
    void getAllCoursFromDB() throws SQLException, ClassNotFoundException {
        int length=ModelTableCours.getAllLessonsFromDB().size();
        //min 1 course suppose to insered
        assertTrue(length>0,"problem with get all cours");
    }

    @Test
    @Order(3)
    @DisplayName("testing update ")
    void updateFromDB()  {
        ModelTableCours tmp= new ModelTableCours(1001,"Internet of things","2020-10-10","20-11-11","test cours","WLC");
        boolean cond =tmp.updateFromDB();
        assertTrue(cond,"ubdate a course have a problem!");
    }

    @Test
    @Order(4)
    @DisplayName("getId evenement")
    void getIdEvenement() {
        int s= modelTableCours.getIdEvenement();
        assertEquals(1001,s,"wrong id evenement");
    }

    @Test
    @Order(5)
    @DisplayName("getting acronyme ")
    void getAcronyme() {
        String s= modelTableCours.getAcronyme();
        assertEquals("WLC",s,"wrong acronym");
    }

    @Test
    @Order(6)
    @DisplayName("getting title")
    void getTitre() {
        String s= modelTableCours.getTitre();
        assertEquals("clouding",s,"wrong title");
    }

    @Test
    @Order(7)
    @DisplayName("getting start date")
    void getDateDebut() {
        String s= modelTableCours.getDateDebut();
        assertEquals("10.10.2020",s,"wrong start date");
    }

    @Test
    @Order(8)
    @DisplayName("getting end date")
    void getDateEcheance() {
        String s= modelTableCours.getDateEcheance();
        assertEquals("11.11.2020",s,"wrong end date");
    }
    @Test
    @Order(9)
    @DisplayName("testing delete")
    void deleteFromDB() {
        boolean cond =modelTableCours.deleteFromDB();
        assertTrue(cond,"issue with delete course");
        ModelTableProf.deleteFromDB(modelTableProf.acronyme);
    }
}