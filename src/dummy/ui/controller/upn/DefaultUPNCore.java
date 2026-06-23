package dummy.ui.controller.upn;

import common.exception.IllegalUserInputException;
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

   /**
    * Setzt den Rechner in den Funktionsmodus.
    *
    * <p>
    * Falls eine Eingabezeile vorhanden ist, wird sie in einen Double-Wert
    * konvertiert und auf den Stack geschoben. Anschließend wird die
    * Eingabezeile gelöscht.
    * </p>
    *
    * @throws UserException
    *            falls die Eingabezeile nicht in einen gültigen Double-Wert
    *            konvertiert werden kann
    */
   private void setFunctionMode() throws UserException
   {
      if (inputString != null)
      {
         try
         {
            double value = Double.parseDouble(inputString);

            if (Double.isNaN(value) || Double.isInfinite(value))
            {
               throw new IllegalUserInputException("Ungueltiger Eingabewert.");
            }

            stack.push(value);
            inputString = null;
         }
         catch (NumberFormatException e)
         {
            throw new IllegalUserInputException("Ungueltiger Eingabewert.", e);
         }
      }

      inputMode = false;
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
      else if ("-".equals(inputString))
      {
         inputString = "-0";
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
            inputString = "-0";
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
         setFunctionMode();
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
      setFunctionMode();

      if (!stack.isEmpty())
      {
         stack.pop();
      }
   }

   @Override
   public void pushLastX() throws UserException
   {
      clearErrorState();
      setFunctionMode();
      stack.push(lastX);
   }

   @Override
   public void swapXY() throws UserException
   {
      clearErrorState();
      setFunctionMode();
      stack.swapXY();
   }

   @Override
   public void applyOperator(Operator operator) throws UserException
   {
      if (operator == null)
      {
         throw new IllegalArgumentException(
               "Der Operator darf nicht null sein.");
      }

      clearErrorState();

      Stack<Double> oldStack = stack.clone();
      double oldLastX = lastX;
      String oldInputString = inputString;
      boolean oldInputMode = inputMode;

      try
      {
         setFunctionMode();

         if (!stack.isEmpty())
         {
            lastX = stack.getX();
         }

         operator.calculate(stack);
      }
      catch (UserException e)
      {
         stack.clear();
         stack.addAll(oldStack);
         lastX = oldLastX;
         inputString = null;
         inputMode = false;
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
