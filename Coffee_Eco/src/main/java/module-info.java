module huy.ntu.coffee_eco {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;


    opens huy.ntu.coffee_eco to javafx.fxml;
    exports huy.ntu.coffee_eco;
}