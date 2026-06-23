package view;

import common.exception.UserException;
import controller.upn.UPNCore;
import controller.upn.operator.Operator;
import dummy.controller.upn.operator.binary.AddOperator;
import dummy.controller.upn.operator.binary.DivOperator;
import dummy.controller.upn.operator.binary.MulOperator;
import dummy.controller.upn.operator.binary.PowerOperator;
import dummy.controller.upn.operator.binary.SubOperator;
import dummy.controller.upn.operator.unary.CosOperator;
import dummy.controller.upn.operator.unary.LnOperator;
import dummy.controller.upn.operator.unary.ReciprocalOperator;
import dummy.controller.upn.operator.unary.SinOperator;
import dummy.controller.upn.operator.unary.SquareRootOperator;
import dummy.controller.upn.operator.unary.TanOperator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * JavaFX-Oberfläche des UPN-Rechners.
 *
 * <p>
 * Diese Klasse ist ausschließlich für Darstellung und Benutzerinteraktion
 * zuständig. Die eigentliche Rechnerlogik liegt im {@link UPNCore}. Nach jedem
 * Tastendruck wird der aktuelle Zustand des Rechenkerns über
 * {@link UPNCore#getDisplayText()} und {@link UPNCore#getStack()} abgefragt und
 * in der Oberfläche angezeigt.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class CalculatorPane extends BorderPane {

    /**
     * Fehlermeldung bei fehlendem Rechenkern.
     */
    private static final String ERROR_CORE_NULL = "UPNCore darf nicht null sein.";

    /**
     * Buttonbeschriftung LN.
     */
    private static final String BUTTON_LN = "LN";

    /**
     * Buttonbeschriftung SIN.
     */
    private static final String BUTTON_SIN = "SIN";

    /**
     * Buttonbeschriftung COS.
     */
    private static final String BUTTON_COS = "COS";

    /**
     * Buttonbeschriftung TAN.
     */
    private static final String BUTTON_TAN = "TAN";

    /**
     * Buttonbeschriftung Vorzeichenwechsel.
     */
    private static final String BUTTON_CHANGE_SIGN = "+/-";

    /**
     * Buttonbeschriftung Kehrwert.
     */
    private static final String BUTTON_RECIPROCAL = "1/X";

    /**
     * Buttonbeschriftung Potenz.
     */
    private static final String BUTTON_POWER = "Y^X";

    /**
     * Buttonbeschriftung Quadratwurzel.
     */
    private static final String BUTTON_SQUARE_ROOT = "SQR";

    /**
     * Buttonbeschriftung LastX.
     */
    private static final String BUTTON_LAST_X = "LastX";

    /**
     * Buttonbeschriftung X<>Y.
     */
    private static final String BUTTON_SWAP_XY = "X<>Y";

    /**
     * Buttonbeschriftung CLR.
     */
    private static final String BUTTON_CLEAR = "CLR";

    /**
     * Buttonbeschriftung CLX.
     */
    private static final String BUTTON_CLEAR_X = "CLX";

    /**
     * Buttonbeschriftung Division.
     */
    private static final String BUTTON_DIVIDE = "/";

    /**
     * Buttonbeschriftung Multiplikation.
     */
    private static final String BUTTON_MULTIPLY = "*";

    /**
     * Buttonbeschriftung Subtraktion.
     */
    private static final String BUTTON_SUBTRACT = "-";

    /**
     * Buttonbeschriftung Dezimalpunkt.
     */
    private static final String BUTTON_DECIMAL_POINT = ".";

    /**
     * Buttonbeschriftung Enter.
     */
    private static final String BUTTON_ENTER = "Enter";

    /**
     * Buttonbeschriftung Addition.
     */
    private static final String BUTTON_ADD = "+";

    /**
     * Beschriftung der Zifferntaste 0.
     */
    private static final String DIGIT_ZERO = "0";

    /**
     * Beschriftung der Zifferntaste 1.
     */
    private static final String DIGIT_ONE = "1";

    /**
     * Beschriftung der Zifferntaste 2.
     */
    private static final String DIGIT_TWO = "2";

    /**
     * Beschriftung der Zifferntaste 3.
     */
    private static final String DIGIT_THREE = "3";

    /**
     * Beschriftung der Zifferntaste 4.
     */
    private static final String DIGIT_FOUR = "4";

    /**
     * Beschriftung der Zifferntaste 5.
     */
    private static final String DIGIT_FIVE = "5";

    /**
     * Beschriftung der Zifferntaste 6.
     */
    private static final String DIGIT_SIX = "6";

    /**
     * Beschriftung der Zifferntaste 7.
     */
    private static final String DIGIT_SEVEN = "7";

    /**
     * Beschriftung der Zifferntaste 8.
     */
    private static final String DIGIT_EIGHT = "8";

    /**
     * Beschriftung der Zifferntaste 9.
     */
    private static final String DIGIT_NINE = "9";

    /**
     * Zahlenwert 0.
     */
    private static final int VALUE_ZERO = 0;

    /**
     * Zahlenwert 1.
     */
    private static final int VALUE_ONE = 1;

    /**
     * Zahlenwert 2.
     */
    private static final int VALUE_TWO = 2;

    /**
     * Zahlenwert 3.
     */
    private static final int VALUE_THREE = 3;

    /**
     * Zahlenwert 4.
     */
    private static final int VALUE_FOUR = 4;

    /**
     * Zahlenwert 5.
     */
    private static final int VALUE_FIVE = 5;

    /**
     * Zahlenwert 6.
     */
    private static final int VALUE_SIX = 6;

    /**
     * Zahlenwert 7.
     */
    private static final int VALUE_SEVEN = 7;

    /**
     * Zahlenwert 8.
     */
    private static final int VALUE_EIGHT = 8;

    /**
     * Zahlenwert 9.
     */
    private static final int VALUE_NINE = 9;

    /**
     * Erste Spalte des Tastaturrasters.
     */
    private static final int COLUMN_ZERO = 0;

    /**
     * Zweite Spalte des Tastaturrasters.
     */
    private static final int COLUMN_ONE = 1;

    /**
     * Dritte Spalte des Tastaturrasters.
     */
    private static final int COLUMN_TWO = 2;

    /**
     * Vierte Spalte des Tastaturrasters.
     */
    private static final int COLUMN_THREE = 3;

    /**
     * Erste Zeile des Tastaturrasters.
     */
    private static final int ROW_ZERO = 0;

    /**
     * Zweite Zeile des Tastaturrasters.
     */
    private static final int ROW_ONE = 1;

    /**
     * Dritte Zeile des Tastaturrasters.
     */
    private static final int ROW_TWO = 2;

    /**
     * Vierte Zeile des Tastaturrasters.
     */
    private static final int ROW_THREE = 3;

    /**
     * Fünfte Zeile des Tastaturrasters.
     */
    private static final int ROW_FOUR = 4;

    /**
     * Sechste Zeile des Tastaturrasters.
     */
    private static final int ROW_FIVE = 5;

    /**
     * Siebte Zeile des Tastaturrasters.
     */
    private static final int ROW_SIX = 6;

    /**
     * Außenabstand der gesamten Oberfläche.
     */
    private static final int PANE_PADDING = 10;

    /**
     * Vertikaler Innenabstand des Tastaturrasters.
     */
    private static final int GRID_VERTICAL_PADDING = 10;

    /**
     * Horizontaler Innenabstand des Tastaturrasters.
     */
    private static final int GRID_HORIZONTAL_PADDING = 0;

    /**
     * Abstand zwischen den Buttons.
     */
    private static final int GRID_GAP = 5;

    /**
     * Mindesthöhe der Anzeige.
     */
    private static final int DISPLAY_MIN_HEIGHT = 50;

    /**
     * Mindesthöhe der Statuszeile.
     */
    private static final int STATUS_MIN_HEIGHT = 30;

    /**
     * Mindestbreite eines Buttons.
     */
    private static final int BUTTON_MIN_WIDTH = 80;

    /**
     * Mindesthöhe eines Buttons.
     */
    private static final int BUTTON_MIN_HEIGHT = 40;

    /**
     * CSS-Style der Anzeige.
     */
    private static final String DISPLAY_STYLE = "-fx-border-color: black; "
            + "-fx-font-size: 24px; "
            + "-fx-padding: 8px;";

    /**
     * CSS-Style der normalen Statuszeile.
     */
    private static final String STATUS_STYLE_NORMAL = "-fx-font-size: 12px; "
            + "-fx-padding: 5px; "
            + "-fx-text-fill: black;";

    /**
     * CSS-Style der Statuszeile im Fehlerfall.
     */
    private static final String STATUS_STYLE_ERROR = "-fx-font-size: 12px; "
            + "-fx-padding: 5px; "
            + "-fx-text-fill: red;";

    /**
     * Präfix der Stack-Anzeige.
     */
    private static final String STATUS_STACK_PREFIX = "Stack: ";

    /**
     * Suffix der Stack-Anzeige.
     */
    private static final String STATUS_STACK_SUFFIX = " Elemente";

    /**
     * Allgemeiner Fehlertext.
     */
    private static final String STATUS_ERROR = "Fehler";

    /**
     * Präfix für unerwartete Fehler.
     */
    private static final String STATUS_UNEXPECTED_ERROR_PREFIX = "Unerwarteter Fehler: ";

    /**
     * Text der Anzeige im Fehlerfall.
     */
    private static final String DISPLAY_ERROR_TEXT = "Err";

    /**
     * Rechenkern des UPN-Rechners.
     */
    private final UPNCore upnCore;

    /**
     * Anzeige des Rechners.
     */
    private final Label displayLabel;

    /**
     * Statuszeile für Stack-Anzahl oder Fehlermeldungen.
     */
    private final Label statusLabel;

    /**
     * Erzeugt die Oberfläche des UPN-Rechners.
     *
     * @param upnCore
     *            Rechenkern, der durch die Oberfläche bedient wird; darf nicht
     *            {@code null} sein
     * @throws IllegalArgumentException
     *             wenn {@code upnCore} {@code null} ist
     */
    public CalculatorPane(UPNCore upnCore) {
        if (upnCore == null) {
            throw new IllegalArgumentException(ERROR_CORE_NULL);
        }

        this.upnCore = upnCore;
        this.displayLabel = createDisplayLabel();
        this.statusLabel = createStatusLabel();

        setPadding(new Insets(PANE_PADDING));
        setTop(displayLabel);
        setCenter(createButtonGrid());
        setBottom(statusLabel);

        updateView();
    }

    /**
     * Erstellt das Display-Label.
     *
     * @return Label für die Rechneranzeige
     */
    private Label createDisplayLabel() {
        Label label = new Label();
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMinHeight(DISPLAY_MIN_HEIGHT);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setStyle(DISPLAY_STYLE);
        return label;
    }

    /**
     * Erstellt die Statuszeile.
     *
     * @return Label für Statusinformationen
     */
    private Label createStatusLabel() {
        Label label = new Label();
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMinHeight(STATUS_MIN_HEIGHT);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setStyle(STATUS_STYLE_NORMAL);
        return label;
    }

    /**
     * Erstellt die Tastatur des Rechners.
     *
     * @return GridPane mit allen Rechnerbuttons
     */
    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(GRID_GAP);
        grid.setVgap(GRID_GAP);
        grid.setPadding(new Insets(GRID_VERTICAL_PADDING, GRID_HORIZONTAL_PADDING,
                GRID_VERTICAL_PADDING, GRID_HORIZONTAL_PADDING));

        addButton(grid, BUTTON_LN, COLUMN_ZERO, ROW_ZERO, () -> executeOperator(new LnOperator()));
        addButton(grid, BUTTON_SIN, COLUMN_ONE, ROW_ZERO, () -> executeOperator(new SinOperator()));
        addButton(grid, BUTTON_COS, COLUMN_TWO, ROW_ZERO, () -> executeOperator(new CosOperator()));
        addButton(grid, BUTTON_TAN, COLUMN_THREE, ROW_ZERO, () -> executeOperator(new TanOperator()));

        addButton(grid, BUTTON_CHANGE_SIGN, COLUMN_ZERO, ROW_ONE, this::changeSign);
        addButton(grid, BUTTON_RECIPROCAL, COLUMN_ONE, ROW_ONE,
                () -> executeOperator(new ReciprocalOperator()));
        addButton(grid, BUTTON_POWER, COLUMN_TWO, ROW_ONE,
                () -> executeOperator(new PowerOperator()));
        addButton(grid, BUTTON_SQUARE_ROOT, COLUMN_THREE, ROW_ONE,
                () -> executeOperator(new SquareRootOperator()));

        addButton(grid, BUTTON_LAST_X, COLUMN_ZERO, ROW_TWO, this::pushLastX);
        addButton(grid, BUTTON_SWAP_XY, COLUMN_ONE, ROW_TWO, this::swapXY);
        addButton(grid, BUTTON_CLEAR, COLUMN_TWO, ROW_TWO, this::clear);
        addButton(grid, BUTTON_CLEAR_X, COLUMN_THREE, ROW_TWO, this::clearX);

        addButton(grid, DIGIT_SEVEN, COLUMN_ZERO, ROW_THREE, () -> inputDigit(VALUE_SEVEN));
        addButton(grid, DIGIT_EIGHT, COLUMN_ONE, ROW_THREE, () -> inputDigit(VALUE_EIGHT));
        addButton(grid, DIGIT_NINE, COLUMN_TWO, ROW_THREE, () -> inputDigit(VALUE_NINE));
        addButton(grid, BUTTON_DIVIDE, COLUMN_THREE, ROW_THREE,
                () -> executeOperator(new DivOperator()));

        addButton(grid, DIGIT_FOUR, COLUMN_ZERO, ROW_FOUR, () -> inputDigit(VALUE_FOUR));
        addButton(grid, DIGIT_FIVE, COLUMN_ONE, ROW_FOUR, () -> inputDigit(VALUE_FIVE));
        addButton(grid, DIGIT_SIX, COLUMN_TWO, ROW_FOUR, () -> inputDigit(VALUE_SIX));
        addButton(grid, BUTTON_MULTIPLY, COLUMN_THREE, ROW_FOUR,
                () -> executeOperator(new MulOperator()));

        addButton(grid, DIGIT_ONE, COLUMN_ZERO, ROW_FIVE, () -> inputDigit(VALUE_ONE));
        addButton(grid, DIGIT_TWO, COLUMN_ONE, ROW_FIVE, () -> inputDigit(VALUE_TWO));
        addButton(grid, DIGIT_THREE, COLUMN_TWO, ROW_FIVE, () -> inputDigit(VALUE_THREE));
        addButton(grid, BUTTON_SUBTRACT, COLUMN_THREE, ROW_FIVE,
                () -> executeOperator(new SubOperator()));

        addButton(grid, DIGIT_ZERO, COLUMN_ZERO, ROW_SIX, () -> inputDigit(VALUE_ZERO));
        addButton(grid, BUTTON_DECIMAL_POINT, COLUMN_ONE, ROW_SIX, this::inputDecimalPoint);
        addButton(grid, BUTTON_ENTER, COLUMN_TWO, ROW_SIX, this::enter);
        addButton(grid, BUTTON_ADD, COLUMN_THREE, ROW_SIX, () -> executeOperator(new AddOperator()));

        return grid;
    }

    /**
     * Fügt einen Button in das Grid ein.
     *
     * @param grid
     *            GridPane, in das der Button eingefügt wird
     * @param text
     *            Buttonbeschriftung
     * @param column
     *            Spalte im Grid
     * @param row
     *            Zeile im Grid
     * @param action
     *            Aktion, die beim Klick ausgeführt wird
     */
    private void addButton(GridPane grid, String text, int column, int row, Runnable action) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
        button.setOnAction(event -> action.run());

        grid.add(button, column, row);
    }

    /**
     * Verarbeitet eine Zifferneingabe.
     *
     * @param digit
     *            gedrückte Ziffer
     */
    private void inputDigit(int digit) {
        try {
            upnCore.inputDigit(digit);
            updateView();
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die Dezimalpunkttaste.
     */
    private void inputDecimalPoint() {
        try {
            upnCore.inputDecimalPoint();
            updateView();
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die Vorzeichentaste.
     */
    private void changeSign() {
        try {
            upnCore.changeSign();
            updateView();
        } catch (UserException ex) {
            updateViewWithUserError(ex);
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die Enter-Taste.
     */
    private void enter() {
        try {
            upnCore.enter();
            updateView();
        } catch (UserException ex) {
            updateViewWithUserError(ex);
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die CLR-Taste.
     */
    private void clear() {
        try {
            upnCore.clear();
            updateView();
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die CLX-Taste.
     */
    private void clearX() {
        try {
            upnCore.clearX();
            updateView();
        } catch (UserException ex) {
            updateViewWithUserError(ex);
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die LastX-Taste.
     */
    private void pushLastX() {
        try {
            upnCore.pushLastX();
            updateView();
        } catch (UserException ex) {
            updateViewWithUserError(ex);
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Verarbeitet die X&lt;&gt;Y-Taste.
     */
    private void swapXY() {
        try {
            upnCore.swapXY();
            updateView();
        } catch (UserException ex) {
            updateViewWithUserError(ex);
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Führt einen mathematischen Operator aus.
     *
     * @param operator
     *            auszuführender Operator
     */
    private void executeOperator(Operator operator) {
        try {
            upnCore.applyOperator(operator);
            updateView();
        } catch (UserException ex) {
            updateViewWithUserError(ex);
        } catch (RuntimeException ex) {
            showUnexpectedError(ex);
        }
    }

    /**
     * Aktualisiert Display und Statuszeile aus dem aktuellen Zustand des
     * Rechenkerns.
     */
    private void updateView() {
        displayLabel.setText(upnCore.getDisplayText());

        if (upnCore.hasError()) {
            statusLabel.setStyle(STATUS_STYLE_ERROR);
            statusLabel.setText(STATUS_ERROR);
        } else {
            statusLabel.setStyle(STATUS_STYLE_NORMAL);
            statusLabel.setText(STATUS_STACK_PREFIX + upnCore.getStack().size()
                    + STATUS_STACK_SUFFIX);
        }
    }

    /**
     * Aktualisiert die View nach einem Anwenderfehler.
     *
     * @param ex
     *            aufgetretener Anwenderfehler
     */
    private void updateViewWithUserError(UserException ex) {
        displayLabel.setText(upnCore.getDisplayText());
        statusLabel.setStyle(STATUS_STYLE_ERROR);
        statusLabel.setText(ex.getMessage());
    }

    /**
     * Zeigt einen unerwarteten Programmfehler in der Statuszeile an.
     *
     * @param ex
     *            unerwarteter Fehler
     */
    private void showUnexpectedError(RuntimeException ex) {
        displayLabel.setText(DISPLAY_ERROR_TEXT);
        statusLabel.setStyle(STATUS_STYLE_ERROR);
        statusLabel.setText(STATUS_UNEXPECTED_ERROR_PREFIX + ex.getMessage());
    }
}