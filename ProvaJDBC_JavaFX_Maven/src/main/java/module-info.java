module com.provajdbc_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.provajdbc_javafx to javafx.fxml;
    exports com.provajdbc_javafx;
}