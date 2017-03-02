package ru.crawler;

/*
 документация к Jsoup - https://jsoup.org/cookbook/extracting-data/selector-syntax
ещё парсеры (ссылки внизу) - http://jericho.htmlparser.net/docs/index.html
 */


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser {
    public static void main(String[] args) {

        //объявляем переменные для документов, в которые будут скачаны  сайты robots.txt и sitemap.xml
        Document robots = null;
        Document sitemap = null;

        //пробуем скачать эти документы
        try {
            robots = Jsoup.connect("https://geekbrains.ru/robots.txt").get();
            sitemap = Jsoup.connect("https://geekbrains.ru/sitemap.xml").get();
        } catch (IOException e) {
            System.out.println("Sites unavaliable");
        }

        // sitemap парсится по тэгу <loc></loc>
        Elements sites = null;
        if (sitemap != null) {
            sites = sitemap.getElementsByTag("loc");
        }

        // выводится список всех найденных ссылок
        if (sites != null) {
            for (Element site: sites)
            {
                System.out.println(site.text());
            }
        }


    }


}
