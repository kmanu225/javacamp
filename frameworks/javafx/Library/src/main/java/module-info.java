module com {

    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    requires transitive java.sql;
    requires java.sql.rowset;

    opens jfx.app to javafx.fxml;
    opens jfx.app.controller to javafx.fxml;
    opens jfx.app.model to javafx.fxml;

    exports jfx.app;
    exports jfx.app.controller;
    exports jfx.app.model;
}
