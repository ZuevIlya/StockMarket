package com.example.StockMarket.controllers;

import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.Stock;
import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.MarketRepository;
import com.example.StockMarket.repositories.StockRepository;
import com.example.StockMarket.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MarketController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private MarketService marketService;

    @GetMapping("/market/buy/{name}")
    public String buyStocksGet(@PathVariable(value = "name") String name, Model model,
                               @AuthenticationPrincipal User user) {
        Stock stock = stockRepository.findByName(name);
        model.addAttribute("user", user);
        model.addAttribute("stock", stock);
        return "buy_stock";
    }

    @PostMapping("/market/buy/{name}")
    public String buyStockPost(@PathVariable(value = "name") String name,
                               @RequestParam int count,
                               @AuthenticationPrincipal User user,
                               Model model) {
        Map<String, String> errorsMap = new HashMap<>();
        Stock stock = stockRepository.findByName(name);
        double full_price = stock.getPrice() * count;
        if (full_price > user.getScore()) {
            errorsMap.put("score_error", "Недостаточно средств на балансе");
        }
        if (!errorsMap.isEmpty()) {
            model.addAttribute("score_error", errorsMap.get("score_error"));
            model.addAttribute("user", user);
            model.addAttribute("stock", stock);
            return "buy_stock";
        } else {
            marketService.buyStock(name, count, user);
            return "redirect:/stock/pull_stocks";
        }
    }

    @GetMapping("/market/sell/{name}")
    public String sellStockGet(@PathVariable(value = "name") String name,
                               @AuthenticationPrincipal User user,
                               Model model) {
       Stock stock = stockRepository.findByName(name);
       List<Market> marketList = marketRepository.findAllByUser(user);
       Market market = marketService.getMarketbyStockName(marketList, name);
        if (market == null) {
            return "redirect:/home/";
        }
       int count = market.getCount();
       double price = stock.getPrice();
       model.addAttribute("name", name);
       model.addAttribute("count", count);
       model.addAttribute("price", price);
       return "sell_stock";
    }

    @PostMapping("/market/sell/{name}")
    public String sellStockPost(@PathVariable(value = "name") String name,
                               @RequestParam int count,
                               @AuthenticationPrincipal User user,
                               Model model) {
        Map<String, String> errorsMap = new HashMap<>();
        List<Market> marketList = marketRepository.findAllByUser(user);
        Market market = marketService.getMarketbyStockName(marketList, name);
        if (count > market.getCount()) {
            System.out.println("Недостаточно акций для продажи");
            errorsMap.put("count_error", "У вас меньше акций");
            double price = market.getStock().getPrice();
            model.addAttribute("name", name);
            model.addAttribute("count", market.getCount());
            model.addAttribute("price", price);
            model.addAttribute("count_error", errorsMap.get("count_error"));
            return "sell_stock";
        }
        System.out.println("Можно успешно продать акции");
        marketService.sellStock(market, user, count);
        return "redirect:/home/";
    }
}
