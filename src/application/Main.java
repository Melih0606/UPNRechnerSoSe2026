package application;

import common.application.Config;
import common.logger.ExceptionLogger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Startet die JavaFX-Anwendung für Aufgabe 6.
 *
 * @author Kevin Piotrowski, Melih Acar und Dmitrij Ogulev
 */
public class Main extends Application
{
   /**
    * Fenstertitel.
    */
   private static final String WINDOW_TITLE = "Aufgabe 6";

   /**
    * Fensterbreite.
    */
   private static final int WINDOW_WIDTH = 600;

   /**
    * Fensterhöhe.
    */
   private static final int WINDOW_HEIGHT = 400;

   /**
    * Einstiegspunkt der Anwendung.
    *
    * @param args
    *           Kommandozeilenargumente.
    */
   public static void main(String[] args)
   {
      try
      {
         launch(args);
      }
      catch (Exception e)
      {
         ExceptionLogger.errorLog(Config.LOGFILE_NAME, e);

         if (Config.IS_DEVELOP)
         {
            e.printStackTrace();
         }
      }
   }

   /**
    * Erzeugt und zeigt das Hauptfenster an.
    *
    * @param primaryStage
    *           Hauptfenster der Anwendung.
    */
   @Override
   public void start(Stage primaryStage)
   {
      try
      {
         BorderPane root = new BorderPane();
         Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

         primaryStage.setTitle(WINDOW_TITLE);
         primaryStage.setScene(scene);
         primaryStage.show();
      }
      catch (Exception e)
      {
         Alert alert = new Alert(AlertType.ERROR,
               "Unbekannter Fehler.\nSiehe Protokolldatei.", ButtonType.OK);
         alert.showAndWait();

         ExceptionLogger.errorLog(Config.LOGFILE_NAME, e);

         if (Config.IS_DEVELOP)
         {
            e.printStackTrace();
         }
      }
   }
}
