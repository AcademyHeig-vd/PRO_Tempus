package ch.heigvd.pro.controller;

import ch.heigvd.pro.model.ModelEvenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelDay;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayViewControler {

    private static final String FXML_SOURCE = "view/dayView.fxml";

    private Date date; //la date du jour
    @FXML
    Text dateLabel; //pour contenir le jour du mois (numéro)
    @FXML
    TableView<ModelEvenement> evenementTable; //la table contenant les rappels
    @FXML
    TableColumn<ModelEvenement, String> nameEvenement; //le nom de l'évènement




    public DayViewControler (){
    }

    /**
     * Getter et setter
     */
    public void setDate(Date date){
        this.date = date;    }

    public Date getDate(){
        return date;
    }

    /**
     * Initialise la vue du jour
     */
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        ModelDay modelDay = new ModelDay(this.date);
        // Set des valeur du jours
        evenementTable.setItems(modelDay.getRappels());
        dateLabel.setText(Integer.toString(modelDay.getDate().toLocalDate().getDayOfMonth()));
        nameEvenement.setCellValueFactory(cellData -> cellData.getValue().getTitre());
    }

    /**
     * Charge le fxml relatif à ce controleur
     * @param date la date à laquelle fixer le jour
     * @return Un anchorPane contenant le jour
     * @throws IOException
     */
    public static AnchorPane loadFromFXML(Date date) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        DayViewControler dvc = new DayViewControler();
        dvc.setDate(date);
        loader.setController(dvc);
        loader.setLocation(Tempus.class.getResource(FXML_SOURCE));

        return (AnchorPane) loader.load();
    }

}
