package com.example.StockMarket.controllers;

import com.example.StockMarket.models.User;
import com.example.StockMarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FrontEndController {

    static class UserPlus {
        public Long id;
        public String username;
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserPlus> getUserList() {
        List<User> userList = userRepository.findAll();
        List<UserPlus> userPlusList = new ArrayList<>();
        for (User user: userList)  {
            UserPlus userPlus = new UserPlus();
            userPlus.id = user.getId();
            userPlus.username = user.getUsername();
            userPlusList.add(userPlus);
        }
        System.out.println(userPlusList);
        return userPlusList;
    }
}
