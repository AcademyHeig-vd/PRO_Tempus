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
    private TextField heureDebutField;
    @FXML
    private TextField heureFinField;
    @FXML
    private TextField salleField;
    @FXML
    private Button submitButton;
    @FXML
    private ComboBox<ModelTableCoursEvenement> cours = new ComboBox<>();
    @FXML
    private ComboBox<String> jourComboBox = new ComboBox<>();

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
        String jour = jourComboBox.getValue();
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

        ObservableList<String> options = FXCollections.observableArrayList();
        ModelTablePeriode.Jour[] jours = ModelTablePeriode.Jour.values();
        for(ModelTablePeriode.Jour j : jours){
            options.add(j.getValue());
        }
        jourComboBox.setItems(options);

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

       if (coursEvenement == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le nom du cours", false);
            return false;
        }

        if (jourComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez le jour de la semaine", false);
            return false;
        }

        if (heureDebutField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez l'heure de début", false);
            return false;
        } else if(!verifyUserEntry.verificationEntreeHeure(heureDebutField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'heure de début n'est pas au bon format (HH:MM)", false);
            return false;
        }

        if (heureFinField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez l'heure de fin", false);
            return false;
        } else if(!verifyUserEntry.verificationEntreeHeure(heureFinField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'heure de fin n'est pas au bon format (HH:MM)", false);
            return false;
        }

        if (!verifyUserEntry.verificationHeureDebutPlusPetiteHeureFin(heureDebutField.getText(), heureFinField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "L'heure de début doit être plus petite que l'heure de fin", false);
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