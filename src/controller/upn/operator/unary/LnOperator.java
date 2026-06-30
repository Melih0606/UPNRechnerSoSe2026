package controller.upn.operator.unary;

import common.exception.IllegalUserInputException;
import common.exception.UserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Implementierung des Logarithmusoperators.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class LnOperator implements Operator
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

      if (x <= 0.0)
      {
         throw new IllegalUserInputException(
               "Der Logarithmus ist nur fuer positive Werte definiert.");
      }

      double result = Math.log(x);

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new IllegalUserInputException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}