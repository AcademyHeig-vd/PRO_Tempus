package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.connexion.dbConnexion;
import ch.heigvd.pro.controller.validation.VerifyUserEntry;
import ch.heigvd.pro.model.ModelTableCours;
import ch.heigvd.pro.model.ModelTableCoursProf;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class CoursModifyController {
    @FXML
    private TextField titreField;
    @FXML
    private TextField dateDebutField;
    @FXML
    private TextField dateEcheanceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ComboBox<ModelTableCoursProf> professeur = new ComboBox<>();
    @FXML
    private Button submitButton;


    ModelTableCours coursToModify;

    ObservableList<ModelTableCoursProf> oblist = FXCollections.observableArrayList();

    /**
     * Formulaire qui permet d'entrer toutes les informations liées à un cours
     * @throws IOException
     */
    @FXML
    public void register() throws IOException, ParseException {

        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursProf coursProf = professeur.getSelectionModel().getSelectedItem();

        if(!inputValid()) return;

        String acronyme = coursProf.getAcronyme();
        String titre = titreField.getText();
        String dateDebut = dateDebutField.getText();
        String dateEcheance = dateEcheanceField.getText();
        String description = descriptionField.getText();

        String[] dateDebutSeparee = dateDebut.split("\\.");
        String[] dateEcheanceSeparee = dateEcheance.split("\\.");
        dateDebut = dateDebutSeparee[2] + "-" + dateDebutSeparee[1] + "-" + dateDebutSeparee[0];
        dateEcheance = dateEcheanceSeparee[2] + "-" + dateEcheanceSeparee[1] + "-" + dateEcheanceSeparee[0];

        boolean ok_request;
        try {
            coursToModify.deleteFromDB();
            ok_request = true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            ok_request = false;
        }
        dbConnexion db = new dbConnexion();
        if (ok_request) {
            int idEvenement = db.insertRecordEvenement(titre, dateDebut, dateEcheance, description);
            //db.insertRecordCours(idEvenement, acronyme);
            ok_request = ModelTableCours.insertCoursInDB(idEvenement, acronyme);
        }
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
        titreField.setText(coursToModify.getTitre());
        dateDebutField.setText(coursToModify.getDateDebut());
        dateEcheanceField.setText(coursToModify.getDateEcheance());
        descriptionField.setText(coursToModify.getDescription());
        try {
            oblist.addAll(ModelTableCoursProf.getAllAcronymProf());
        } catch (SQLException | ClassNotFoundException e){
            e.getMessage();
        }
        professeur.setItems(oblist);
        professeur.getSelectionModel().select(new ModelTableCoursProf(coursToModify.getAcronyme()));
    }

    /**
     * Vérifie les entrées utilisateurs
     * @return Retourne false si l'entrée est vide ou invalide
     * @throws IOException
     * @throws ParseException
     */
    private boolean inputValid() throws IOException, ParseException {
        Window owner = submitButton.getScene().getWindow();

        ModelTableCoursProf coursProf = professeur.getSelectionModel().getSelectedItem();

        VerifyUserEntry verifyUserEntry = new VerifyUserEntry();

        if (titreField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un titre", false);
            return false;
        }

        if (dateDebutField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date de début", false);
            return false;
        } else if (!verifyUserEntry.verifyEntryDate(dateDebutField.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "La date de début n'est pas au bon format (jj.mm.aaaa)", false);
            return false;
        }

        if (dateEcheanceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez une date d'échéance", false);
            return false;
        } else if (!verifyUserEntry.verifyEntryDate(dateEcheanceField.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "La date d'échéance n'est pas au bon format (jj.mm.aaaa)", false);
            return false;
        }

        if (!verifyUserEntry.verifyDateBeginSmallerDateEnd(dateDebutField.getText(), dateEcheanceField.getText())) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "La date de début doit être plus petite que la d'échéance", false);
            return false;
        }

        if (coursProf == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Erreur de formulaire",
                    "S'il-vous-plaît entrez un professeur", false);
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
        Tempus.changeTab(1);
    }

    public void setCoursToModify(ModelTableCours coursToModify) {
        this.coursToModify = coursToModify;
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
        if(menu) Tempus.changeTab(1);
    }

}
