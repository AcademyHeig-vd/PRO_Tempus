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

/**
 * JavaFX App
 */
public class Tempus extends Application {

    private static Scene scene;
    private static TabPane tabPane;
    private static AnchorPane root;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("view/Tempus");
        // Création des onglets
        tabPane = new TabPane();

        // Ajout de tous les onglets
        Tab main = new Tab("Main");
        Tab cours = new Tab("Cours");
        Tab periode = new Tab("Période");
        Tab prof = new Tab("Professeur");
        Tab rappel = new Tab("Rappel");
        Tab calendrier = new Tab("Calendrier");

        VBox v1 = new VBox();

        // Load de toutes les classes
        main.setContent(loadFXML("view/primary"));
        cours.setContent(loadFXML("view/coursAdd"));
        periode.setContent(loadFXML("view/periodeAdd"));
        prof.setContent(loadFXML("view/profAdd"));
        rappel.setContent(loadFXML("view/rappelAdd"));
        // TODO: à contrôler que l'appel au calendrier est correct
        calendrier.setContent(CalendarPageControler.loadFromFXMLDocument());

        // Ne pas permette aux users de fermer les onglets
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Ajouter les tabs dans l'application
        tabPane.getTabs().addAll(main, cours, periode, prof, rappel, calendrier);

        // Redimensionner à la bonne taille
        tabPane.prefWidthProperty().bind(stage.widthProperty());

        v1.getChildren().addAll(tabPane);
        root = new AnchorPane();
        root.getChildren().addAll(v1);
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void changeTab(int index) throws IOException {
        scene.setRoot(root);
        tabPane.getSelectionModel().select(index);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Tempus.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Scene getScene() {
        return scene;
    }
}