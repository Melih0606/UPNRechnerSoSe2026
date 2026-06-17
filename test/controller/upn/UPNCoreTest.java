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
import dummy.controller.upn.operator.binary.AddOperator;
import dummy.controller.upn.operator.binary.DivOperator;
import dummy.controller.upn.operator.binary.MulOperator;
import dummy.controller.upn.operator.binary.PowerOperator;
import dummy.controller.upn.operator.binary.SubOperator;
import dummy.controller.upn.operator.unary.CosOperator;
import dummy.controller.upn.operator.unary.LnOperator;
import dummy.controller.upn.operator.unary.ReciprocalOperator;
import dummy.controller.upn.operator.unary.SinOperator;
import dummy.controller.upn.operator.unary.SquareRootOperator;
import dummy.controller.upn.operator.unary.TanOperator;
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
      @Test
      @DisplayName("Display zeigt 0.0")
      void testDisplayText()
      {
         assertEquals("0.0", upn.getDisplayText());
      }

      @Test
      @DisplayName("Keine Eingabezeile aktiv")
      void testInputString()
      {
         assertNull(upn.getInputString());
      }

      @Test
      @DisplayName("Stack ist leer")
      void testStackEmpty()
      {
         Stack<Double> stack = upn.getStack();
         assertTrue(stack.isEmpty());
      }

      @Test
      @DisplayName("LastX ist 0.0")
      void testLastX()
      {
         assertEquals(0.0, upn.getLastX());
      }

      @Test
      @DisplayName("Start im Funktionsmodus")
      void testInputMode()
      {
         assertFalse(upn.isInputMode());
      }

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
      @Test
      @DisplayName("Eingabe 6")
      void testDigitSix()
      {
         upn.inputDigit(6);

         assertTrue(upn.isInputMode());
         assertEquals("6", upn.getInputString());
         assertEquals("6", upn.getDisplayText());
      }

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
      @Test
      @DisplayName("Dezimalpunkt startet mit 0.")
      void testDecimalPointFirst()
      {
         upn.inputDecimalPoint();

         assertTrue(upn.isInputMode());
         assertEquals("0.", upn.getInputString());
         assertEquals("0.", upn.getDisplayText());
      }

      @Test
      @DisplayName("6 dann Dezimalpunkt")
      void testDecimalPointAfterDigit()
      {
         upn.inputDigit(6);
         upn.inputDecimalPoint();

         assertEquals("6.", upn.getInputString());
         assertEquals("6.", upn.getDisplayText());
      }

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

      @Test
      @DisplayName("Minus und Dezimalpunkt im Eingabemodus ergibt -0.")
      void testDecimalPointAfterNegativeSign() throws GeneralUserException
      {
         upn.inputDigit(0);
         upn.changeSign();
         upn.inputDecimalPoint();

         assertEquals("-0.", upn.getInputString());
         assertEquals("-0.", upn.getDisplayText());
      }

      /**
       * Tests des Vorzeichenwechsels im Eingabemodus.
       */
      @Nested
      @DisplayName("+/- im Eingabemodus")
      class ChangeSignInputModeTest
      {
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
       * Tests des Vorzeichenwechsels im Funktionsmodus.
       */
      @Nested
      @DisplayName("+/- im Funktionsmodus")
      class ChangeSignFunctionModeTest
      {
         @Test
         @DisplayName("6 wird im Funktionsmodus zu -6.0")
         void testChangeSignFunctionMode() throws GeneralUserException
         {
            upn.inputDigit(6);
            upn.enter();
            upn.changeSign();

            assertFalse(upn.isInputMode());
            assertEquals("-6.0", upn.getDisplayText());
            assertEquals(6.0, upn.getLastX());

            Stack<Double> stack = upn.getStack();
            assertEquals(1, stack.size());
            assertEquals(-6.0, stack.getX());
         }

         @Test
         @DisplayName("Vorzeichenwechsel bei leerem Stack bleibt ohne Wirkung")
         void testChangeSignEmptyStack() throws GeneralUserException
         {
            upn.changeSign();

            assertEquals("0.0", upn.getDisplayText());
            assertFalse(upn.hasError());
            assertTrue(upn.getStack().isEmpty());
         }
      }

      /**
       * Tests der Enter-Taste.
       */
      @Nested
      @DisplayName("Enter")
      class EnterTest
      {
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

         @Test
         @DisplayName("CLR beendet Fehlerzustand")
         void testClearAfterError()
         {
            final Operator operator = new DivOperator();

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

            upn.clear();

            assertFalse(upn.hasError());
            assertEquals("0.0", upn.getDisplayText());
         }
      }

      /**
       * Tests der CLX-Taste.
       */
      @Nested
      @DisplayName("CLX")
      class ClearXTest
      {
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

         @Test
         @DisplayName("CLX bei leerem Stack bleibt ohne Wirkung")
         void testClearXOnEmptyStack() throws GeneralUserException
         {
            upn.clearX();

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

         @Test
         @DisplayName("LastX aus dem Eingabemodus")
         void testPushLastXFromInputMode() throws GeneralUserException
         {
            upn.inputDigit(6);
            upn.pushLastX();

            Stack<Double> stack = upn.getStack();
            assertEquals(2, stack.size());
            assertEquals(0.0, stack.getX());
         }
      }

      /**
       * Tests der X<>Y-Taste.
       */
      @Nested
      @DisplayName("X<>Y")
      class SwapXYTest
      {
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

         @Test
         @DisplayName("X<>Y mit leerem Stack")
         void testSwapXYOnEmptyStack() throws GeneralUserException
         {
            upn.swapXY();

            assertTrue(upn.getStack().isEmpty());
            assertEquals("0.0", upn.getDisplayText());
         }

         @Test
         @DisplayName("X<>Y aus dem Eingabemodus")
         void testSwapXYFromInputMode() throws GeneralUserException
         {
            upn.inputDigit(6);
            upn.enter();
            upn.inputDigit(2);
            upn.swapXY();

            Stack<Double> stack = upn.getStack();
            assertEquals(6.0, stack.getX());
            stack.pop();
            assertEquals(2.0, stack.getX());
         }
      }

      /**
       * Tests der Addition.
       */
      @Nested
      @DisplayName("+")
      class AddTest
      {
         private final Operator operator = new AddOperator();

         @Test
         @DisplayName("6 + 2 = 8")
         void testAdd() throws GeneralUserException
         {
            upn.inputDigit(6);
            upn.enter();
            upn.inputDigit(2);
            upn.applyOperator(operator);

            assertEquals("8.0", upn.getDisplayText());
            assertEquals(2.0, upn.getLastX());
         }
      }

      /**
       * Tests der Subtraktion.
       */
      @Nested
      @DisplayName("-")
      class SubTest
      {
         private final Operator operator = new SubOperator();

         @Test
         @DisplayName("6 - 2 = 4")
         void testSub() throws GeneralUserException
         {
            upn.inputDigit(6);
            upn.enter();
            upn.inputDigit(2);
            upn.applyOperator(operator);

            assertEquals("4.0", upn.getDisplayText());
         }
      }

      /**
       * Tests der Multiplikation.
       */
      @Nested
      @DisplayName("*")
      class MulTest
      {
         private final Operator operator = new MulOperator();

         @Test
         @DisplayName("6 * 2 = 12")
         void testMul() throws GeneralUserException
         {
            upn.inputDigit(6);
            upn.enter();
            upn.inputDigit(2);
            upn.applyOperator(operator);

            assertEquals("12.0", upn.getDisplayText());
         }
      }

      /**
       * Tests der Division.
       */
      @Nested
      @DisplayName("/")
      class DivTest
      {
         private final Operator operator = new DivOperator();

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

      /**
       * Tests des Kehrwerts.
       */
      @Nested
      @DisplayName("1/X")
      class ReciprocalTest
      {
         private final Operator operator = new ReciprocalOperator();

         @Test
         @DisplayName("Kehrwert von 2 ist 0.5")
         void testReciprocal() throws GeneralUserException
         {
            upn.inputDigit(2);
            upn.applyOperator(operator);

            assertEquals("0.5", upn.getDisplayText());
            assertEquals(2.0, upn.getLastX());
         }

         @Test
         @DisplayName("Kehrwert von 0 ist Fehler")
         void testReciprocalZero()
         {
            assertThrows(GeneralUserException.class, new Executable()
            {
               @Override
               public void execute() throws Throwable
               {
                  upn.inputDigit(0);
                  upn.applyOperator(operator);
               }
            });
         }
      }

      /**
       * Tests der Potenzfunktion.
       */
      @Nested
      @DisplayName("Y^X")
      class PowerTest
      {
         private final Operator operator = new PowerOperator();

         @Test
         @DisplayName("2 ^ 3 = 8")
         void testPower() throws GeneralUserException
         {
            upn.inputDigit(2);
            upn.enter();
            upn.inputDigit(3);
            upn.applyOperator(operator);

            assertEquals("8.0", upn.getDisplayText());
         }

         @Test
         @DisplayName("0 ^ -1 ist Fehler")
         void testPowerInvalid()
         {
            assertThrows(GeneralUserException.class, new Executable()
            {
               @Override
               public void execute() throws Throwable
               {
                  upn.inputDigit(0);
                  upn.enter();
                  upn.inputDigit(1);
                  upn.changeSign();
                  upn.applyOperator(operator);
               }
            });
         }
      }

      /**
       * Tests der Quadratwurzel.
       */
      @Nested
      @DisplayName("SQR")
      class SquareRootTest
      {
         private final Operator operator = new SquareRootOperator();

         @Test
         @DisplayName("Sqrt von 9 ist 3")
         void testSquareRoot() throws GeneralUserException
         {
            upn.inputDigit(9);
            upn.applyOperator(operator);

            assertEquals("3.0", upn.getDisplayText());
         }

         @Test
         @DisplayName("Sqrt von -9 ist Fehler")
         void testSquareRootNegative()
         {
            assertThrows(GeneralUserException.class, new Executable()
            {
               @Override
               public void execute() throws Throwable
               {
                  upn.inputDigit(9);
                  upn.changeSign();
                  upn.applyOperator(operator);
               }
            });
         }
      }

      /**
       * Tests des natürlichen Logarithmus.
       */
      @Nested
      @DisplayName("LN")
      class LnTest
      {
         private final Operator operator = new LnOperator();

         @Test
         @DisplayName("LN von 1 ist 0")
         void testLn() throws GeneralUserException
         {
            upn.inputDigit(1);
            upn.applyOperator(operator);

            assertEquals("0.0", upn.getDisplayText());
         }

         @Test
         @DisplayName("LN von 0 ist Fehler")
         void testLnZero()
         {
            assertThrows(GeneralUserException.class, new Executable()
            {
               @Override
               public void execute() throws Throwable
               {
                  upn.inputDigit(0);
                  upn.applyOperator(operator);
               }
            });
         }
      }

      /**
       * Tests des Sinus.
       */
      @Nested
      @DisplayName("SIN")
      class SinTest
      {
         private final Operator operator = new SinOperator();

         @Test
         @DisplayName("SIN von 0 ist 0")
         void testSin() throws GeneralUserException
         {
            upn.inputDigit(0);
            upn.applyOperator(operator);

            assertEquals("0.0", upn.getDisplayText());
         }
      }

      /**
       * Tests des Kosinus.
       */
      @Nested
      @DisplayName("COS")
      class CosTest
      {
         private final Operator operator = new CosOperator();

         @Test
         @DisplayName("COS von 0 ist 1")
         void testCos() throws GeneralUserException
         {
            upn.inputDigit(0);
            upn.applyOperator(operator);

            assertEquals("1.0", upn.getDisplayText());
         }
      }

      /**
       * Tests des Tangens.
       */
      @Nested
      @DisplayName("TAN")
      class TanTest
      {
         private final Operator operator = new TanOperator();

         @Test
         @DisplayName("TAN von 0 ist 0")
         void testTan() throws GeneralUserException
         {
            upn.inputDigit(0);
            upn.applyOperator(operator);

            assertEquals("0.0", upn.getDisplayText());
         }
      }

      /**
       * Tests des Fehlerverhaltens.
       */
      @Nested
      @DisplayName("Fehlerverhalten")
      class ErrorHandlingTest
      {
         private final Operator operator = new DivOperator();

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

         @Test
         @DisplayName("Dezimalpunkt beendet Fehlerzustand")
         void testDecimalPointClearsErrorState()
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

            upn.inputDecimalPoint();

            assertFalse(upn.hasError());
            assertTrue(upn.isInputMode());
            assertEquals("0.", upn.getInputString());
            assertEquals("0.", upn.getDisplayText());
         }

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

         @Test
         @DisplayName("applyOperator mit null")
         void testApplyOperatorNull()
         {
            assertThrows(IllegalArgumentException.class, new Executable()
            {
               @Override
               public void execute() throws Throwable
               {
                  upn.applyOperator(null);
               }
            });
         }
      }
   }
}