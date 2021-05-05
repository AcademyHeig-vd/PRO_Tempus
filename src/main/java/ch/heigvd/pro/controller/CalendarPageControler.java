package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import ch.heigvd.pro.view.CalendarGridView;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;


public class CalendarPageControler {

    private static final String FXML_SOURCE = "view/calendarPage.fxml";
    private YearMonth currentYearMonth; //variable pour stocker le mois/année courants



    @FXML
    private
    Button toMainMenu; //bouton vers menu principal

    @FXML
    private
    AnchorPane calendarSpace; //espace pour la grille du calendrier

    @FXML
    private
    Text monthLabel; //label du mois et année courants
    @FXML
    private
    Text yearLabel; //label du mois et année courants
    @FXML
    private
    ImageView nextYear; //label du mois et année courants
    @FXML
    private
    ImageView nextMonth; //label du mois et année courants
    @FXML
    private
    ImageView previousYear; //label du mois et année courants
    @FXML
    private
    ImageView previousMonth; //label du mois et année courants


    CalendarPageControler(){}

    //TODO faire la requête par mois
    public void initialize() throws IOException, SQLException, ClassNotFoundException {
        disableAllButtons();
        currentYearMonth = YearMonth.now();
        //ajout du calendrier à la page
        CalendarGridView calendar = new CalendarGridView(currentYearMonth);
        calendarSpace.getChildren().add(calendar.getView());

        //Création des actions des labels
        //mois précédent
        previousMonth.setOnMouseClicked(e -> {
            try {
                addOperationMonths(-1);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        //mois suivant
        nextMonth.setOnMouseClicked(e -> {
            try {
                addOperationMonths(1);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        //année précédente
        previousYear.setOnMouseClicked(e -> {
            try {
                addOperationMonths(-12);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        //mois suivant
        nextYear.setOnMouseClicked(e -> {
            try {
                addOperationMonths(12);
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        //menu principal
        toMainMenu.setOnAction(e -> {
            try {
                switchToPrimary();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        //Pour l'affichage du mois et année
        monthLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE));
        yearLabel.setText(String.valueOf(currentYearMonth.getYear()));
        enableAllButtons();
    }

    public static HBox loadFromFXMLDocument() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        CalendarPageControler cpc = new CalendarPageControler();
        loader.setController(cpc);
        loader.setLocation(Tempus.class.getResource(FXML_SOURCE));
        return (HBox) loader.load();
    }

    /**
     * Ajoute un nombre de mois au calendrier en le mettant à jour
     * @param monthsToAdd , le nombre de mois à ajouter
     * @throws IOException
     */
    private void addOperationMonths(int monthsToAdd) throws IOException, SQLException, ClassNotFoundException {
        //nettoie le calendrier
        calendarSpace.getChildren().clear();
        //remet le nouveau mois
        currentYearMonth = monthsToAdd > 0 ?
                currentYearMonth.plusMonths(monthsToAdd) : currentYearMonth.minusMonths(-monthsToAdd);
        monthLabel.setText(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE));
        yearLabel.setText(String.valueOf(currentYearMonth.getYear()));

        //ajoute la nouvelle grid
        calendarSpace.getChildren().add(new CalendarGridView(currentYearMonth).getView());
    }

    /**
     * passe à la vue primaire (menu principal)
     * @throws IOException
     */
    @FXML
    private void switchToPrimary() throws IOException {
        Tempus.changeTab(0);
    }

    private void disableAllButtons(){
        toMainMenu.setDisable(true);
        previousMonth.setDisable(true);
        previousYear.setDisable(true);
        nextMonth.setDisable(true);
        nextYear.setDisable(true);
    }

    private void enableAllButtons(){
        toMainMenu.setDisable(false);
        previousMonth.setDisable(false);
        previousYear.setDisable(false);
        nextMonth.setDisable(false);
        nextYear.setDisable(false);
    }
}
