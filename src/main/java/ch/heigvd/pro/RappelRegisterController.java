package ch.heigvd.pro;

import ch.heigvd.pro.Connexion.dbConnexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class RappelRegisterController {
    @FXML
    private TextField contenuField;
    @FXML
    private TextField lienField;
    @FXML
    private TextField heureField;
    @FXML
    private Button submitButton;


    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        System.out.println(contenuField.getText());
        System.out.println(lienField.getText());
        System.out.println(heureField.getText());

        if(!inputValid()) return;

        String contenu = contenuField.getText();
        String lien = lienField.getText();
        String heure = heureField.getText();

        dbConnexion db = new dbConnexion();
        db.insertRecordRappel(1, contenu, lien, heure);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Ajout réussi!",
                "La nouvelle entrée a été effectuée !", true);
    }

    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        if (contenuField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un contenu", false);
            return false;
        }
        if (lienField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un lien", false);
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
        if(menu) Tempus.setRoot("rappelAdd");
    }

    @FXML
    private void OKButton() throws IOException {
        Tempus.setRoot("rappelAdd");
    }

}
