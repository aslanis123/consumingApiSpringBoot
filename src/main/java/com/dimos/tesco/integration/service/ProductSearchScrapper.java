package com.dimos.tesco.integration.service;

import com.dimos.tesco.integration.entity.Product;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchScrapper {

    public static void getProducts() {

        List<Product> products = new ArrayList<>();

        try {
            Document doc  = Jsoup.connect("https://nakup.itesco.cz/groceries/en-GB/search?query=" + URLEncoder.encode("pumpkin seeds", "UTF-8")).get();
            Elements results = doc.getElementsByClass("product-list--list-item");
            for (Element result: results){
                String productPage = result.getElementsByTag("a").attr("href");
                String productIndetifier = result.getElementsByClass("tile-content").attr("id");
                String productName = result.getElementsByTag("img").attr("alt");
                String productImgUrl = result.getElementsByClass("product-image").attr("src");
                String price = result.getElementsByClass("price-control-wrapper").text();
                products.add(new Product(productIndetifier, productName, productPage, price, productImgUrl));
                System.out.println("Nme: " + productName + " Descr: " + productPage + " Price: " + price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getProductInformation(String id){

        try {
            Document doc = Jsoup.connect("https://nakup.itesco.cz/groceries/cs-CZ/products/" + id).get();
            Elements inforation = doc.getElementsByClass("groupItem");

            for (Element info : inforation){
                Element sectionTitle = info.getElementsByTag("h3").first();
                if (sectionTitle.text().equals("Složení")){
                    String productIngredients = info.text();
                    translateText(productIngredients);
                    System.out.println(productIngredients);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void translateText(String sourceText) {

        final String key = "trnsl.1.1.20180628T114334Z.7141d84d9a6108c3.acc724f9e7ac8f70cbc8d2cff19fcbd40fcb7441";
        final String lang = "cs-en";


        try {

            HttpClient httpClient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder("https://translate.yandex.net/api/v1.5/tr.json/translate?&key="+key+"&text="+URLEncoder.encode(sourceText, "UTF-8")+"&lang="+lang);

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(EntityUtils.toString(entity));
            JSONArray productInofrmationTranslated = (JSONArray) jsonObject.get("text");


            System.out.println(productInofrmationTranslated.get(0));


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args){

        getProducts();

        getProductInformation("2001000431014");
    }
}
