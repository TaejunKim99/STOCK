package com.web_stock.stock.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class StockOrderDto {
    private String stockName;
    private Integer stockPrice;
    private Integer stockCount;

    public StockOrderDto(String stockName, Integer stockPrice, Integer stockCount) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockCount = stockCount;
    }
}
