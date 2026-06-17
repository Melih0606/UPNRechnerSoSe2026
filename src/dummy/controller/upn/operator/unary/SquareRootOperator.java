package dummy.controller.upn.operator.unary;

import common.exception.GeneralUserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Dummy-Implementierung des Wurzeloperators.
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class SquareRootOperator implements Operator
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

      if (x < 0.0)
      {
         throw new GeneralUserException(
               "Quadratwurzel aus negativem Wert ist nicht erlaubt.");
      }

      double result = Math.sqrt(x);

      if (Double.isNaN(result) || Double.isInfinite(result))
      {
         throw new GeneralUserException("Ungueltiges Ergebnis.");
      }

      stack.push(result);
   }
}