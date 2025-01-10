package com.web_stock.stock.repository;

import com.web_stock.stock.entity.Member;
import com.web_stock.stock.entity.StockOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {
    List<StockOrder> findByMember(Member member);
    StockOrder findByMemberAndStockName(Member member, String stockName);
}
