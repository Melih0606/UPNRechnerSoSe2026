package controller.upn.operator.unary;

import common.exception.IllegalUserInputException;
import common.exception.UserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Dummy-Implementierung des Kosinusoperators.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class CosOperator implements Operator
{
   @Override
   public void calculate(Stack<Double> stack) throws UserException
   {
      if (stack == null)
      {
         throw new IllegalArgumentException(
               "Der Stack darf nicht null sein.");
      }

      if (stack.isEmpty())
      {
         throw new IllegalUserInputException("Zu wenige Operanden.");
      }

      double x = stack.pop();
      double result = Math.cos(x);

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new IllegalUserInputException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}
