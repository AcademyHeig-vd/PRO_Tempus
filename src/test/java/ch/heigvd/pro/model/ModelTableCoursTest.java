package ch.heigvd.pro.model;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ModelTableCoursTest {


    static private ModelTableProf modelTableProf;
    static private ModelTableCours modelTableCours;
    static private ModelTableEvenement modelTableEvenement;
    static private  ModelTableCoursEvenement modelTableCoursEvenement;
    @BeforeAll
    static void setUp(){
        modelTableProf= new ModelTableProf("WLC","wilt","Chamberlin","wilt.chamberlin@nba.com");
        modelTableEvenement=new ModelTableEvenement(1001,"clouding","2020-10-10","20-11-11","test cours");
        modelTableCours= new ModelTableCours(1001,"clouding","2020-10-10","20-11-11","test cours","WLC");
        modelTableCoursEvenement = new ModelTableCoursEvenement(1001,"clouding");
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    @DisplayName("Ensure good insertion")
    void insertCoursInDB() throws SQLException, ClassNotFoundException {
        //modelTableEvenement.updateFromDB();
        ModelTableProf.insertProfInDB(modelTableProf.acronyme,modelTableProf.nom,modelTableProf.prenom,modelTableProf.mail);
        modelTableEvenement.insertEvenementInDB();
        boolean cond= ModelTableCours.insertCoursInDB(modelTableCours.idEvenement,modelTableCours.acronyme);

        assertTrue(cond,"problem with course insertion");
    }


    @Test
    @DisplayName("Verify select all course result")
    void getAllCoursFromDB() throws SQLException, ClassNotFoundException {
        int length=ModelTableCours.getAllCoursFromDB().size();
        //min 1 course suppose to insered
        assertTrue(length>0,"problem with get all cours");
    }

    @Test
    void updateFromDB() {
    }

    @Test
    void deleteFromDB() {
    }



    @Test
    void getIdEvenement() {
    }

    @Test
    void getAcronyme() {
    }

    @Test
    void getTitre() {
    }

    @Test
    void getDateDebut() {
    }

    @Test
    void getDateEcheance() {
    }
}