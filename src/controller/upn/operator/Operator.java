package controller.upn.operator;

import common.exception.GeneralUserException;
import model.Stack;

/**
 * Beschreibt eine mathematische Operation des UPN-Rechners.
 *
 * <p>
 * Eine Operation entnimmt die benoetigten Operanden aus dem uebergebenen
 * Stack, fuehrt die Berechnung aus und legt das Ergebnis wieder auf dem Stack
 * ab. Die konkrete Anzahl der Operanden haengt von der jeweiligen Operation ab.
 * </p>
 *
 * <p>
 * Implementierungen muessen sicherstellen, dass ungueltige Operanden,
 * fehlende Operanden sowie unzulaessige Ergebnisse wie NaN oder Infinity als
 * Fehler behandelt werden.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public interface Operator {

    /**
     * Fuehrt die Operation auf dem uebergebenen Stack aus.
     *
     * <p>
     * Die Methode veraendert den Stack nur dann dauerhaft, wenn die Operation
     * erfolgreich ausgefuehrt werden kann. Bei einem Fehler soll die aufrufende
     * Klasse dafuer sorgen, dass der vorherige Zustand des Stacks
     * wiederhergestellt wird.
     * </p>
     *
     * @param stack
     *            Stack, auf dem die Operation ausgefuehrt wird; darf nicht
     *            {@code null} sein
     * @throws GeneralUserException
     *             wenn die Operation wegen fehlender oder ungueltiger Operanden
     *             nicht ausgefuehrt werden kann
     */
    void calculate(Stack<Double> stack) throws GeneralUserException;
}