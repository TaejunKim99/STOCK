package com.web_stock.stock.repository;

import com.web_stock.stock.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Page<Stock> findAll(Pageable pageable);

    Long countBy();
    Stock findByStockName(String stockName);
    List<Stock> findByStockNameContaining(String stockName);
}
