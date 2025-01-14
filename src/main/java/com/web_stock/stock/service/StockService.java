package com.web_stock.stock.service;

import com.web_stock.stock.entity.Stock;
import com.web_stock.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {
    private final StockRepository stockRepository;

    public List<Stock> stockSearch(String stockName) {
        return stockRepository.findByStockNameContaining(stockName);
    }
}
