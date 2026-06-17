package dummy.controller.upn.operator.unary;

import common.exception.GeneralUserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Dummy-Implementierung des Sinusoperators.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class SinOperator implements Operator
{
   @Override
   public void calculate(Stack<Double> stack) throws GeneralUserException
   {
      if (stack == null)
      {
         throw new IllegalArgumentException(
               "Der Stack darf nicht null sein.");
      }

      if (stack.isEmpty())
      {
         throw new GeneralUserException("Zu wenige Operanden.");
      }

      double x = stack.pop();
      double result = Math.sin(x);

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new GeneralUserException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}