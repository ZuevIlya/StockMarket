package com.example.StockMarket.repositories;

import com.example.StockMarket.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
