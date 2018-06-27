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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchProductService {


    public List<Product> getSearchProductResults(String product) {

        List<Product> productsResults = new ArrayList<Product>();

        try {

            HttpClient httpClient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder("https://dev.tescolabs.com/grocery/products/?query=" + product + "&offset=0&limit=10");


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", "fd6086e00ee94d1197f29fe540fd6d94");


            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            String entityString = EntityUtils.toString(entity);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(entityString);

            JSONObject uk_obj = (JSONObject) jsonObject.get("uk");
            JSONObject ghs = (JSONObject) uk_obj.get("ghs");
            JSONObject products = (JSONObject) ghs.get("products");
            JSONArray results = (JSONArray) products.get("results");


            for (Object result : results){
                productsResults.add(new Product((String)((JSONObject)result).get("name"), (String)((JSONArray)((JSONObject) result).get("description")).get(0)));
            }

            for (Product pr: productsResults){
                System.out.println(pr.getProductName() + " " + pr.getProductDescription());
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return productsResults;


    }
}
