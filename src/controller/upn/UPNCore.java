package controller.upn;

import common.exception.UserException;
import controller.upn.operator.Operator;
import model.Stack;

/**
 * Beschreibt die Schnittstelle des Rechenkerns eines UPN-Taschenrechners.
 *
 * <p>
 * Der Rechenkern kapselt den Stack, das LastX-Register, die Eingabezeile,
 * den Betriebsmodus und den Fehlerzustand des Rechners. Eine grafische
 * Oberflaeche darf keine eigene Rechnerlogik enthalten, sondern ruft fuer
 * jeden Tastendruck genau die passende Methode dieser Schnittstelle auf und
 * aktualisiert ihre Anzeige anschliessend ueber {@link #getDisplayText()}.
 * </p>
 *
 * <p>
 * Der Rechner kennt zwei Betriebsmodi: Eingabemodus und Funktionsmodus. Im
 * Eingabemodus wird eine Zahl als Text aufgebaut. Im Funktionsmodus wird das
 * X-Register, also das unterste Element des UPN-Stacks, angezeigt. Ist der
 * Stack leer, liefert die Anzeige {@code "0.0"}. Im Fehlerfall liefert die
 * Anzeige bis zum naechsten Tastendruck {@code "Err"}.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public interface UPNCore
{
   /**
    * Verarbeitet den Druck auf eine Zifferntaste.
    *
    * <p>
    * Die Methode setzt den Rechner in den Eingabemodus, falls er sich nicht
    * bereits darin befindet. Anschliessend wird die angegebene Ziffer an die
    * Eingabezeile angehaengt.
    * </p>
    *
    * @param digit
    *            die gedrueckte Ziffer; gueltig sind nur Werte von {@code 0}
    *            bis {@code 9}
    * @throws IllegalArgumentException
    *             wenn {@code digit} kleiner als {@code 0} oder groesser als
    *             {@code 9} ist
    */
   void inputDigit(int digit);

   /**
    * Verarbeitet den Druck auf die Dezimalpunkttaste.
    */
   void inputDecimalPoint();

   /**
    * Wechselt das Vorzeichen des aktuellen Wertes.
    *
    * @throws UserException
    *             wenn der Vorzeichenwechsel nicht erfolgreich ausgefuehrt
    *             werden kann
    */
   void changeSign() throws UserException;

   /**
    * Verarbeitet den Druck auf die Enter-Taste.
    *
    * @throws UserException
    *             wenn die Eingabe nicht erfolgreich uebernommen werden kann
    */
   void enter() throws UserException;

   /**
    * Loescht den gesamten Rechnerzustand.
    */
   void clear();

   /**
    * Loescht das X-Register.
    *
    * @throws UserException
    *             wenn die Funktion nicht erfolgreich ausgefuehrt werden kann
    */
   void clearX() throws UserException;

   /**
    * Schiebt den Inhalt des LastX-Registers auf den Stack.
    *
    * @throws UserException
    *             wenn die Funktion nicht erfolgreich ausgefuehrt werden kann
    */
   void pushLastX() throws UserException;

   /**
    * Vertauscht X und Y.
    *
    * @throws UserException
    *             wenn die Funktion nicht erfolgreich ausgefuehrt werden kann
    */
   void swapXY() throws UserException;

   /**
    * Fuehrt eine mathematische Operation aus.
    *
    * @param operator
    *            auszufuehrender Operator; darf nicht {@code null} sein
    * @throws IllegalArgumentException
    *             wenn {@code operator} {@code null} ist
    * @throws UserException
    *             wenn die Operation nicht erfolgreich ausgefuehrt werden kann
    */
   void applyOperator(Operator operator) throws UserException;

   /**
    * Liefert den aktuell anzuzeigenden Displaytext.
    *
    * @return aktuell anzuzeigender Displaytext; niemals {@code null}
    */
   String getDisplayText();

   /**
    * Liefert die aktuelle Eingabezeile.
    *
    * @return aktuelle Eingabezeile oder {@code null}
    */
   String getInputString();

   /**
    * Liefert eine Kopie des aktuellen Stacks.
    *
    * @return Kopie des aktuellen UPN-Stacks
    */
   Stack<Double> getStack();

   /**
    * Liefert den aktuellen Wert des LastX-Registers.
    *
    * @return aktueller LastX-Wert
    */
   double getLastX();

   /**
    * Gibt an, ob der Rechner gerade im Eingabemodus ist.
    *
    * @return {@code true}, wenn Eingabemodus aktiv ist, sonst {@code false}
    */
   boolean isInputMode();

   /**
    * Gibt an, ob der Rechner gerade im Fehlerzustand ist.
    *
    * @return {@code true}, wenn der Rechner im Fehlerzustand ist, sonst
    *         {@code false}
    */
   boolean hasError();
}