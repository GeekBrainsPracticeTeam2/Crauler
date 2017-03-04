package ru.crawler;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Date;

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
    static LinkedList<String> getPagesNotScanned() {
        LinkedList<String> pages = new LinkedList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT Url FROM Pages WHERE LastScanDate IS NULL");
            while (rs.next()) {
                pages.add(rs.getString(1));
            }
            return pages;
        } catch (SQLException e) {
            System.out.println("Ошибка обработки запроса к таблице Pages");
        }
        return pages;
    }

    //метод, который просматривает таблицу Sites и ищет те сайты, у которых совсем нет сопоставлений в таблице Pages
    static LinkedList<String> getSitesWithNoPages() {
        LinkedList<String> sites = new LinkedList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT Sites.name FROM Sites LEFT JOIN (SELECT DISTINCT SiteID FROM Pages) as Pages ON Sites.id = Pages.SiteID WHERE Pages.SiteID IS NULL");
            while (rs.next()) {
                sites.add(rs.getString(1));
            }
            return sites;
        } catch (SQLException e) {
            System.out.println("Ошибка обработки запроса к таблице Sites");
        }
        return sites;
    }

    //метод, который добавляет в таблицу Pages строку с URL равным http://<имя_сайта>/robots.txt и LastScanDate равным null
    static void setRobotsPage(String siteName) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        System.out.println(dateFormat.format(date));
        try {
            ResultSet rs = stmt.executeQuery("SELECT id FROM Sites WHERE name = '" + siteName + "';");
            if (rs.next()) {
                int siteID = rs.getInt(1);
                System.out.println(siteID);
                int a = stmt.executeUpdate("INSERT Pages SET SiteID = '" + siteID + "', FoundDateTime = '" + dateFormat.format(date) + "', Url = 'http://" + siteName + "/robots.txt'");
            } else System.out.println("Имя сайта не найдено в таблице сайтов");
        } catch (SQLException e) {
            System.out.println("Ошибка добавления записи в таблицу Pages");
        }
    }
}
