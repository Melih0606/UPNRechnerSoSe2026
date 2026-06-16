package controller.upn.operator;

import common.exception.GeneralUserException;
import model.Stack;

/**
 * Beschreibt eine mathematische Operation des UPN-Rechners.
 *
 * <p>
 * Eine Operation entnimmt die benoetigten Operanden aus dem uebergebenen
 * Stack, fuehrt die Berechnung aus und legt das Ergebnis wieder auf dem Stack
 * ab.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public interface Operator
{
   /**
    * Fuehrt die Operation auf dem uebergebenen Stack aus.
    *
    * @param stack
    *            Stack, auf dem die Operation ausgefuehrt wird
    * @throws GeneralUserException
    *            wenn die Operation nicht erfolgreich ausgefuehrt werden kann
    */
   void calculate(Stack<Double> stack) throws GeneralUserException;
}