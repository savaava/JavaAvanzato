module com.servicesproject {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.servicesproject.main;
    opens com.servicesproject.main to javafx.fxml;
    exports com.servicesproject.services;
    opens com.servicesproject.services to javafx.fxml;
    exports com.servicesproject.controllers;
    opens com.servicesproject.controllers to javafx.fxml;
    exports com.servicesproject.model;
    opens com.servicesproject.model to javafx.base;
}