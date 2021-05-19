/*
package ch.heigvd.pro.controller;

import ch.heigvd.pro.Tempus;
import ch.heigvd.pro.model.ModelEvenement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class WelcomeControler {
    private static String FXML_SOURCE = "view/welcomeView.fxml";

    @FXML
    private
    MediaView tempusIntro; //la vidéo à jouer

    public WelcomeControler (){
    }


    public void initialize(){
        MediaPlayer mp = new MediaPlayer(new Media("src/main/ressources/ch.heigvd.pro.view/images/Tempus.mp4"));
        tempusIntro.setMediaPlayer(mp);

    }

    /**
     * Charge le fxml relatif à ce controleur
     * @return Un anchorPane contenant le jour
     * @throws IOException

    public static AnchorPane loadFromFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        WelcomeControler wc = new WelcomeControler();
        loader.setController(wc);
        loader.setLocation(Tempus.class.getResource(FXML_SOURCE));

        return (AnchorPane) loader.load();
    }


}*/
