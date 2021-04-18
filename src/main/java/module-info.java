module ch.heigvd.pro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ch.heigvd.pro to javafx.fxml;
    exports ch.heigvd.pro;

    exports ch.heigvd.pro.calendarview;
    opens ch.heigvd.pro.calendarview to javafx.graphics;
}