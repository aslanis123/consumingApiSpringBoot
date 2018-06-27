package com.dimos.tesco.integration.service;

import com.dimos.tesco.integration.entity.Product;
import com.dimos.tesco.integration.repository.ProductRepository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void searchProduct(String product){
        try {

            HttpClient httpClient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder("https://dev.tescolabs.com/grocery/products/?query="+product+"&offset=0&limit=10    ");


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", "{subscription key}");

//            ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(uri, Product.class);

            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);

//            ResponseEntity<Product[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }


}
