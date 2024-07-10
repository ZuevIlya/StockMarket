package com.example.StockMarket.repositories;

import com.example.StockMarket.models.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Long> {
}
