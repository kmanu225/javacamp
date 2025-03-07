module com {
    requires transitive javafx.controls;
    requires transitive java.sql;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.sql.rowset;
    // requires mysql.connector.java;
    
    

    opens com.library.controller to javafx.fxml;
    exports com.library.controller;

    opens com.library.model to javafx.fxml;
    exports com.library.model;
}
