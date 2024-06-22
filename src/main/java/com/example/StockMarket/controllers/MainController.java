package com.example.StockMarket.controllers;

import com.example.StockMarket.models.Person;
import com.example.StockMarket.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class MainController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/person")
    public String addPerson() {
        Person person = new Person();
        person.setName("Ilya");
        personRepository.save(person);
        System.out.println("add new person");
        return "home";
    }
}
