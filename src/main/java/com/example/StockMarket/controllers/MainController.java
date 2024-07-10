package com.example.StockMarket.controllers;

import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.MarketRepository;
import com.example.StockMarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("marketList", marketList);
        return "home";
    }
}
