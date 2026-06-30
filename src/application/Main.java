package application;

import controller.upn.DefaultUPNCore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.CalculatorPane;

/**
 * Startet die JavaFX-Anwendung für Aufgabe 6c.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class Main extends Application {

    /**
     * Fenstertitel der Anwendung.
     */
    private static final String WINDOW_TITLE = "UPN-Rechner - Aufgabe 6d";

    /**
     * Breite des Hauptfensters.
     */
    private static final int WINDOW_WIDTH = 380;

    /**
     * Höhe des Hauptfensters.
     */
    private static final int WINDOW_HEIGHT = 520;

    /**
     * Einstiegspunkt der Anwendung.
     *
     * @param args
     *            Kommandozeilenargumente
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Erzeugt und zeigt das Hauptfenster an.
     *
     * @param primaryStage
     *            Hauptfenster der Anwendung
     */
    @Override
    public void start(Stage primaryStage) {
        CalculatorPane root = new CalculatorPane(new DefaultUPNCore());
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}