package com.example.StockMarket.controllers;


import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.Stock;
import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.MarketRepository;
import com.example.StockMarket.repositories.StockRepository;
import com.example.StockMarket.repositories.UserRepository;
import com.example.StockMarket.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/stock/pull_stocks")
    public String stocks(Model model) {
        List<Stock> stocks = stockRepository.findAll();
        System.out.println(stocks.size());
        for (Stock element: stocks) {
            System.out.println(element.getId() + " " + element.getName() + " " + element.getPrice());
        }
        model.addAttribute("stocks", stocks);
        return "stocksList";
    }

    @GetMapping("/stock/pull_stocks/add_stocks")
    public String addNewStocks() throws IOException {
        stockService.downloadStocks();
        return "redirect:/stock/pull_stocks";
    }

    @GetMapping("/stock/pull_stocks/{name}")
    public String buyStocksGet(@PathVariable(value = "name") String name, Model model) {
        Stock stock = stockRepository.findByName(name);
        model.addAttribute("stock", stock);
        return "buy_stock";
    }

    @PostMapping("/stock/pull_stocks/{name}")
    public String buyStockPost(@PathVariable(value = "name") String name,
                               @RequestParam int count,
                               @AuthenticationPrincipal User user) {
        Stock stock = stockRepository.findByName(name);
        double full_price = stock.getPrice() * count;
        user.setScore(user.getScore() - full_price);
        Market market = new Market();
        market.setUser(user);
        market.setCount(count);
        market.setStock(stock);
        marketRepository.save(market);
        userRepository.save(user);
        return "redirect:/stock/pull_stocks";
    }
}
