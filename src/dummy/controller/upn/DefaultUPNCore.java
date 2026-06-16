package dummy.controller.upn;

import common.exception.UserException;
import controller.upn.UPNCore;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Dummy-Implementierung des UPN-Rechenkerns.
 *
 * <p>
 * Diese Klasse dient dazu, Tests bereits vor der spaeteren echten
 * Implementierung des Rechenkerns kompilieren und ausfuehren zu koennen.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class DefaultUPNCore implements UPNCore
{
   /**
    * Eingabezeile.
    */
   private String inputString = null;

   /**
    * Stack des Rechners.
    */
   private final Stack<Double> stack = new Stack<Double>();

   /**
    * LastX-Register.
    */
   private double lastX = 0.0;

   /**
    * Kennzeichnet den Eingabemodus.
    */
   private boolean inputMode = false;

   /**
    * Kennzeichnet den Fehlerzustand.
    */
   private boolean error = false;

   /**
    * Hebt einen Fehlerzustand auf.
    */
   private void clearErrorState()
   {
      error = false;
   }

   @Override
   public void inputDigit(int digit)
   {
      if (digit < 0 || digit > 9)
      {
         throw new IllegalArgumentException(
               "Die Ziffer muss zwischen 0 und 9 liegen.");
      }

      clearErrorState();

      if (!inputMode)
      {
         inputMode = true;
         inputString = "";
      }

      if (inputString == null)
      {
         inputString = "";
      }

      inputString = inputString + digit;
   }

   @Override
   public void inputDecimalPoint()
   {
      clearErrorState();

      if (!inputMode)
      {
         inputMode = true;
         inputString = "";
      }

      if (inputString == null || inputString.isEmpty())
      {
         inputString = "0";
      }

      if (!inputString.contains("."))
      {
         inputString = inputString + ".";
      }
   }

   @Override
   public void changeSign() throws UserException
   {
      clearErrorState();

      if (inputMode)
      {
         if (inputString == null || inputString.isEmpty())
         {
            inputString = "-";
         }
         else if (inputString.startsWith("-"))
         {
            inputString = inputString.substring(1);
         }
         else
         {
            inputString = "-" + inputString;
         }
      }
      else if (!stack.isEmpty())
      {
         double x = stack.pop();
         lastX = x;
         stack.push(-x);
      }
   }

   @Override
   public void enter() throws UserException
   {
      clearErrorState();

      if (inputMode)
      {
         if (inputString == null || inputString.isEmpty()
               || "-".equals(inputString))
         {
            stack.push(0.0);
         }
         else
         {
            stack.push(Double.parseDouble(inputString));
         }

         inputString = null;
         inputMode = false;
      }
      else
      {
         if (stack.isEmpty())
         {
            stack.push(0.0);
         }
         else
         {
            stack.push(stack.getX());
         }
      }
   }

   @Override
   public void clear()
   {
      stack.clear();
      inputString = null;
      inputMode = false;
      error = false;
      lastX = 0.0;
   }

   @Override
   public void clearX() throws UserException
   {
      clearErrorState();
      inputString = null;
      inputMode = false;

      if (!stack.isEmpty())
      {
         stack.pop();
      }
   }

   @Override
   public void pushLastX() throws UserException
   {
      clearErrorState();
      inputString = null;
      inputMode = false;
      stack.push(lastX);
   }

   @Override
   public void swapXY() throws UserException
   {
      clearErrorState();
      inputString = null;
      inputMode = false;
      stack.swapXY();
   }

   @Override
   public void applyOperator(Operator operator) throws UserException
   {
      clearErrorState();

      if (operator == null)
      {
         throw new IllegalArgumentException(
               "Der Operator darf nicht null sein.");
      }

      if (inputMode)
      {
         enter();
      }

      if (!stack.isEmpty())
      {
         lastX = stack.getX();
      }

      try
      {
         operator.calculate(stack);
      }
      catch (UserException e)
      {
         error = true;
         throw e;
      }
   }

   @Override
   public String getDisplayText()
   {
      if (error)
      {
         return "Err";
      }

      if (inputMode && inputString != null)
      {
         return inputString;
      }

      if (stack.isEmpty())
      {
         return "0.0";
      }

      return String.valueOf(stack.getX());
   }

   @Override
   public String getInputString()
   {
      return inputString;
   }

   @Override
   public Stack<Double> getStack()
   {
      return stack.clone();
   }

   @Override
   public double getLastX()
   {
      return lastX;
   }

   @Override
   public boolean isInputMode()
   {
      return inputMode;
   }

   @Override
   public boolean hasError()
   {
      return error;
   }
}