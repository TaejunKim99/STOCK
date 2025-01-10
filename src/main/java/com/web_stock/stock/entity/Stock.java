package com.web_stock.stock.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    @Column(nullable = false)
    private String stockName;
    @Column(nullable = false)
    private Integer stockPrice;
    @Column
    private String basDt;
    @Column
    private String priceLastWeek;

}
