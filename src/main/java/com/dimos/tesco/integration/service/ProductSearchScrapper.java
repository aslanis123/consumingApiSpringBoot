package com.dimos.tesco.integration.service;

import com.dimos.tesco.integration.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ProductSearchScrapper {

    public static void getProducts() {

        try {
            Document doc  = Jsoup.connect("https://nakup.itesco.cz/groceries/en-GB/search?query=" + URLEncoder.encode("pumpkin seeds", "UTF-8")).get();
            Elements results = doc.getElementsByClass("product-list--list-item");
            for (Element result: results){
                String productPage = result.getElementsByTag("a").attr("href");
                String productName = result.getElementsByTag("img").attr("alt");
                String price = result.getElementsByClass("value").text();
                System.out.println("Nme: " + productName + " Descr: " + productPage + " Price: " + price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProductInce

    public static void main(String[] args){
        getProducts();
    }
}
