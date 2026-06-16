package controller.upn;

import static org.junit.jupiter.api.Assertions.assertEquals;
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