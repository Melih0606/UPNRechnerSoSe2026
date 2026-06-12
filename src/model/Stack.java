package model;

import java.util.ArrayList;

/**
 * Beschreibt einen einfachen generischen Stack fuer den UPN-Rechner.
 *
 * <p>
 * Der Stack speichert die Operanden des UPN-Rechners. Das zuletzt eingefuegte
 * Element bildet das X-Register des Rechners. Das davor eingefuegte Element
 * bildet das Y-Register. Weitere Elemente liegen darueber.
 * </p>
 *
 * <p>
 * Die Klasse stellt Methoden zum Einfuegen, Entfernen, Lesen und Tauschen von
 * Stackelementen bereit. Sie ist Teil des Models und enthaelt keine Logik zur
 * Darstellung der Benutzeroberflaeche.
 * </p>
 *
 * @param <T>
 *            Typ der im Stack gespeicherten Werte
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev

 */
public class Stack<T> extends ArrayList<T> {

    /**
     * Versionsnummer fuer die Serialisierung.
     *
     * <p>
     * Da {@link ArrayList} das Interface {@link java.io.Serializable}
     * implementiert, sollte auch diese abgeleitete Klasse eine eigene
     * serialVersionUID definieren.
     * </p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * Legt ein Element auf den Stack.
     *
     * <p>
     * Das eingefuegte Element wird zum neuen obersten beziehungsweise untersten
     * UPN-Element im Sinne des X-Registers. Vorherige Elemente werden dadurch
     * um eine Position verschoben.
     * </p>
     *
     * @param value
     *            Wert, der auf den Stack gelegt werden soll
     */
    public void push(T value) {
        add(value);
    }

    /**
     * Entfernt das X-Register vom Stack und liefert dessen Wert.
     *
     * <p>
     * Das X-Register ist das zuletzt eingefuegte Element. Nach dem Entfernen
     * wird das vorherige Y-Register zum neuen X-Register.
     * </p>
     *
     * @return entfernter Wert des X-Registers
     * @throws IndexOutOfBoundsException
     *             wenn der Stack leer ist
     */
    public T pop() {
        return remove(size() - 1);
    }

    /**
     * Liefert den Wert des X-Registers, ohne ihn vom Stack zu entfernen.
     *
     * @return aktueller Wert des X-Registers
     * @throws IndexOutOfBoundsException
     *             wenn der Stack leer ist
     */
    public T peek() {
        return get(size() - 1);
    }

    /**
     * Liefert den Wert des X-Registers.
     *
     * <p>
     * Diese Methode ist eine fachlich benannte Alternative zu {@link #peek()}.
     * Sie dient dazu, den UPN-Begriff X-Register im Quellcode sichtbar zu
     * machen.
     * </p>
     *
     * @return aktueller Wert des X-Registers
     * @throws IndexOutOfBoundsException
     *             wenn der Stack leer ist
     */
    public T getX() {
        return peek();
    }

    /**
     * Liefert den Wert des Y-Registers.
     *
     * <p>
     * Das Y-Register ist das Element direkt oberhalb des X-Registers, also das
     * vorletzte Element in der internen Liste.
     * </p>
     *
     * @return aktueller Wert des Y-Registers
     * @throws IndexOutOfBoundsException
     *             wenn weniger als zwei Elemente auf dem Stack liegen
     */
    public T getY() {
        return get(size() - 2);
    }

    /**
     * Vertauscht X-Register und Y-Register.
     *
     * <p>
     * Sind weniger als zwei Elemente auf dem Stack vorhanden, bleibt der Stack
     * unveraendert.
     * </p>
     */
    public void swapXY() {
        if (size() >= 2) {
            int xIndex = size() - 1;
            int yIndex = size() - 2;

            T x = get(xIndex);
            T y = get(yIndex);

            set(xIndex, y);
            set(yIndex, x);
        }
    }

    /**
     * Erzeugt eine Kopie dieses Stacks.
     *
     * <p>
     * Die Kopie enthaelt dieselben Elemente wie dieser Stack. Aenderungen an
     * der kopierten Stack-Liste veraendern die urspruengliche Stack-Liste
     * nicht.
     * </p>
     *
     * @return Kopie dieses Stacks
     */
    @Override
    public Stack<T> clone() {
        Stack<T> copy = new Stack<>();

        for (T value : this) {
            copy.add(value);
        }

        return copy;
    }
}