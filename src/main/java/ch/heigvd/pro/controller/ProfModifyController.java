/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : ProfModifyController.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Controlleur pour la page de modification des professeursm
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.controller.validation.VerifyUserEntry;
import ch.heigvd.pro.model.ModelTableProf;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;

public class ProfModifyController {
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

    private ModelTableProf profToModify;

    @FXML
    public void initialize(){
        acronymeField.setText(profToModify.getAcronyme());
        nomField.setText(profToModify.getNom());
        prenomField.setText(profToModify.getPrenom());
        mailField.setText(profToModify.getMail());
    }

    /**
     * Formulaire qui permet d'entrer toutes les informations liées à un cours
     * @throws IOException
     */
    @FXML
    public void register(ActionEvent event) throws IOException {

        Window owner = submitButton.getScene().getWindow();

        if(!inputValid()) return;

        profToModify.setOldAcronyme(profToModify.getAcronyme());
        profToModify.setAcronyme(acronymeField.getText());
        profToModify.setNom(nomField.getText());
        profToModify.setPrenom(prenomField.getText());
        profToModify.setMail(mailField.getText());

        boolean ok_request;
        try {
            profToModify.updateFromDB();
            ok_request = true;
        } catch (Exception e) {
            e.printStackTrace();
            ok_request = false;
        }
        if (ok_request)
            showAlert(Alert.AlertType.INFORMATION, owner, "Modification réussie!",
                    "La moodification a bien été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Modification échouée",
                    "Erreur lors de la modification", true);
        }
    }

    /**
     * Vérifie les entrées utilisateurs
     * @return Retourne false si l'entrée est vide ou invalide
     * @throws IOException
     */
    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        VerifyUserEntry verifyUserEntry = new VerifyUserEntry();

        if (acronymeField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un acronyme", false);
            return false;
        } else if(!verifyUserEntry.verifyEntryAcronym(acronymeField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'acronyme n'est pas au bon format (3 lettres majuscules)", false);
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
        } else if(!verifyUserEntry.verifyEntryMail(mailField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "Le mail n'est pas valide", false);
            return false;
        }

        return true;
    }

    /**
     * Bouton de validation/annulation qui nous fait revenir à l'onglet précédent
     * @throws IOException
     */
    @FXML
    private void OKButton() throws IOException {
        Tempus.changeTab(3);
    }

    /**
     * Fonction qui affiche une fenêtre d'alerte lors d'une action
     * @param alertType Type d'alerte à afficher
     * @param owner Bouton submit
     * @param title Titre de la fenêtre
     * @param message Message à afficher
     * @param menu True si il est nécessaire de retourner au précédent onglet
     * @throws IOException
     */
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message, boolean
                                  menu) throws IOException {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        if(menu) Tempus.changeTab(3);
    }

    /**
     * Setter
     */
    public void setProfToModify(ModelTableProf profAModifier) {
        this.profToModify = profAModifier;
    }
}
