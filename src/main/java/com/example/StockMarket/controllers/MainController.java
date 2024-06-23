package com.example.StockMarket.controllers;

import com.example.StockMarket.models.Person;
import com.example.StockMarket.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(path = "/home")
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

    @GetMapping("/getpersons")
    public String getPersons() {
        List<Person> list = personRepository.findAll();
        list.forEach(x -> System.out.println(x.getName()));
        return "home";
    }
}
