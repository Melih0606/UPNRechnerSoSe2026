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
 * Die Klasse ist ausschließlich für die Darstellung und die Reaktion auf
 * Button-Klicks zuständig. Die eigentliche Rechnerlogik liegt im
 * {@link UPNCore}. Nach jedem Tastendruck wird der aktuelle Zustand des
 * Rechenkerns über {@link UPNCore#getDisplayText()} und
 * {@link UPNCore#getStack()} abgefragt und in der Oberfläche angezeigt.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class CalculatorPane extends BorderPane {

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
            throw new IllegalArgumentException("UPNCore darf nicht null sein.");
        }

        this.upnCore = upnCore;
        this.displayLabel = createDisplayLabel();
        this.statusLabel = createStatusLabel();

        setPadding(new Insets(10));
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
        label.setMinHeight(50);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setStyle("-fx-border-color: black; -fx-font-size: 24px; -fx-padding: 8px;");
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
        label.setMinHeight(30);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setStyle("-fx-font-size: 12px; -fx-padding: 5px;");
        return label;
    }

    /**
     * Erstellt die Tastatur des Rechners.
     *
     * @return GridPane mit allen Rechnerbuttons
     */
    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10, 0, 10, 0));

        addButton(grid, "LN", 0, 0, () -> executeOperator(new LnOperator()));
        addButton(grid, "SIN", 1, 0, () -> executeOperator(new SinOperator()));
        addButton(grid, "COS", 2, 0, () -> executeOperator(new CosOperator()));
        addButton(grid, "TAN", 3, 0, () -> executeOperator(new TanOperator()));

        addButton(grid, "+/-", 0, 1, this::changeSign);
        addButton(grid, "1/X", 1, 1, () -> executeOperator(new ReciprocalOperator()));
        addButton(grid, "Y^X", 2, 1, () -> executeOperator(new PowerOperator()));
        addButton(grid, "SQR", 3, 1, () -> executeOperator(new SquareRootOperator()));

        addButton(grid, "LastX", 0, 2, this::pushLastX);
        addButton(grid, "X<>Y", 1, 2, this::swapXY);
        addButton(grid, "CLR", 2, 2, this::clear);
        addButton(grid, "CLX", 3, 2, this::clearX);

        addButton(grid, "7", 0, 3, () -> inputDigit(7));
        addButton(grid, "8", 1, 3, () -> inputDigit(8));
        addButton(grid, "9", 2, 3, () -> inputDigit(9));
        addButton(grid, "/", 3, 3, () -> executeOperator(new DivOperator()));

        addButton(grid, "4", 0, 4, () -> inputDigit(4));
        addButton(grid, "5", 1, 4, () -> inputDigit(5));
        addButton(grid, "6", 2, 4, () -> inputDigit(6));
        addButton(grid, "*", 3, 4, () -> executeOperator(new MulOperator()));

        addButton(grid, "1", 0, 5, () -> inputDigit(1));
        addButton(grid, "2", 1, 5, () -> inputDigit(2));
        addButton(grid, "3", 2, 5, () -> inputDigit(3));
        addButton(grid, "-", 3, 5, () -> executeOperator(new SubOperator()));

        addButton(grid, "0", 0, 6, () -> inputDigit(0));
        addButton(grid, ".", 1, 6, this::inputDecimalPoint);
        addButton(grid, "Enter", 2, 6, this::enter);
        addButton(grid, "+", 3, 6, () -> executeOperator(new AddOperator()));

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
        button.setMinSize(80, 40);
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
     * Verarbeitet die X<>Y-Taste.
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
            statusLabel.setStyle("-fx-font-size: 12px; -fx-padding: 5px; -fx-text-fill: red;");
            statusLabel.setText("Fehler");
        } else {
            statusLabel.setStyle("-fx-font-size: 12px; -fx-padding: 5px; -fx-text-fill: black;");
            statusLabel.setText("Stack: " + upnCore.getStack().size() + " Elemente");
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
        statusLabel.setStyle("-fx-font-size: 12px; -fx-padding: 5px; -fx-text-fill: red;");
        statusLabel.setText(ex.getMessage());
    }

    /**
     * Zeigt einen unerwarteten Programmfehler in der Statuszeile an.
     *
     * @param ex
     *            unerwarteter Fehler
     */
    private void showUnexpectedError(RuntimeException ex) {
        displayLabel.setText("Err");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-padding: 5px; -fx-text-fill: red;");
        statusLabel.setText("Unerwarteter Fehler: " + ex.getMessage());
    }
}