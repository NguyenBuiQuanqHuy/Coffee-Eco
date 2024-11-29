package huy.ntu.coffee_eco.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static public Connection DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String strConn = "jdbc:mysql://localhost:3306/coffee_eco";
        Connection conn = DriverManager.getConnection(strConn, "root", "");
        return conn;
    }
    static public void CloseConnect(Connection connection) throws SQLException {
        connection.close();
    }
}
