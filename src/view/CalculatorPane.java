package view;

import common.exception.UserException;
import controller.upn.UPNCore;
import controller.upn.operator.Operator;
import controller.upn.operator.binary.AddOperator;
import controller.upn.operator.binary.DivOperator;
import controller.upn.operator.binary.MulOperator;
import controller.upn.operator.binary.PowerOperator;
import controller.upn.operator.binary.SubOperator;
import controller.upn.operator.unary.CosOperator;
import controller.upn.operator.unary.LnOperator;
import controller.upn.operator.unary.ReciprocalOperator;
import controller.upn.operator.unary.SinOperator;
import controller.upn.operator.unary.SquareRootOperator;
import controller.upn.operator.unary.TanOperator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * <p>
 * Für Aufgabe 7 besitzen alle wichtigen grafischen Komponenten eindeutige
 * JavaFX-Ids. Dadurch können sie in TestFX-Tests zuverlässig angesprochen
 * werden.
 * </p>
 *
 * @author Melih Acar, Kevin Piotrowski und Dmitrij Ogulev
 */
public class CalculatorPane extends BorderPane
{
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
         + "-fx-font-size: 24px; " + "-fx-padding: 8px;";

   /**
    * CSS-Style der normalen Statuszeile.
    */
   private static final String STATUS_STYLE_NORMAL = "-fx-font-size: 12px; "
         + "-fx-padding: 5px; " + "-fx-text-fill: black;";

   /**
    * CSS-Style der Statuszeile im Fehlerfall.
    */
   private static final String STATUS_STYLE_ERROR = "-fx-font-size: 12px; "
         + "-fx-padding: 5px; " + "-fx-text-fill: red;";

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
   private static final String STATUS_UNEXPECTED_ERROR_PREFIX =
         "Unerwarteter Fehler: ";

   /**
    * Text der Anzeige im Fehlerfall.
    */
   private static final String DISPLAY_ERROR_TEXT = "Err";

   /**
    * Id des Display-Labels für TestFX.
    */
   private static final String ID_DISPLAY_LABEL = "displayLabel";

   /**
    * Id des Status-Labels für TestFX.
    */
   private static final String ID_STATUS_LABEL = "statusLabel";

   /**
    * Präfix für die Ids der Ziffernbuttons.
    */
   private static final String ID_BUTTON_PREFIX = "button";

   /**
    * Id des Dezimalpunkt-Buttons für TestFX.
    */
   private static final String ID_BUTTON_DECIMAL_POINT = "buttonDecimalPoint";

   /**
    * Id des Enter-Buttons für TestFX.
    */
   private static final String ID_BUTTON_ENTER = "buttonEnter";

   /**
    * Id des Additions-Buttons für TestFX.
    */
   private static final String ID_BUTTON_ADD = "buttonAdd";

   /**
    * Id des Subtraktions-Buttons für TestFX.
    */
   private static final String ID_BUTTON_SUBTRACT = "buttonSubtract";

   /**
    * Id des Multiplikations-Buttons für TestFX.
    */
   private static final String ID_BUTTON_MULTIPLY = "buttonMultiply";

   /**
    * Id des Divisions-Buttons für TestFX.
    */
   private static final String ID_BUTTON_DIVIDE = "buttonDivide";

   /**
    * Id des CLR-Buttons für TestFX.
    */
   private static final String ID_BUTTON_CLEAR = "buttonClear";

   /**
    * Id des CLX-Buttons für TestFX.
    */
   private static final String ID_BUTTON_CLEAR_X = "buttonClearX";

   /**
    * Id des LastX-Buttons für TestFX.
    */
   private static final String ID_BUTTON_LAST_X = "buttonLastX";

   /**
    * Id des X&lt;&gt;Y-Buttons für TestFX.
    */
   private static final String ID_BUTTON_SWAP_XY = "buttonSwapXY";

   /**
    * Id des Vorzeichenwechsel-Buttons für TestFX.
    */
   private static final String ID_BUTTON_CHANGE_SIGN = "buttonChangeSign";

   /**
    * Id des Kehrwert-Buttons für TestFX.
    */
   private static final String ID_BUTTON_RECIPROCAL = "buttonReciprocal";

   /**
    * Id des Potenz-Buttons für TestFX.
    */
   private static final String ID_BUTTON_POWER = "buttonPower";

   /**
    * Id des Quadratwurzel-Buttons für TestFX.
    */
   private static final String ID_BUTTON_SQUARE_ROOT = "buttonSquareRoot";

   /**
    * Id des Logarithmus-Buttons für TestFX.
    */
   private static final String ID_BUTTON_LN = "buttonLn";

   /**
    * Id des Sinus-Buttons für TestFX.
    */
   private static final String ID_BUTTON_SIN = "buttonSin";

   /**
    * Id des Cosinus-Buttons für TestFX.
    */
   private static final String ID_BUTTON_COS = "buttonCos";

   /**
    * Id des Tangens-Buttons für TestFX.
    */
   private static final String ID_BUTTON_TAN = "buttonTan";

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
    *           Rechenkern, der durch die Oberfläche bedient wird; darf nicht
    *           {@code null} sein
    * @throws IllegalArgumentException
    *           wenn {@code upnCore} {@code null} ist
    */
   public CalculatorPane(UPNCore upnCore)
   {
      if (upnCore == null)
      {
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
    * Erstellt das Display-Label des Rechners.
    *
    * <p>
    * Die Id wird gesetzt, damit TestFX das Label in Oberflächentests eindeutig
    * über {@code #displayLabel} finden kann.
    * </p>
    *
    * @return Label für die Rechneranzeige
    */
   private Label createDisplayLabel()
   {
      Label label = new Label();
      label.setId(ID_DISPLAY_LABEL);
      label.setMaxWidth(Double.MAX_VALUE);
      label.setMinHeight(DISPLAY_MIN_HEIGHT);
      label.setAlignment(Pos.CENTER_RIGHT);
      label.setStyle(DISPLAY_STYLE);
      return label;
   }

   /**
    * Erstellt das Status-Label des Rechners.
    *
    * <p>
    * Die Id wird gesetzt, damit TestFX das Label in Oberflächentests eindeutig
    * über {@code #statusLabel} finden kann.
    * </p>
    *
    * @return Label für Statusinformationen
    */
   private Label createStatusLabel()
   {
      Label label = new Label();
      label.setId(ID_STATUS_LABEL);
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
   private GridPane createButtonGrid()
   {
      GridPane grid = new GridPane();
      grid.setHgap(GRID_GAP);
      grid.setVgap(GRID_GAP);
      grid.setPadding(new Insets(GRID_VERTICAL_PADDING, GRID_HORIZONTAL_PADDING,
            GRID_VERTICAL_PADDING, GRID_HORIZONTAL_PADDING));

      addButton(grid, BUTTON_LN, ID_BUTTON_LN, COLUMN_ZERO, ROW_ZERO,
            new OperatorButtonEventHandler(new LnOperator()));
      addButton(grid, BUTTON_SIN, ID_BUTTON_SIN, COLUMN_ONE, ROW_ZERO,
            new OperatorButtonEventHandler(new SinOperator()));
      addButton(grid, BUTTON_COS, ID_BUTTON_COS, COLUMN_TWO, ROW_ZERO,
            new OperatorButtonEventHandler(new CosOperator()));
      addButton(grid, BUTTON_TAN, ID_BUTTON_TAN, COLUMN_THREE, ROW_ZERO,
            new OperatorButtonEventHandler(new TanOperator()));

      addButton(grid, BUTTON_CHANGE_SIGN, ID_BUTTON_CHANGE_SIGN, COLUMN_ZERO,
            ROW_ONE, new ChangeSignButtonEventHandler());
      addButton(grid, BUTTON_RECIPROCAL, ID_BUTTON_RECIPROCAL, COLUMN_ONE,
            ROW_ONE, new OperatorButtonEventHandler(new ReciprocalOperator()));
      addButton(grid, BUTTON_POWER, ID_BUTTON_POWER, COLUMN_TWO, ROW_ONE,
            new OperatorButtonEventHandler(new PowerOperator()));
      addButton(grid, BUTTON_SQUARE_ROOT, ID_BUTTON_SQUARE_ROOT, COLUMN_THREE,
            ROW_ONE, new OperatorButtonEventHandler(new SquareRootOperator()));

      addButton(grid, BUTTON_LAST_X, ID_BUTTON_LAST_X, COLUMN_ZERO, ROW_TWO,
            new PushLastXButtonEventHandler());
      addButton(grid, BUTTON_SWAP_XY, ID_BUTTON_SWAP_XY, COLUMN_ONE, ROW_TWO,
            new SwapXYButtonEventHandler());
      addButton(grid, BUTTON_CLEAR, ID_BUTTON_CLEAR, COLUMN_TWO, ROW_TWO,
            new ClearButtonEventHandler());
      addButton(grid, BUTTON_CLEAR_X, ID_BUTTON_CLEAR_X, COLUMN_THREE, ROW_TWO,
            new ClearXButtonEventHandler());

      addButton(grid, DIGIT_SEVEN, ID_BUTTON_PREFIX + DIGIT_SEVEN, COLUMN_ZERO,
            ROW_THREE, new DigitButtonEventHandler(VALUE_SEVEN));
      addButton(grid, DIGIT_EIGHT, ID_BUTTON_PREFIX + DIGIT_EIGHT, COLUMN_ONE,
            ROW_THREE, new DigitButtonEventHandler(VALUE_EIGHT));
      addButton(grid, DIGIT_NINE, ID_BUTTON_PREFIX + DIGIT_NINE, COLUMN_TWO,
            ROW_THREE, new DigitButtonEventHandler(VALUE_NINE));
      addButton(grid, BUTTON_DIVIDE, ID_BUTTON_DIVIDE, COLUMN_THREE, ROW_THREE,
            new OperatorButtonEventHandler(new DivOperator()));

      addButton(grid, DIGIT_FOUR, ID_BUTTON_PREFIX + DIGIT_FOUR, COLUMN_ZERO,
            ROW_FOUR, new DigitButtonEventHandler(VALUE_FOUR));
      addButton(grid, DIGIT_FIVE, ID_BUTTON_PREFIX + DIGIT_FIVE, COLUMN_ONE,
            ROW_FOUR, new DigitButtonEventHandler(VALUE_FIVE));
      addButton(grid, DIGIT_SIX, ID_BUTTON_PREFIX + DIGIT_SIX, COLUMN_TWO,
            ROW_FOUR, new DigitButtonEventHandler(VALUE_SIX));
      addButton(grid, BUTTON_MULTIPLY, ID_BUTTON_MULTIPLY, COLUMN_THREE,
            ROW_FOUR, new OperatorButtonEventHandler(new MulOperator()));

      addButton(grid, DIGIT_ONE, ID_BUTTON_PREFIX + DIGIT_ONE, COLUMN_ZERO,
            ROW_FIVE, new DigitButtonEventHandler(VALUE_ONE));
      addButton(grid, DIGIT_TWO, ID_BUTTON_PREFIX + DIGIT_TWO, COLUMN_ONE,
            ROW_FIVE, new DigitButtonEventHandler(VALUE_TWO));
      addButton(grid, DIGIT_THREE, ID_BUTTON_PREFIX + DIGIT_THREE, COLUMN_TWO,
            ROW_FIVE, new DigitButtonEventHandler(VALUE_THREE));
      addButton(grid, BUTTON_SUBTRACT, ID_BUTTON_SUBTRACT, COLUMN_THREE,
            ROW_FIVE, new OperatorButtonEventHandler(new SubOperator()));

      addButton(grid, DIGIT_ZERO, ID_BUTTON_PREFIX + DIGIT_ZERO, COLUMN_ZERO,
            ROW_SIX, new DigitButtonEventHandler(VALUE_ZERO));
      addButton(grid, BUTTON_DECIMAL_POINT, ID_BUTTON_DECIMAL_POINT, COLUMN_ONE,
            ROW_SIX, new DecimalPointButtonEventHandler());
      addButton(grid, BUTTON_ENTER, ID_BUTTON_ENTER, COLUMN_TWO, ROW_SIX,
            new EnterButtonEventHandler());
      addButton(grid, BUTTON_ADD, ID_BUTTON_ADD, COLUMN_THREE, ROW_SIX,
            new OperatorButtonEventHandler(new AddOperator()));

      return grid;
   }

   /**
    * Fügt einen Button in das Tastaturraster ein.
    *
    * <p>
    * Jeder Button erhält eine eindeutige Id. Diese Ids werden von TestFX
    * verwendet, um Buttons in Oberflächentests unabhängig von ihrer sichtbaren
    * Beschriftung anzusprechen.
    * </p>
    *
    * @param grid
    *           GridPane, in das der Button eingefügt wird
    * @param text
    *           sichtbare Beschriftung des Buttons
    * @param id
    *           eindeutige JavaFX-Id des Buttons für TestFX
    * @param column
    *           Spalte im GridPane
    * @param row
    *           Zeile im GridPane
    * @param action
    *           Aktion, die beim Klick auf den Button ausgeführt wird
    */
   private void addButton(GridPane grid, String text, String id, int column,
         int row, EventHandler<ActionEvent> action)
   {
      Button button = new Button(text);
      button.setId(id);
      button.setMaxWidth(Double.MAX_VALUE);
      button.setMinSize(BUTTON_MIN_WIDTH, BUTTON_MIN_HEIGHT);
      button.setOnAction(action);

      grid.add(button, column, row);
   }

   /**
    * Aktualisiert Display und Statuszeile aus dem aktuellen Zustand des
    * Rechenkerns.
    */
   private void updateView()
   {
      displayLabel.setText(upnCore.getDisplayText());

      if (upnCore.hasError())
      {
         statusLabel.setStyle(STATUS_STYLE_ERROR);
         statusLabel.setText(STATUS_ERROR);
      }
      else
      {
         statusLabel.setStyle(STATUS_STYLE_NORMAL);
         statusLabel.setText(STATUS_STACK_PREFIX + upnCore.getStack().size()
               + STATUS_STACK_SUFFIX);
      }
   }

   /**
    * Aktualisiert die View nach einem Anwenderfehler.
    *
    * @param ex
    *           aufgetretener Anwenderfehler
    */
   private void updateViewWithUserError(UserException ex)
   {
      displayLabel.setText(upnCore.getDisplayText());
      statusLabel.setStyle(STATUS_STYLE_ERROR);
      statusLabel.setText(ex.getMessage());
   }

   /**
    * Zeigt einen unerwarteten Programmfehler in der Statuszeile an.
    *
    * @param ex
    *           unerwarteter Fehler
    */
   private void showUnexpectedError(RuntimeException ex)
   {
      displayLabel.setText(DISPLAY_ERROR_TEXT);
      statusLabel.setStyle(STATUS_STYLE_ERROR);
      statusLabel.setText(STATUS_UNEXPECTED_ERROR_PREFIX + ex.getMessage());
   }

   /**
    * Gemeinsame Basisklasse für sichere Button-EventHandler.
    *
    * <p>
    * Die Unterklassen führen nur die konkrete Aktion aus. Fehlerbehandlung und
    * Aktualisierung der Oberfläche werden zentral in dieser Klasse erledigt.
    * Dadurch bleibt die Behandlung aller Buttons einheitlich.
    * </p>
    */
   private abstract class AbstractSafeButtonEventHandler
         implements EventHandler<ActionEvent>
   {
      @Override
      public final void handle(ActionEvent event)
      {
         try
         {
            execute();
            updateView();
         }
         catch (UserException ex)
         {
            updateViewWithUserError(ex);
         }
         catch (RuntimeException ex)
         {
            showUnexpectedError(ex);
         }
      }

      /**
       * Führt die eigentliche Buttonaktion aus.
       *
       * @throws UserException
       *            wenn beim Rechenkern ein Anwenderfehler entsteht
       */
      protected abstract void execute() throws UserException;
   }

   /**
    * EventHandler für Ziffernbuttons.
    */
   private class DigitButtonEventHandler extends AbstractSafeButtonEventHandler
   {
      /**
       * Zu verarbeitende Ziffer.
       */
      private final int digit;

      /**
       * Erzeugt einen EventHandler für eine Ziffer.
       *
       * @param digit
       *            Ziffer, die an den Rechenkern übergeben wird
       */
      DigitButtonEventHandler(int digit)
      {
         this.digit = digit;
      }

      @Override
      protected void execute()
      {
         upnCore.inputDigit(digit);
      }
   }

   /**
    * EventHandler für den Dezimalpunkt.
    */
   private class DecimalPointButtonEventHandler
         extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute()
      {
         upnCore.inputDecimalPoint();
      }
   }

   /**
    * EventHandler für den Vorzeichenwechsel.
    */
   private class ChangeSignButtonEventHandler
         extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute() throws UserException
      {
         upnCore.changeSign();
      }
   }

   /**
    * EventHandler für Enter.
    */
   private class EnterButtonEventHandler extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute() throws UserException
      {
         upnCore.enter();
      }
   }

   /**
    * EventHandler für CLR.
    */
   private class ClearButtonEventHandler extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute()
      {
         upnCore.clear();
      }
   }

   /**
    * EventHandler für CLX.
    */
   private class ClearXButtonEventHandler
         extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute() throws UserException
      {
         upnCore.clearX();
      }
   }

   /**
    * EventHandler für LastX.
    */
   private class PushLastXButtonEventHandler
         extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute() throws UserException
      {
         upnCore.pushLastX();
      }
   }

   /**
    * EventHandler für X&lt;&gt;Y.
    */
   private class SwapXYButtonEventHandler extends AbstractSafeButtonEventHandler
   {
      @Override
      protected void execute() throws UserException
      {
         upnCore.swapXY();
      }
   }

   /**
    * EventHandler für mathematische Operatoren.
    */
   private class OperatorButtonEventHandler extends AbstractSafeButtonEventHandler
   {
      /**
       * Auszuführender Operator.
       */
      private final Operator operator;

      /**
       * Erzeugt einen EventHandler für einen mathematischen Operator.
       *
       * @param operator
       *            Operator, der beim Buttonklick ausgeführt wird
       */
      OperatorButtonEventHandler(Operator operator)
      {
         this.operator = operator;
      }

      @Override
      protected void execute() throws UserException
      {
         upnCore.applyOperator(operator);
      }
   }
}