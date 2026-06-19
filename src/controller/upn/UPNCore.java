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
    * Eingabezeile angehaengt, sofern dadurch weiterhin ein in einen
    * {@code double} konvertierbarer Text entsteht. Ein eventuell vorher
    * angezeigter Fehler wird durch diesen neuen Tastendruck beendet.
    * </p>
    *
    * @param digit
    *            die gedrueckte Ziffer; gueltig sind nur Werte von {@code 0}
    *            bis {@code 9}
    * @throws IllegalArgumentException
    *             wenn {@code digit} kleiner als {@code 0} oder groesser als
    *             {@code 9} ist; dies ist ein Programmierfehler der Oberflaeche
    */
   void inputDigit(int digit);

   /**
    * Verarbeitet den Druck auf die Dezimalpunkttaste.
    *
    * <p>
    * Die Methode setzt den Rechner in den Eingabemodus und ergaenzt die
    * Eingabezeile um einen Dezimalpunkt. Die Eingabezeile darf zu keinem
    * Zeitpunkt ungueltig werden. Ist bereits ein Dezimalpunkt vorhanden, bleibt
    * die Eingabezeile unveraendert. Ein eventuell vorher angezeigter Fehler
    * wird durch diesen neuen Tastendruck beendet.
    * </p>
    */
   void inputDecimalPoint();

   /**
    * Wechselt das Vorzeichen des aktuellen Wertes.
    *
    * <p>
    * Im Eingabemodus wird am Anfang der Eingabezeile ein Minuszeichen
    * eingefuegt oder entfernt. Im Funktionsmodus wird das Vorzeichen des
    * X-Registers gewechselt. Dabei wird der bisherige X-Wert im LastX-Register
    * gesichert. Ein eventuell vorher angezeigter Fehler wird durch diesen neuen
    * Tastendruck beendet.
    * </p>
    *
    * @throws UserException
    *             wenn der Vorzeichenwechsel im Funktionsmodus nicht moeglich
    *             ist, zum Beispiel weil kein X-Register vorhanden ist oder das
    *             Ergebnis nicht endlich ist
    */
   void changeSign() throws UserException;

   /**
    * Verarbeitet den Druck auf die Enter-Taste.
    *
    * <p>
    * Im Eingabemodus wird die aktuelle Eingabezeile in einen {@code double}
    * konvertiert, auf den Stack gelegt und die Eingabezeile geleert. Im
    * Funktionsmodus wird der Inhalt des X-Registers kopiert und erneut auf den
    * Stack gelegt. Ist der Stack leer, wird der angezeigte Wert {@code 0.0} auf
    * den Stack gelegt. Nach erfolgreicher Ausfuehrung befindet sich der Rechner
    * im Funktionsmodus.
    * </p>
    *
    * @throws UserException
    *             wenn die Eingabezeile nicht in einen {@code double}
    *             konvertiert werden kann oder das Kopieren des X-Registers
    *             fehlschlaegt
    */
   void enter() throws UserException;

   /**
    * Loescht den gesamten Stack und setzt das LastX-Register auf den
    * Anfangswert zurueck.
    *
    * <p>
    * Vor dem Loeschen wird der Rechner in den Funktionsmodus gesetzt. Eine
    * vorhandene Eingabezeile wird dabei verworfen. Nach der Methode ist der
    * Stack leer, LastX hat den Wert {@code 0.0}, und die Anzeige zeigt
    * {@code "0.0"}.
    * </p>
    */
   void clear();

   /**
    * Loescht das X-Register des Stacks.
    *
    * <p>
    * Der Rechner wird zuerst in den Funktionsmodus gesetzt. Gibt es ein
    * X-Register, wird dieses entfernt. Ist der Stack leer, bleibt er leer. Das
    * LastX-Register wird durch diese Stackmanipulation nicht veraendert.
    * </p>
    *
    * @throws UserException
    *             wenn eine vorhandene Eingabezeile nicht auf den Stack
    *             uebernommen werden kann
    */
   void clearX() throws UserException;

   /**
    * Schiebt den Inhalt des LastX-Registers auf den Stack.
    *
    * <p>
    * Der Rechner wird zuerst in den Funktionsmodus gesetzt. Danach wird der
    * aktuelle Wert des LastX-Registers auf den Stack gelegt und dadurch zum
    * neuen X-Register. Der Anfangswert von LastX ist {@code 0.0}.
    * </p>
    *
    * @throws UserException
    *             wenn eine vorhandene Eingabezeile nicht auf den Stack
    *             uebernommen werden kann
    */
   void pushLastX() throws UserException;

   /**
    * Vertauscht die beiden untersten Elemente des Stacks.
    *
    * <p>
    * Der Rechner wird zuerst in den Funktionsmodus gesetzt. Anschliessend
    * werden X-Register und Y-Register vertauscht. Sind weniger als zwei Werte
    * auf dem Stack vorhanden, bleibt der Stack unveraendert. Das
    * LastX-Register wird durch diese Stackmanipulation nicht veraendert.
    * </p>
    *
    * @throws UserException
    *             wenn eine vorhandene Eingabezeile nicht auf den Stack
    *             uebernommen werden kann
    */
   void swapXY() throws UserException;

   /**
    * Fuehrt eine mathematische Operation auf dem Stack aus.
    *
    * @param operator
    *            die auszufuehrende mathematische Operation; darf nicht
    *            {@code null} sein
    * @throws IllegalArgumentException
    *             wenn {@code operator} {@code null} ist
    * @throws UserException
    *             wenn die Operation nicht erfolgreich ausgefuehrt werden kann
    */
   void applyOperator(Operator operator) throws UserException;

   /**
    * Liefert den Text, der im Display angezeigt werden soll.
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
    * @return {@code true}, wenn eine Zahl als Eingabezeile aufgebaut wird,
    *         sonst {@code false}
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
