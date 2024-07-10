package com.example.StockMarket.repositories;

import com.example.StockMarket.models.Market;
import com.example.StockMarket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Long> {
    List<Market> findAllByUser(User user);
}
