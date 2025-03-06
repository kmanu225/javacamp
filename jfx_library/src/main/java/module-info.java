module com {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    // requires mysql.connector.java;
    requires java.sql.rowset;
    requires transitive javafx.graphics;

    opens com.library.controller to javafx.fxml;
    exports com.library.controller;

    opens com.library.model to javafx.fxml;
    exports com.library.model;
}
