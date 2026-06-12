package controller.upn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import common.exception.IllegalUserInputException;
import common.exception.UserException;
import controller.upn.operator.Operator;
import dummy.controller.upn.operator.binary.DivOperator;
import model.Stack;

/**
 * JUnit 5 Testklasse zum Testen der {@link UPNCore} Implementierung
 * {@link DefaultUPNCore}.
 *
 * @author M. Faulstich
 *
 */
class UPNCoreTest
{

   private UPNCore upn = null; // Sobald diese Klasse implementiert ist: new DefaultUPNCore();

   /*
    * Beispiel für den Test des der Division
    */
   // Ob diese Test reichen, entscheiden Sie....

   /**
    * Test Division.
    */
   @Nested
   @DisplayName("/")
   class DivTest
   {
      Operator operator = new DivOperator();

      @Test
      @DisplayName("6 / 2 = 3")
      void testOk() throws UserException
      {
         upn.clr();
         upn.keyIn("6");
         upn.enter();
         upn.keyIn("2");
         upn.execute(operator);

         Stack<Double> stack = upn.getStack();
         assertTrue(stack.size() > 0);

         assertEquals(3.0, stack.peek(stack.size() - 1));
         // LastX korrekt?
         upn.lastX();
         assertEquals(2.0, stack.peek(stack.size() - 1));
      }

      /**
       * Test Division durch 0.
       */
      @Test
      @DisplayName("6 / 0")
      void testDiv0()
      {
         assertThrows(IllegalUserInputException.class, new Executable()
         {

            @Override
            public void execute() throws Throwable
            {
               upn.clr();
               upn.keyIn("6");
               upn.enter();
               upn.keyIn("0");
               upn.execute(operator);
            }
         });
      }

   }

}
