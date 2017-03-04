package ru.crawler;

import java.sql.*;
import java.util.LinkedList;

/**
 * Created by Alexander Sychev on 02.03.2017.
 */


class SQLHandler {

    // JDBC URL, username and password of MySQL server
//    private static final String url = "jdbc:mysql://194.87.238.4:3306/humaninweb";
//    private static final String user = "sandyqwe";
//    private static final String password = "50mx7o5Rq?nRJ7";

    // тестовая база для отладки
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

    // метод, который загружает из базы все страницы, у которых поле LastScanDate = null
    static LinkedList<String> getPagesNotScanned (){
        LinkedList<String> pages = new LinkedList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT Url FROM Pages WHERE LastScanDate IS NULL");
            while (rs.next()){
                pages.add(rs.getString(1));
            }
            return pages;
        } catch (SQLException e) {
            System.out.println("Ошибка обработки запроса к таблице Pages");
        }
        return pages;
    }
}
