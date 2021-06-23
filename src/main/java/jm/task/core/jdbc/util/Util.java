package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String connectionURL = "jdbc:mysql://localhost:3306/mysql";
    private static final String userName = "root";
    private static final String password = "lfif";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Ошибка с соединением БД" + e);
        }
        return connection;
    }
}
