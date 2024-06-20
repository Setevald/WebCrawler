package org.example;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<WebCrawler> bots = new ArrayList<>();

        bots.add(new WebCrawler("https://abc.go.com", 1, "movie"));
        bots.add(new WebCrawler("https://www.npr.org", 2, "asia"));
        bots.add(new WebCrawler("https://www.bbc.com", 3, "business"));
        bots.add(new WebCrawler("https://www.wikipedia.org", 4, "health"));

        for (WebCrawler w : bots) {
            try {
                w.getThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
