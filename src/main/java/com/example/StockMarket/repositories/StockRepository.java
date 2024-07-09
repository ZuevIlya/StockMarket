package com.example.StockMarket.repositories;

import com.example.StockMarket.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByName(String name);
}
