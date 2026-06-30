package controller.upn.operator.binary;

import common.exception.IllegalUserInputException;
import common.exception.UserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Implementierung des Divisionsoperators.
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
         throw new IllegalUserInputException("Zu wenige Operanden.");
      }

      double x = stack.pop();
      double y = stack.pop();

      if (x == 0.0)
      {
         throw new IllegalUserInputException(
               "Division durch 0 ist nicht erlaubt.");
      }

      double result = y / x;

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new IllegalUserInputException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}