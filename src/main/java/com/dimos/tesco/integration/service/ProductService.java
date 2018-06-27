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
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.plugin.javascript.JSObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void addProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }


}
