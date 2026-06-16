package controller.upn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import common.exception.GeneralUserException;
import controller.upn.operator.Operator;
import dummy.controller.upn.DefaultUPNCore;
import dummy.controller.upn.operator.binary.DivOperator;
import model.Stack;

/**
 * JUnit 5 Testklasse zum Testen der {@link UPNCore} Implementierung.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
class UPNCoreTest
{
   /**
    * Zu testender Rechnerkern.
    */
   private UPNCore upn = null;

   /**
    * Erzeugt vor jedem Test einen neuen Dummy-Rechenkern.
    */
   @BeforeEach
   void setUp()
   {
      upn = new DefaultUPNCore();
   }

   /**
    * Tests des Startzustands.
    */
   @Nested
   @DisplayName("Startzustand")
   class InitialStateTest
   {
      /**
       * Testet die Anzeige beim Start.
       */
      @Test
      @DisplayName("Display zeigt 0.0")
      void testDisplayText()
      {
         assertEquals("0.0", upn.getDisplayText());
      }

      /**
       * Testet die Eingabezeile beim Start.
       */
      @Test
      @DisplayName("Keine Eingabezeile aktiv")
      void testInputString()
      {
         assertNull(upn.getInputString());
      }

      /**
       * Testet den Stack beim Start.
       */
      @Test
      @DisplayName("Stack ist leer")
      void testStackEmpty()
      {
         Stack<Double> stack = upn.getStack();
         assertTrue(stack.isEmpty());
      }

      /**
       * Testet LastX beim Start.
       */
      @Test
      @DisplayName("LastX ist 0.0")
      void testLastX()
      {
         assertEquals(0.0, upn.getLastX());
      }

      /**
       * Testet den Modus beim Start.
       */
      @Test
      @DisplayName("Start im Funktionsmodus")
      void testInputMode()
      {
         assertFalse(upn.isInputMode());
      }

      /**
       * Testet den Fehlerzustand beim Start.
       */
      @Test
      @DisplayName("Kein Fehlerzustand")
      void testError()
      {
         assertFalse(upn.hasError());
      }
   }

   /**
    * Tests der Zifferneingabe.
    */
   @Nested
   @DisplayName("Zifferneingabe")
   class InputDigitTest
   {
      /**
       * Testet die Eingabe der Ziffer 6.
       */
      @Test
      @DisplayName("Eingabe 6")
      void testDigitSix()
      {
         upn.inputDigit(6);

         assertTrue(upn.isInputMode());
         assertEquals("6", upn.getInputString());
         assertEquals("6", upn.getDisplayText());
      }

      /**
       * Testet die Eingabe mehrerer Ziffern.
       */
      @Test
      @DisplayName("Eingabe 6 dann 5")
      void testTwoDigits()
      {
         upn.inputDigit(6);
         upn.inputDigit(5);

         assertTrue(upn.isInputMode());
         assertEquals("65", upn.getInputString());
         assertEquals("65", upn.getDisplayText());
      }

      /**
       * Testet eine zu kleine Ziffer.
       */
      @Test
      @DisplayName("Ziffer -1 ist unzulässig")
      void testDigitTooSmall()
      {
         assertThrows(IllegalArgumentException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(-1);
            }
         });
      }

      /**
       * Testet eine zu große Ziffer.
       */
      @Test
      @DisplayName("Ziffer 10 ist unzulässig")
      void testDigitTooLarge()
      {
         assertThrows(IllegalArgumentException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(10);
            }
         });
      }
   }

   /**
    * Tests des Dezimalpunkts.
    */
   @Nested
   @DisplayName("Dezimalpunkt")
   class DecimalPointTest
   {
      /**
       * Testet den Dezimalpunkt als erste Eingabe.
       */
      @Test
      @DisplayName("Dezimalpunkt startet mit 0.")
      void testDecimalPointFirst()
      {
         upn.inputDecimalPoint();

         assertTrue(upn.isInputMode());
         assertEquals("0.", upn.getInputString());
         assertEquals("0.", upn.getDisplayText());
      }

      /**
       * Testet den Dezimalpunkt nach einer Ziffer.
       */
      @Test
      @DisplayName("6 dann Dezimalpunkt")
      void testDecimalPointAfterDigit()
      {
         upn.inputDigit(6);
         upn.inputDecimalPoint();

         assertEquals("6.", upn.getInputString());
         assertEquals("6.", upn.getDisplayText());
      }

      /**
       * Testet, dass kein zweiter Dezimalpunkt angehängt wird.
       */
      @Test
      @DisplayName("Kein zweiter Dezimalpunkt")
      void testNoSecondDecimalPoint()
      {
         upn.inputDigit(6);
         upn.inputDecimalPoint();
         upn.inputDecimalPoint();

         assertEquals("6.", upn.getInputString());
         assertEquals("6.", upn.getDisplayText());
      }
   }

   /**
    * Tests des Vorzeichenwechsels im Eingabemodus.
    */
   @Nested
   @DisplayName("+/- im Eingabemodus")
   class ChangeSignInputModeTest
   {
      /**
       * Testet den Vorzeichenwechsel einer positiven Eingabe.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("6 wird zu -6")
      void testChangeSignToNegative() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.changeSign();

         assertTrue(upn.isInputMode());
         assertEquals("-6", upn.getInputString());
         assertEquals("-6", upn.getDisplayText());
      }

      /**
       * Testet das Zurückwechseln auf positiv.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("-6 wird wieder zu 6")
      void testChangeSignBackToPositive() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.changeSign();
         upn.changeSign();

         assertTrue(upn.isInputMode());
         assertEquals("6", upn.getInputString());
         assertEquals("6", upn.getDisplayText());
      }
   }

   /**
    * Test Division.
    */
   @Nested
   @DisplayName("/")
   class DivTest
   {
      /**
       * Zu testender Operator.
       */
      private final Operator operator = new DivOperator();

      /**
       * Testet 6 / 2 = 3.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("6 / 2 = 3")
      void testOk() throws GeneralUserException
      {
         upn.clear();
         upn.inputDigit(6);
         upn.enter();
         upn.inputDigit(2);
         upn.applyOperator(operator);

         Stack<Double> stack = upn.getStack();
         assertTrue(stack.size() > 0);

         assertEquals(3.0, stack.getX());
         assertEquals(2.0, upn.getLastX());
      }

      /**
       * Testet Division durch 0.
       */
      @Test
      @DisplayName("6 / 0")
      void testDiv0()
      {
         assertThrows(GeneralUserException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.clear();
               upn.inputDigit(6);
               upn.enter();
               upn.inputDigit(0);
               upn.applyOperator(operator);
            }
         });
      }
   }
}