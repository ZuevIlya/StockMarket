package com.example.StockMarket.services;

import com.example.StockMarket.models.Stock;
import com.example.StockMarket.repositories.StockRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void downloadStocks() throws IOException {
        String sURL = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities.json?iss.meta=off&iss.only=marketdata&marketdata.columns=SECID,LAST"; //just a string

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader((request.getInputStream())));
        String output;
        StringBuilder builder = new StringBuilder();
        while ((output = br.readLine()) != null) {
            builder.append(output);
        }
        builder = new StringBuilder(builder.substring(56, builder.length() - 2));
        System.out.println(builder);

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> map = gson.fromJson(builder.toString(), type);
        System.out.println(map);
        for (String key: map.keySet()) {
            Stock stock;
            if (key == null) continue;
            // Если акции нет в БД
            if (stockRepository.findByName(key) == null) {
                stock = new Stock();
                stock.setName(key);
            } else { // Если акция уже есть в бд
                stock = stockRepository.findByName(key);
            }
            if (map.get(key) == null) {
                stock.setPrice(0);
                stock.setActive(false);
            } else {
                stock.setPrice(Double.parseDouble(map.get(key)));
                stock.setActive(true);
            }
            stockRepository.save(stock);

        }
        System.out.println("Все акции:" + map);
    }
}
