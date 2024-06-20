package org.example;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class WebCrawler implements Runnable {
    private static final int MAX_DEPTH = 4;
    private Thread thread;
    private String first_Link;
    private ArrayList<String> visitedLinks = new ArrayList<>();
    private int ID;
    private String keyword;

    public WebCrawler(String link, int num, String keyword) {
        System.out.println("WebCrawler created");
        first_Link = link;
        ID = num;
        this.keyword = keyword;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        crawl(1, first_Link);
    }

    private void crawl(int level, String url) {
        if (level <= MAX_DEPTH) {
            Document doc = request(url);

            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if (!visitedLinks.contains(next_link) && urlFilter(next_link)) {
                        crawl(level + 1, next_link);
                    }
                }
            }
        }
    }

    private Document request(String url) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if (con.response().statusCode() == 200) {
                System.out.println("\n**Bot ID:" + ID + " Received Webpage at " + url);

                String title = doc.title();
                System.out.println("Title: " + title);
                visitedLinks.add(url);

                return doc;
            } else {
                System.out.println("\n**Bot ID:" + ID + " Failed to receive Webpage at " + url + " - Status Code: " + con.response().statusCode());
            }
        } catch (IOException e) {
            System.out.println("\n**Bot ID:" + ID + " Error while requesting " + url + ": " + e.getMessage());
        }
        return null;
    }

    private boolean urlFilter(String url) {
        return url.contains(keyword);
    }

    public Thread getThread() {
        return thread;
    }
}
