package ch.heigvd.pro.model;

import ch.heigvd.pro.connexion.dbConnexion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class ModelDayTest {
    static private ModelDay modelDay;
    static private Date date=new Date(2020,10,10);
    static private ModelTableRappel modelTableRappel;
    private static Object Date;

    @BeforeAll
    static void setUp() throws SQLException, ClassNotFoundException, ParseException {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        date = new java.sql.Date(ft.parse("2020-10-10").getTime());
        modelDay= new ModelDay(date);
        modelTableRappel = new ModelTableRappel(1002,"event","2020-10-10","11:20","important","contenet","here");

    }
    @Test
    @DisplayName("Verify date")
    void getDate() {
        Date d=modelDay.getDate();
        assertEquals(d,date,"date is not the same");

    }

    @Test
    @DisplayName("Verify setting date")
    void setDate() {
        modelDay.setDate(date);
        assertEquals(date,modelDay.getDate(),"Date is not set correctly");
    }

    @Test
    @DisplayName("verify reminder per day ")
    void selectRappelPerDay() throws SQLException, ClassNotFoundException {
        dbConnexion db = new dbConnexion();
        int idEvenement = db.insertionEntreeEvenement(modelTableRappel.titre, "2020-10-10", "2020-10-10", modelTableRappel.description);
        boolean cond = ModelTableRappel.insertRecordRappel(idEvenement, modelTableRappel.contenu, modelTableRappel.lien, modelTableRappel.heure);
         int size=ModelDay.selectRappelPerDay(date).size();
         assertEquals(1,size,"problem with reminder");
         //cleaning
         modelTableRappel.deleteFromDB();
         ModelTableRappel inter= new ModelTableRappel(idEvenement,"event","2020-10-10","11:20","important","contenet","here");
         inter.deleteFromDB();
    }
}