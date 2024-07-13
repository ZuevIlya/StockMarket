package com.example.StockMarket.controllers;


import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.Stock;
import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.MarketRepository;
import com.example.StockMarket.repositories.StockRepository;
import com.example.StockMarket.repositories.UserRepository;
import com.example.StockMarket.services.MarketService;
import com.example.StockMarket.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Controller
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarketService marketService;



    @GetMapping("/stock/pull_stocks")
    public String stocks(Model model) {
        List<Stock> stocks = stockRepository.findAll();
        /*
        for (Stock element: stocks) {
            System.out.println(element.getId() + " " + element.getName() + " " + element.getPrice());
        }

         */
        model.addAttribute("stocks", stocks);
        return "stocksList";
    }

    @GetMapping("/stock/pull_stocks/add_stocks")
    public String addNewStocks() throws IOException {
        stockService.downloadStocks();
        return "redirect:/stock/pull_stocks";
    }
}
