package ru.crawler;

import java.sql.*;

/**
 * Created by Alexander Sychev on 02.03.2017.
 */


class SQLHandler {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "root";

    // JDBC variables for opening and managing connection
    private static Connection conn;
    private static Statement stmt;

    static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            System.out.println("done!");
        } catch (Exception e) {
            System.out.println("Невозможно подключиться к БД");
        }
    }
}
