package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/users_data";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Pussya_135";
    private static Connection connection = null;

    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение с БД установлено");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка подключения к БД");
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение с БД закрыто");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

