package com.example.StockMarket.controllers;

import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.Stock;
import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.MarketRepository;
import com.example.StockMarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(path = "/home")
public class MainController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    MarketRepository marketRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) {
        List<Market> marketList = marketRepository.findAllByUser(user);

        class MarketPlus {
            public Market market;
            public String difference;

        }
        List<MarketPlus> marketPlusList = new ArrayList<>();
        for (Market market: marketList) {
            MarketPlus marketPlus = new MarketPlus();
            marketPlus.market = market;
            double value = (market.getPurchase_price()/market.getStock().getPrice() * 100 - 100);
            double difference = Math.floor(value * 100) / 100;
            if (difference >= 0) {
                marketPlus.difference = "+" + difference + "%";
            } else {
                marketPlus.difference = difference + "%";
            }
            marketPlusList.add(marketPlus);
        }
        model.addAttribute("marketPlusList", marketPlusList);
        model.addAttribute("user", user);
        return "home";
    }
}
