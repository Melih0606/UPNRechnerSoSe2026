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
    * Tests der Enter-Taste.
    */
   @Nested
   @DisplayName("Enter")
   class EnterTest
   {
      /**
       * Testet Enter im Eingabemodus.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("Eingabe 6 und Enter")
      void testEnterFromInputMode() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();

         assertFalse(upn.isInputMode());
         assertNull(upn.getInputString());
         assertEquals("6.0", upn.getDisplayText());

         Stack<Double> stack = upn.getStack();
         assertEquals(1, stack.size());
         assertEquals(6.0, stack.getX());
      }

      /**
       * Testet Enter im Funktionsmodus bei leerem Stack.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("Enter bei leerem Stack legt 0.0 auf den Stack")
      void testEnterOnEmptyStack() throws GeneralUserException
      {
         upn.clear();
         upn.enter();

         Stack<Double> stack = upn.getStack();
         assertEquals(1, stack.size());
         assertEquals(0.0, stack.getX());
         assertEquals("0.0", upn.getDisplayText());
      }

      /**
       * Testet Enter im Funktionsmodus bei vorhandenem X-Register.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("Enter im Funktionsmodus dupliziert X")
      void testEnterDuplicatesX() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.enter();

         Stack<Double> stack = upn.getStack();
         assertEquals(2, stack.size());
         assertEquals(6.0, stack.getX());
         assertEquals("6.0", upn.getDisplayText());
      }
   }

   /**
    * Tests der CLR-Taste.
    */
   @Nested
   @DisplayName("CLR")
   class ClearTest
   {
      /**
       * Testet das vollständige Zurücksetzen des Rechners.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("CLR setzt den Rechner zurück")
      void testClear() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.changeSign();
         upn.clear();

         assertFalse(upn.isInputMode());
         assertFalse(upn.hasError());
         assertNull(upn.getInputString());
         assertEquals(0.0, upn.getLastX());
         assertEquals("0.0", upn.getDisplayText());
         assertTrue(upn.getStack().isEmpty());
      }
   }

   /**
    * Tests der CLX-Taste.
    */
   @Nested
   @DisplayName("CLX")
   class ClearXTest
   {
      /**
       * Testet CLX bei vorhandenem X-Register.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("CLX löscht X")
      void testClearX() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.clearX();

         assertTrue(upn.getStack().isEmpty());
         assertEquals("0.0", upn.getDisplayText());
      }

      /**
       * Testet CLX aus dem Eingabemodus heraus.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("CLX aus dem Eingabemodus")
      void testClearXFromInputMode() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.clearX();

         assertFalse(upn.isInputMode());
         assertNull(upn.getInputString());
         assertTrue(upn.getStack().isEmpty());
         assertEquals("0.0", upn.getDisplayText());
      }
   }

   /**
    * Tests der LastX-Taste.
    */
   @Nested
   @DisplayName("LastX")
   class PushLastXTest
   {
      /**
       * Testet LastX mit Default-Wert.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("LastX mit Default-Wert 0.0")
      void testPushDefaultLastX() throws GeneralUserException
      {
         upn.clear();
         upn.pushLastX();

         Stack<Double> stack = upn.getStack();
         assertEquals(1, stack.size());
         assertEquals(0.0, stack.getX());
         assertEquals("0.0", upn.getDisplayText());
      }

      /**
       * Testet LastX nach Vorzeichenwechsel.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("LastX nach changeSign")
      void testPushSavedLastX() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.changeSign();
         upn.pushLastX();

         Stack<Double> stack = upn.getStack();
         assertEquals(2, stack.size());
         assertEquals(6.0, stack.getX());
      }
   }

   /**
    * Tests der X<>Y-Taste.
    */
   @Nested
   @DisplayName("X<>Y")
   class SwapXYTest
   {
      /**
       * Testet das Vertauschen von X und Y.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("X und Y werden vertauscht")
      void testSwapXY() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.inputDigit(2);
         upn.enter();
         upn.swapXY();

         Stack<Double> stack = upn.getStack();
         assertEquals(6.0, stack.getX());
         stack.pop();
         assertEquals(2.0, stack.getX());
      }

      /**
       * Testet X<>Y bei nur einem Stackelement.
       *
       * @throws GeneralUserException
       *             falls unerwartet ein Fehler auftritt
       */
      @Test
      @DisplayName("X<>Y mit nur einem Element")
      void testSwapXYWithOneElement() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.swapXY();

         Stack<Double> stack = upn.getStack();
         assertEquals(1, stack.size());
         assertEquals(6.0, stack.getX());
      }
   }

   /**
    * Tests des Fehlerverhaltens.
    */
   @Nested
   @DisplayName("Fehlerverhalten")
   class ErrorHandlingTest
   {
      /**
       * Zu testender Operator.
       */
      private final Operator operator = new DivOperator();

      /**
       * Testet die Anzeige von Err nach einem Fehler.
       */
      @Test
      @DisplayName("Display zeigt Err nach Division durch 0")
      void testDisplayErrAfterError()
      {
         assertThrows(GeneralUserException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(6);
               upn.enter();
               upn.inputDigit(0);
               upn.applyOperator(operator);
            }
         });

         assertTrue(upn.hasError());
         assertEquals("Err", upn.getDisplayText());
      }

      /**
       * Testet, dass der Fehlerzustand beim nächsten Tastendruck endet.
       */
      @Test
      @DisplayName("Naechster Tastendruck beendet Fehlerzustand")
      void testNextKeyClearsErrorState()
      {
         assertThrows(GeneralUserException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(6);
               upn.enter();
               upn.inputDigit(0);
               upn.applyOperator(operator);
            }
         });

         upn.inputDigit(5);

         assertFalse(upn.hasError());
         assertTrue(upn.isInputMode());
         assertEquals("5", upn.getInputString());
         assertEquals("5", upn.getDisplayText());
      }

      /**
       * Testet, dass der Stack bei einem Fehler unverändert bleibt.
       */
      @Test
      @DisplayName("Stack bleibt bei Fehler unveraendert")
      void testStackUnchangedOnError() throws GeneralUserException
      {
         upn.inputDigit(8);
         upn.enter();

         Stack<Double> stackBefore = upn.getStack();

         assertThrows(GeneralUserException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(0);
               upn.applyOperator(operator);
            }
         });

         Stack<Double> stackAfter = upn.getStack();

         assertEquals(stackBefore.size(), stackAfter.size());
         assertEquals(stackBefore.getX(), stackAfter.getX());
      }

      /**
       * Testet, dass LastX bei einem Fehler unverändert bleibt.
       */
      @Test
      @DisplayName("LastX bleibt bei Fehler unveraendert")
      void testLastXUnchangedOnError() throws GeneralUserException
      {
         upn.inputDigit(6);
         upn.enter();
         upn.changeSign();

         double lastXBefore = upn.getLastX();

         assertThrows(GeneralUserException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(0);
               upn.applyOperator(operator);
            }
         });

         assertEquals(lastXBefore, upn.getLastX());
      }

      /**
       * Testet einen Fehler bei zu wenigen Operanden.
       */
      @Test
      @DisplayName("Division mit zu wenigen Operanden")
      void testTooFewOperands()
      {
         assertThrows(GeneralUserException.class, new Executable()
         {
            @Override
            public void execute() throws Throwable
            {
               upn.inputDigit(6);
               upn.applyOperator(operator);
            }
         });

         assertTrue(upn.hasError());
         assertEquals("Err", upn.getDisplayText());
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