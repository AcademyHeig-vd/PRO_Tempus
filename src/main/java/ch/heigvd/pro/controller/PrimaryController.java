package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import javafx.fxml.FXML;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToPeriode() throws IOException {
        Tempus.setRoot("view/periodeAdd");
    }

    @FXML
    private void switchToRappel() throws IOException {
        Tempus.setRoot("view/rappelAdd");
    }

    @FXML
    private void switchToProf() throws IOException {
        Tempus.setRoot("view/profAdd");
    }

    @FXML
    private void switchToCours() throws IOException {
        Tempus.setRoot("view/coursAdd");
    }
}
