package ch.heigvd.pro.connexion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class dbConnexionTest {
    @Test
    @DisplayName("Connect to the database.")
    static public void connect() throws SQLException, ClassNotFoundException {
        dbConnexion db = new dbConnexion();
        Connection connection = db.getConnection();
        assertNotEquals(null,connection,"database connection problem");
    }

}