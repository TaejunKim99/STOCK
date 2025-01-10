package com.web_stock.stock.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class StockOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false)
    private String stockName;
    /**
     *  구매한 주식의 평균 값
     */
    @Column(nullable = false)
    private Integer stockPrice;
    /**
     *  현재 주식 가격
     */
    @Column
    private Integer currentStockPrice;

    @Column(nullable = false)
    private Integer stockCount;

    @Column(nullable = false)
    private Integer totalPrice;
    /**
     *  현재 주식의 총 가격
     */
    @Column
    private Integer currentTotalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sequence_id")
    private Member member;

    public StockOrder(String stockName, Integer stockPrice, Integer stockCount, Integer totalPrice, Member member) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockCount = stockCount;
        this.totalPrice = totalPrice;
        this.member = member;
    }

    public StockOrder() {

    }
}
