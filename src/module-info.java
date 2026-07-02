module Aufgabe7
{
   requires javafx.controls;
   requires common;
   requires org.junit.jupiter.api;
   requires org.testfx;
   requires org.testfx.junit5;

   exports application;
   exports controller.upn;
   exports controller.upn.operator;
   exports controller.upn.operator.binary;
   exports controller.upn.operator.unary;
   exports model;
   exports view;

   opens application to javafx.graphics, javafx.fxml, org.testfx;
   opens view to javafx.fxml, org.testfx;
}