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
        // Если акция уже была куплена
        for (Market temp: marketList) {
            if (temp.getStock().getName().equals(name)) {
                double middle_price = (temp.getPurchase_price() * temp.getCount() + temp.getStock().getPrice() * count) / (temp.getCount() + count);
                temp.setCount(temp.getCount() + count);
                temp.setPurchase_price(middle_price);
                findStockInMarket = true;
                marketRepository.save(temp);
                break;
            }
        }
        // Если акции ещё не было
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

    public Market getMarketbyStockName(List<Market> marketList, String name) {
        for (Market market: marketList) {
            if (market.getStock().getName().equals(name)) {
                System.out.println(market.getUser().getUsername() + " " + market.getStock().getName() + " " + market.getCount());
                return market;
            }
        }
        return null;
    }

    public void sellStock(Market market, User user, int count) {
        double full_price = market.getStock().getPrice() * count;
        user.setScore(user.getScore() + full_price);
        userRepository.save(user);
        // Если продаёт все акции - удаляем запись
        if (market.getCount() == count) {
            marketRepository.delete(market);
        } else {
            market.setCount(market.getCount() - count);
            marketRepository.save(market);
        }

    }
}
