package dummy.controller.upn.operator.binary;

import common.exception.GeneralUserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Dummy-Implementierung des Divisionsoperators.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class DivOperator implements Operator
{
   @Override
   public void calculate(Stack<Double> stack) throws GeneralUserException
   {
      if (stack == null)
      {
         throw new IllegalArgumentException(
               "Der Stack darf nicht null sein.");
      }

      if (stack.size() < 2)
      {
         throw new GeneralUserException("Zu wenige Operanden.");
      }

      double x = stack.pop();
      double y = stack.pop();

      if (x == 0.0)
      {
         throw new GeneralUserException(
               "Division durch 0 ist nicht erlaubt.");
      }

      double result = y / x;

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new GeneralUserException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}