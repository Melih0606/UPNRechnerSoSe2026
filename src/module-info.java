module Aufgabe6b {
    requires javafx.controls;
    requires common;
    requires org.junit.jupiter.api;

    exports controller.upn;
    exports controller.upn.operator;
    exports model;

    opens application to javafx.graphics, javafx.fxml;
}