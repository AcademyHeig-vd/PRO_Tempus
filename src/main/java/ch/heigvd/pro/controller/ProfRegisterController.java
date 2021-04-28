package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;

import ch.heigvd.pro.model.ModelTableProf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;

public class ProfRegisterController {
    @FXML
    private TextField acronymeField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField mailField;
    @FXML
    private Button submitButton;


    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        if(!inputValid()) return;

        String acronyme = acronymeField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String mail = mailField.getText();

        boolean ok_request = ModelTableProf.insertProfInDB(acronyme, nom, prenom, mail);
        if (ok_request)
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                    "La nouvelle entrée a été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Ajout échoué",
                    "Erreur lors de l'insertion", true);
        }
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        if (acronymeField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un acronyme", false);
            return false;
        }
        if (nomField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un nom", false);
            return false;
        }
        if (prenomField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un prénom", false);
            return false;
        }
        if (mailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une adresse mail", false);
            return false;
        }

        return true;
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message, boolean
                                  menu) throws IOException {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        if(menu) Tempus.setRoot("view/primary");
    }

    @FXML
    private void OKButton() throws IOException {
        Tempus.setRoot("view/primary");
    }
}
