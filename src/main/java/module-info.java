module gourmand {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
   // requires com.google.zxing.core;
    requires java.sql;
    requires java.prefs;
    opens gourmand.gui;
    opens gourmand.entity;
    opens gourmand.services;
    opens gourmand;
    opens gourmand.utils;
}