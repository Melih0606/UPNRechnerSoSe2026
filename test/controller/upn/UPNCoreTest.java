package controller.upn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import common.exception.UserException;
import controller.upn.operator.Operator;
import dummy.controller.upn.DefaultUPNCore;
import dummy.controller.upn.operator.binary.DivOperator;
import model.Stack;

/**
 * JUnit-5-Testklasse zum Testen des UPN-Rechenkerns.
 *
 * <p>
 * In diesem ersten Stand werden der Startzustand sowie erste Eingaben und die
 * Division geprueft.
 * </p>
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
       * Testet den Startmodus.
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
   }

   /**
    * Beispieltest Division.
    */
   @Nested
   @DisplayName("/")
   class DivTest
   {
      /**
       * Dummy-Divisionsoperator.
       */
      private final Operator operator = new DivOperator();

      /**
       * Testet 6 / 2 = 3.
       *
       * @throws UserException
       *            falls der Test unerwartet fehlschlaegt
       */
      @Test
      @DisplayName("6 / 2 = 3")
      void testOk() throws UserException
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
         assertThrows(UserException.class, new Executable()
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