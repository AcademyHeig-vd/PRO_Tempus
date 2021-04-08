package ch.heigvd.pro;

import javafx.fxml.FXML;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToCours() throws IOException {
        Tempus.setRoot("periodeAdd");
    }

    @FXML
    private void switchToProf() throws IOException {
        Tempus.setRoot("profAdd");
    }
}
