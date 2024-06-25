package com.example.StockMarket.controllers;

import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.UserRepository;
import com.example.StockMarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login() {
        System.out.println("пришли в логин");
        List<User> list = userRepository.findAll();
        list.forEach(x -> System.out.println(x.getUsername() + " " + x.getPassword()));
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String createUser(User user) {
        userService.createUser(user);
        return "redirect:/login";
    }
}
