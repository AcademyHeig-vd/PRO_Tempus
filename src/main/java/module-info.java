module ch.heigvd.pro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens ch.heigvd.pro to javafx.fxml;
    exports ch.heigvd.pro;
    opens ch.heigvd.pro.model to javafx.fxml;
    exports ch.heigvd.pro.model;
    opens ch.heigvd.pro.controller to javafx.fxml;
    exports ch.heigvd.pro.controller;
    opens ch.heigvd.pro.view to javafx.fxml;
    exports ch.heigvd.pro.view;

}
