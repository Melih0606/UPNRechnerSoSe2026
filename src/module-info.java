module Aufgabe6a {
    requires javafx.controls;
    requires common;

    exports controller.upn;
    exports controller.upn.operator;
    exports model;

    opens application to javafx.graphics, javafx.fxml;
}