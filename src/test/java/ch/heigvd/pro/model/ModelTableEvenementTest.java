package ch.heigvd.pro.model;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ModelTableEvenementTest {
    static  private ModelTableEvenement modelTableEvenement;

    @BeforeAll
    static void setUp(){
     modelTableEvenement =new ModelTableEvenement(5006,"test","2005-09-09","2005-10-09","testing event");
    }
    @Test
    @Order(1)
    @DisplayName("ensure insert in databae")
    void insertEvenementInDB() {
        boolean cond=modelTableEvenement.insertEventInDB();
        assertTrue(cond,"problem with insert event");
    }
    @Test
    @Order(2)
    @DisplayName("verify update event")
    void updateFromDB() throws SQLException, ClassNotFoundException {
        modelTableEvenement.setTitre("modify");
        boolean cond =modelTableEvenement.updateFromDB();
        assertTrue(cond,"verify update event");

    }

    @Test
    @Order(3)
    @DisplayName("verify Id")
    void getId() {
        int n =modelTableEvenement.getId();
        assertEquals(5006,n,"verify getId");
    }

    /*
    @Test
    @Order(4)
    @DisplayName("verify title")
    void getTitre() {
        String n =modelTableEvenement.getTitre();
        assertEquals("modify",n,"verify getTitre");
    }*/

    @Test
    @Order(5)
    @DisplayName("setting title")
    void setTitre() {
        modelTableEvenement.setTitre("second");
        String n =modelTableEvenement.getTitre();
        assertEquals("second",n,"verify getTitre");
    }

    @Test
    @Order(6)
    @DisplayName("verify start date")
    void getDateDebut() {
        String d =modelTableEvenement.getDateDebut();
        assertEquals("2005-09-09",d,"verify date");
    }

    @Test
    @Order(7)
    @DisplayName("setting start date")
    void setDateDebut() {
        modelTableEvenement.setDateDebut("2006-09-09");
        String d =modelTableEvenement.getDateDebut();
        assertEquals("2006-09-09",d,"verify date");
    }

    @Test
    @Order(8)
    @DisplayName("verify end date")
    void getDateEcheance() {
        String d =modelTableEvenement.getDateEcheance();
        assertEquals("2005-10-09",d,"verify date");
    }

    @Test
    @Order(9)
    @DisplayName("setting end date")
    void setDateEcheance() {
        modelTableEvenement.setDateEcheance("2006-11-09");
        String d =modelTableEvenement.getDateEcheance();
        assertEquals("2006-11-09",d,"verify date");
    }

    /*
    @Test
    @Order(10)
    @DisplayName("verify current description")
    void getDescription() {
        String d =modelTableEvenement.getDescription();
        assertEquals("testing event",d,"verify description");
    }
    */

    @Test
    @Order(11)
    @DisplayName("setting new description")
    void setDescription() {
        modelTableEvenement.setDescription("new one");
        String d =modelTableEvenement.getDescription();
        assertEquals("new one",d,"verify description");
    }

    @Test
    @Order(12)
    @DisplayName("testing delete from db")
    void deleteFromDB() {
        boolean cond =modelTableEvenement.deleteFromDB();
        assertTrue(cond,"verify delete event");
    }
}