module ch.heigvd.pro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires kotlin.stdlib;

    opens ch.heigvd.pro to javafx.fxml;
    exports ch.heigvd.pro;
}