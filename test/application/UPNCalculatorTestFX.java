package application;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import controller.upn.DefaultUPNCore;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.CalculatorPane;

/**
 * TestFX-Tests für die JavaFX-Oberfläche des UPN-Rechners.
 *
 * <p>
 * Die Tests bedienen den Rechner so wie ein Benutzer: Sie klicken Buttons über
 * ihre JavaFX-Ids an und prüfen anschließend den Text im Display. Dadurch wird
 * geprüft, ob die Oberfläche korrekt mit dem {@link controller.upn.UPNCore}
 * zusammenarbeitet.
 * </p>
 *
 * <p>
 * Getestet werden mathematische Operationen und Stackoperationen, wie es in
 * Aufgabe 7 gefordert ist.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
class UPNCalculatorTestFX extends ApplicationTest
{
   /**
    * Breite des Testfensters.
    */
   private static final int WINDOW_WIDTH = 380;

   /**
    * Höhe des Testfensters.
    */
   private static final int WINDOW_HEIGHT = 520;

   /**
    * Id des Display-Labels.
    */
   private static final String ID_DISPLAY = "#displayLabel";

   /**
    * Id des Status-Labels.
    */
   private static final String ID_STATUS = "#statusLabel";

   /**
    * Id des Buttons 0.
    */
   private static final String ID_BUTTON_0 = "#button0";

   /**
    * Id des Buttons 1.
    */
   private static final String ID_BUTTON_1 = "#button1";

   /**
    * Id des Buttons 2.
    */
   private static final String ID_BUTTON_2 = "#button2";

   /**
    * Id des Buttons 3.
    */
   private static final String ID_BUTTON_3 = "#button3";

   /**
    * Id des Buttons 4.
    */
   private static final String ID_BUTTON_4 = "#button4";

   /**
    * Id des Buttons 5.
    */
   private static final String ID_BUTTON_5 = "#button5";

   /**
    * Id des Buttons 6.
    */
   private static final String ID_BUTTON_6 = "#button6";

   /**
    * Id des Buttons 7.
    */
   private static final String ID_BUTTON_7 = "#button7";

   /**
    * Id des Buttons 8.
    */
   private static final String ID_BUTTON_8 = "#button8";

   /**
    * Id des Buttons 9.
    */
   private static final String ID_BUTTON_9 = "#button9";

   /**
    * Id des Dezimalpunkt-Buttons.
    */
   private static final String ID_BUTTON_DECIMAL_POINT = "#buttonDecimalPoint";

   /**
    * Id des Enter-Buttons.
    */
   private static final String ID_BUTTON_ENTER = "#buttonEnter";

   /**
    * Id des Additions-Buttons.
    */
   private static final String ID_BUTTON_ADD = "#buttonAdd";

   /**
    * Id des Subtraktions-Buttons.
    */
   private static final String ID_BUTTON_SUBTRACT = "#buttonSubtract";

   /**
    * Id des Multiplikations-Buttons.
    */
   private static final String ID_BUTTON_MULTIPLY = "#buttonMultiply";

   /**
    * Id des Divisions-Buttons.
    */
   private static final String ID_BUTTON_DIVIDE = "#buttonDivide";

   /**
    * Id des CLR-Buttons.
    */
   private static final String ID_BUTTON_CLEAR = "#buttonClear";

   /**
    * Id des CLX-Buttons.
    */
   private static final String ID_BUTTON_CLEAR_X = "#buttonClearX";

   /**
    * Id des LastX-Buttons.
    */
   private static final String ID_BUTTON_LAST_X = "#buttonLastX";

   /**
    * Id des X&lt;&gt;Y-Buttons.
    */
   private static final String ID_BUTTON_SWAP_XY = "#buttonSwapXY";

   /**
    * Id des Vorzeichenwechsel-Buttons.
    */
   private static final String ID_BUTTON_CHANGE_SIGN = "#buttonChangeSign";

   /**
    * Id des Kehrwert-Buttons.
    */
   private static final String ID_BUTTON_RECIPROCAL = "#buttonReciprocal";

   /**
    * Id des Potenz-Buttons.
    */
   private static final String ID_BUTTON_POWER = "#buttonPower";

   /**
    * Id des Quadratwurzel-Buttons.
    */
   private static final String ID_BUTTON_SQUARE_ROOT = "#buttonSquareRoot";

   /**
    * Id des Logarithmus-Buttons.
    */
   private static final String ID_BUTTON_LN = "#buttonLn";

   /**
    * Id des Sinus-Buttons.
    */
   private static final String ID_BUTTON_SIN = "#buttonSin";

   /**
    * Id des Cosinus-Buttons.
    */
   private static final String ID_BUTTON_COS = "#buttonCos";

   /**
    * Id des Tangens-Buttons.
    */
   private static final String ID_BUTTON_TAN = "#buttonTan";

   /**
    * Erwarteter Startwert im Display.
    */
   private static final String DISPLAY_ZERO = "0.0";

   /**
    * Erwarteter Fehlertext im Display.
    */
   private static final String DISPLAY_ERROR = "Err";

   /**
    * Erstellt vor jedem Test eine neue Oberfläche mit einem neuen Rechenkern.
    *
    * @param stage
    *           JavaFX-Fenster für den Test
    */
   @Override
   public void start(Stage stage)
   {
      CalculatorPane root = new CalculatorPane(new DefaultUPNCore());
      Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

      stage.setScene(scene);
      stage.show();
   }

   /**
    * Testet den Startzustand der Oberfläche.
    */
   @Test
   @DisplayName("Startzustand der Oberfläche")
   void testInitialDisplay()
   {
      verifyThat(ID_DISPLAY, hasText(DISPLAY_ZERO));
      verifyThat(ID_STATUS, hasText("Stack: 0 Elemente"));
   }

   /**
    * Testet die Zifferneingabe.
    */
   @Test
   @DisplayName("Zifferneingabe")
   void testDigitInput()
   {
      clickOn(ID_BUTTON_7);
      clickOn(ID_BUTTON_8);

      verifyThat(ID_DISPLAY, hasText("78"));
   }

   /**
    * Testet die Eingabe einer Dezimalzahl.
    */
   @Test
   @DisplayName("Dezimalzahleingabe")
   void testDecimalInput()
   {
      clickOn(ID_BUTTON_1);
      clickOn(ID_BUTTON_DECIMAL_POINT);
      clickOn(ID_BUTTON_5);

      verifyThat(ID_DISPLAY, hasText("1.5"));
   }

   /**
    * Testet den Vorzeichenwechsel im Eingabemodus.
    */
   @Test
   @DisplayName("Vorzeichenwechsel")
   void testChangeSign()
   {
      clickOn(ID_BUTTON_5);
      clickOn(ID_BUTTON_CHANGE_SIGN);

      verifyThat(ID_DISPLAY, hasText("-5"));
   }

   /**
    * Testet die Addition.
    */
   @Test
   @DisplayName("Addition")
   void testAddition()
   {
      clickOn(ID_BUTTON_6);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_ADD);

      verifyThat(ID_DISPLAY, hasText("8.0"));
   }

   /**
    * Testet die Subtraktion.
    */
   @Test
   @DisplayName("Subtraktion")
   void testSubtraction()
   {
      clickOn(ID_BUTTON_6);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_SUBTRACT);

      verifyThat(ID_DISPLAY, hasText("4.0"));
   }

   /**
    * Testet die Multiplikation.
    */
   @Test
   @DisplayName("Multiplikation")
   void testMultiplication()
   {
      clickOn(ID_BUTTON_6);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_MULTIPLY);

      verifyThat(ID_DISPLAY, hasText("12.0"));
   }

   /**
    * Testet die Division.
    */
   @Test
   @DisplayName("Division")
   void testDivision()
   {
      clickOn(ID_BUTTON_6);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_DIVIDE);

      verifyThat(ID_DISPLAY, hasText("3.0"));
   }

   /**
    * Testet die Potenzoperation Y^X.
    */
   @Test
   @DisplayName("Potenz Y hoch X")
   void testPower()
   {
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_3);
      clickOn(ID_BUTTON_POWER);

      verifyThat(ID_DISPLAY, hasText("8.0"));
   }

   /**
    * Testet den Kehrwertoperator.
    */
   @Test
   @DisplayName("Kehrwert")
   void testReciprocal()
   {
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_RECIPROCAL);

      verifyThat(ID_DISPLAY, hasText("0.5"));
   }

   /**
    * Testet die Quadratwurzel.
    */
   @Test
   @DisplayName("Quadratwurzel")
   void testSquareRoot()
   {
      clickOn(ID_BUTTON_9);
      clickOn(ID_BUTTON_SQUARE_ROOT);

      verifyThat(ID_DISPLAY, hasText("3.0"));
   }

   /**
    * Testet den natürlichen Logarithmus.
    */
   @Test
   @DisplayName("Natürlicher Logarithmus")
   void testLn()
   {
      clickOn(ID_BUTTON_1);
      clickOn(ID_BUTTON_LN);

      verifyThat(ID_DISPLAY, hasText("0.0"));
   }

   /**
    * Testet den Sinus.
    */
   @Test
   @DisplayName("Sinus")
   void testSin()
   {
      clickOn(ID_BUTTON_0);
      clickOn(ID_BUTTON_SIN);

      verifyThat(ID_DISPLAY, hasText("0.0"));
   }

   /**
    * Testet den Cosinus.
    */
   @Test
   @DisplayName("Cosinus")
   void testCos()
   {
      clickOn(ID_BUTTON_0);
      clickOn(ID_BUTTON_COS);

      verifyThat(ID_DISPLAY, hasText("1.0"));
   }

   /**
    * Testet den Tangens.
    */
   @Test
   @DisplayName("Tangens")
   void testTan()
   {
      clickOn(ID_BUTTON_0);
      clickOn(ID_BUTTON_TAN);

      verifyThat(ID_DISPLAY, hasText("0.0"));
   }

   /**
    * Testet die Enter-Taste als Stackoperation.
    */
   @Test
   @DisplayName("Enter dupliziert X")
   void testEnterDuplicatesX()
   {
      clickOn(ID_BUTTON_5);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_ADD);

      verifyThat(ID_DISPLAY, hasText("10.0"));
   }

   /**
    * Testet die CLX-Taste.
    */
   @Test
   @DisplayName("CLX löscht X")
   void testClearX()
   {
      clickOn(ID_BUTTON_7);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_CLEAR_X);

      verifyThat(ID_DISPLAY, hasText(DISPLAY_ZERO));
      verifyThat(ID_STATUS, hasText("Stack: 0 Elemente"));
   }

   /**
    * Testet die CLR-Taste.
    */
   @Test
   @DisplayName("CLR löscht den Rechner")
   void testClear()
   {
      clickOn(ID_BUTTON_7);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_8);
      clickOn(ID_BUTTON_CLEAR);

      verifyThat(ID_DISPLAY, hasText(DISPLAY_ZERO));
      verifyThat(ID_STATUS, hasText("Stack: 0 Elemente"));
   }

   /**
    * Testet die Taste X&lt;&gt;Y.
    */
   @Test
   @DisplayName("X und Y vertauschen")
   void testSwapXY()
   {
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_5);
      clickOn(ID_BUTTON_SWAP_XY);
      clickOn(ID_BUTTON_SUBTRACT);

      verifyThat(ID_DISPLAY, hasText("3.0"));
   }

   /**
    * Testet die LastX-Taste.
    */
   @Test
   @DisplayName("LastX wieder auf den Stack legen")
   void testLastX()
   {
      clickOn(ID_BUTTON_6);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_2);
      clickOn(ID_BUTTON_DIVIDE);
      clickOn(ID_BUTTON_LAST_X);

      verifyThat(ID_DISPLAY, hasText("2.0"));
   }

   /**
    * Testet einen Fehlerfall über die Oberfläche.
    */
   @Test
   @DisplayName("Division durch 0 zeigt Fehler")
   void testDivisionByZeroShowsError()
   {
      clickOn(ID_BUTTON_6);
      clickOn(ID_BUTTON_ENTER);
      clickOn(ID_BUTTON_0);
      clickOn(ID_BUTTON_DIVIDE);

      verifyThat(ID_DISPLAY, hasText(DISPLAY_ERROR));
      verifyThat(ID_STATUS, hasText("Division durch 0 ist nicht erlaubt."));
   }
}