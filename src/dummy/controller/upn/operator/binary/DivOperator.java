package dummy.controller.upn.operator.binary;

import common.exception.UserException;
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
   public void calculate(Stack<Double> stack) throws UserException
   {
      if (stack == null)
      {
         throw new IllegalArgumentException(
               "Der Stack darf nicht null sein.");
      }

      if (stack.size() < 2)
      {
         throw new UserException("Zu wenige Operanden.");
      }

      double x = stack.pop();
      double y = stack.pop();

      if (x == 0.0)
      {
         throw new UserException("Division durch 0 ist nicht erlaubt.");
      }

      double result = y / x;

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new UserException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}