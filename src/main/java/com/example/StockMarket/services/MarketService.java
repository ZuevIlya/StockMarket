package com.example.StockMarket.services;

import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.Stock;
import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.MarketRepository;
import com.example.StockMarket.repositories.StockRepository;
import com.example.StockMarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private UserRepository userRepository;

    public void buyStock(String name, int count, User user) {
        Stock stock = stockRepository.findByName(name);
        double full_price = stock.getPrice() * count;
        user.setScore(user.getScore() - full_price);
        List<Market> marketList = marketRepository.findAllByUser(user);
        boolean findStockInMarket = false;
        for (Market temp: marketList) {
            if (temp.getStock().getName().equals(name)) {
                temp.setCount(temp.getCount() + count);
                temp.setPurchase_price(stock.getPrice());
                findStockInMarket = true;
                marketRepository.save(temp);
                break;
            }
        }
        if (!findStockInMarket) {
            Market market = new Market();
            market.setUser(user);
            market.setCount(count);
            market.setStock(stock);
            market.setPurchase_price(stock.getPrice());
            marketRepository.save(market);
        }
        userRepository.save(user);
    }
}
