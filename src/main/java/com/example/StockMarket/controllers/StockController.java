package com.example.StockMarket.controllers;


import com.example.StockMarket.models.Stock;
import com.example.StockMarket.repositories.StockRepository;
import com.example.StockMarket.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String buyStocks(@PathVariable(value = "name") String name, Model model) {
        Stock stock = stockRepository.findByName(name);
        model.addAttribute("stock", stock);
        return "buy_stock";
    }
}
