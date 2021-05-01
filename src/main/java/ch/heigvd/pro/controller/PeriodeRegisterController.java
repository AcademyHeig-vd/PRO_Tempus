package ch.heigvd.pro.controller;

import ch.heigvd.pro.controller.validation.VerifyUserEntry;
import ch.heigvd.pro.model.ModelTableCoursEvenement;
import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelTablePeriode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;


public class PeriodeRegisterController {


    @FXML
    private TextField jourField;
    @FXML
    private TextField heureDebutField;
    @FXML
    private TextField heureFinField;
    @FXML
    private TextField salleField;
    @FXML
    private Button submitButton;
    @FXML
    private ComboBox<ModelTableCoursEvenement> cours = new ComboBox<>();

    ObservableList<ModelTableCoursEvenement> oblist = FXCollections.observableArrayList();

    /**
     * Formulaire qui permet d'entrer toutes les informations liées à un cours
     * @throws IOException
     */
    @FXML
    public void register(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        Window owner = submitButton.getScene().getWindow();
        ModelTableCoursEvenement  coursEvenement = cours.getSelectionModel().getSelectedItem();

        if(!inputValid()) return;

        int id = coursEvenement.getIdEvenement();
        String jour = jourField.getText();
        String heureDebut = heureDebutField.getText();
        String heureFin = heureFinField.getText();
        String salle = salleField.getText();

        // Connexion a la database
        boolean ok_request = ModelTablePeriode.insertRecordPeriode(id, jour, heureDebut, heureFin, salle);
        if (ok_request)
        showAlert(Alert.AlertType.INFORMATION, owner, "Ajout réussi!",
                "La nouvelle entrée a été effectuée !", true);
        else{
            showAlert(Alert.AlertType.ERROR, owner, "Ajout échoué",
                    "Erreur lors de l'insertion", true);
        }
    }

    /**
     * Méthode automatiquement appelée lors de l'invocation du FXML, permet d'afficher tous les champs du formulaire
     */
    @FXML
    public void initialize(){
        // Ajout d'une liste déroulante avec les différents cours
        try {
            oblist.addAll(ModelTableCoursEvenement.selectAllFromDB());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }
        cours.setItems(oblist);
    }

    /**
     * Vérifie les entrées utilisateurs
     * @return Retourne false si l'entrée est vide ou invalide
     * @throws IOException
     */
    private boolean inputValid() throws IOException {
        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursEvenement coursEvenement = cours.getSelectionModel().getSelectedItem();

        VerifyUserEntry verifyUserEntry = new VerifyUserEntry();

        ModelTablePeriode.Jour jour;
        jour = ModelTablePeriode.Jour.LUNDI;
        jour = jour.getJour(jourField.getText());

       if (coursEvenement == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le nom du cours", false);
            return false;
        }

        if (jourField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le jour de la semaine", false);
            return false;
        } else if(jour == null){
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "Le jour de la semaine n'a pas été écrit correctement, tout doit être en minuscule", false);
            return false;
        }

        if (heureDebutField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez l'heure de début", false);
            return false;
        } else if(!verifyUserEntry.verifyEntryHour(heureDebutField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'heure de début n'est pas au bon format (HH:MM)", false);
            return false;
        }

        if (heureFinField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez l'heure de fin", false);
            return false;
        } else if(!verifyUserEntry.verifyEntryHour(heureFinField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'heure de fin n'est pas au bon format (HH:MM)", false);
            return false;
        }

        if (salleField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le numéro de la salle", false);
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
        Tempus.changeTab(2);
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
        if(menu) Tempus.changeTab(2);
    }

}