/*
 -----------------------------------------------------------------------------------
 Laboratoire : PRO - Projet de semestre
 Fichier     : Tempus.java
 Auteur(s)   : Robin Gaudin, Walid Massaoudi, Noémie Plancherel, Lev Pozniakoff, Axel Vallon
 Date        : 20.05.2021
 But         : Classe principale de l'application
 Remarque(s) : -
 -----------------------------------------------------------------------------------
*/

package ch.heigvd.pro;

import ch.heigvd.pro.controller.CalendarPageControler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Tempus extends Application {
        private static Scene scene;
    private static TabPane tabPane;
    private static AnchorPane root;
    private static Tab main;
    private static Tab cours;
    private static Tab periode;
    private static Tab prof;
    private static Tab rappel;
    private static Tab calendrier;

    /**
     * Méthode de démarrage de l'application
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("view/Tempus");
        // Création des onglets
        tabPane = new TabPane();

        // Ajout de tous les onglets
        main = new Tab("Main");
        cours = new Tab("Cours");
        periode = new Tab("Période");
        prof = new Tab("Professeur");
        rappel = new Tab("Rappel");
        calendrier = new Tab("Calendrier");

        VBox v1 = new VBox();

        // Load de toutes les classes
        updateTab();

        // Ne pas permette aux users de fermer les onglets
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Ajouter les tabs dans l'application
        tabPane.getTabs().addAll(main, cours, periode, prof, rappel, calendrier);

        // Redimensionner à la bonne taille
        tabPane.prefWidthProperty().bind(stage.widthProperty());

        v1.getChildren().addAll(tabPane);
        root = new AnchorPane();
        root.getChildren().addAll(v1);
        scene = new Scene(root, 1150, 1000);
        scene.getStylesheets().add("tabPane.css");
        stage.setScene(scene);
        // Icone de l'application, à tester
        //stage.getIcons().add(new Image("file:images/logo.png"));
        stage.show();
    }

    /**
     * Méthode de mise à jour des onglets
     * @throws IOException
     */
    public static void updateTab() throws IOException {
        main.setContent(loadFXML("view/primary"));
        cours.setContent(loadFXML("view/coursAdd"));
        periode.setContent(loadFXML("view/periodeAdd"));
        prof.setContent(loadFXML("view/profAdd"));
        rappel.setContent(loadFXML("view/rappelAdd"));
        calendrier.setContent(CalendarPageControler.loadFromFXMLDocument());
    }

    /**
     * Méthode lorsque l'on change d'onglet
     * @param index
     * @throws IOException
     */
    public static void changeTab(int index) throws IOException {
        scene.setRoot(root);
        tabPane.getSelectionModel().select(index);
        updateTab();
    }

    /**
     * Set la page principale de l'application (première page qui va s'afficher lors du lancement de l'application)
     * @param fxml
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Charge les fichiers FXML
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Tempus.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Obtenir la scène graphique
     * @return
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * Méthode main
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}