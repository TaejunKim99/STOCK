package com.web_stock.stock.service;

import com.web_stock.stock.dto.StockOrderDto;
import com.web_stock.stock.entity.Member;
import com.web_stock.stock.entity.Stock;
import com.web_stock.stock.entity.StockOrder;
import com.web_stock.stock.repository.MemberRepository;
import com.web_stock.stock.repository.StockOrderRepository;
import com.web_stock.stock.repository.StockRepository;
import com.web_stock.stock.util.MessageConst;
import com.web_stock.stock.util.Result;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.awt.print.Pageable;

import static com.web_stock.stock.util.MessageConst.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    
    private final MemberRepository memberRepository;
    private final StockOrderRepository stockOrderRepository;
    private final StockRepository stockRepository;


    @Transactional
    public Result stockPurchase(Member member, StockOrderDto stockPurchase) {
        Result result = new Result();
        Stock findStock = stockRepository.findByStockName(stockPurchase.getStockName());

        if (!findStock.getStockPrice().equals(stockPurchase.getStockPrice())) {
            result.setMsg(FAIL_STOCK_PURCHASE_OR_SALE);
            result.setCode(FAIL);
            return result;
        }

        Integer holdingMoney = member.getUserMoney();
        Integer stockPurchaseTotalPrice = calculateTotalPrice(stockPurchase.getStockPrice(), stockPurchase.getStockCount());

        if (holdingMoney < stockPurchaseTotalPrice) {
            result.setMsg(FAIL_STOCK_PURCHASE_OR_SALE);
            result.setCode(FAIL);
            return result;
        }

        member.setUserMoney(holdingMoney-stockPurchaseTotalPrice);

        StockOrder havingStock = stockOrderRepository.findByMemberAndStockName(member, stockPurchase.getStockName());

        if (null == havingStock) {
            StockOrder stockOrder = new StockOrder(stockPurchase.getStockName(), stockPurchase.getStockPrice(), stockPurchase.getStockCount(), stockPurchaseTotalPrice, member);
            stockOrder.setCurrentStockPrice(stockPurchase.getStockPrice());
            stockOrder.setCurrentTotalPrice(stockPurchaseTotalPrice);

            stockOrderRepository.save(stockOrder);
        } else {
            Integer holdingStockPrice = havingStock.getStockPrice();
            Integer holdingStockQuantity = havingStock.getStockCount();
            Integer purchaseStockQuantity = stockPurchase.getStockCount();
            Integer holdingStockTotalPrice = calculateTotalPrice(holdingStockPrice, holdingStockQuantity);
            int avgPrice = (holdingStockTotalPrice + stockPurchaseTotalPrice) / (holdingStockQuantity + (purchaseStockQuantity));

            havingStock.setStockCount(holdingStockQuantity + purchaseStockQuantity);
            havingStock.setStockPrice(avgPrice);
            havingStock.setCurrentTotalPrice(havingStock.getStockCount() * havingStock.getCurrentStockPrice());
            havingStock.setTotalPrice(calculateTotalPrice(havingStock.getStockPrice(), havingStock.getStockCount()));

            stockOrderRepository.save(havingStock);
        }

        memberRepository.save(member);

        result.setMsg(SUCCESS_STOCK_PURCHASE);
        result.setCode(SUCCESS);

        return result;
    }

    @Transactional
    public Result stockSale(Member member, StockOrderDto stockSale) {
        Result result = new Result();
        StockOrder holdingStock = stockOrderRepository.findByMemberAndStockName(member, stockSale.getStockName());

        if (null == holdingStock) {
            result.setMsg(FAIL_STOCK_PURCHASE_OR_SALE);
            result.setCode(FAIL);
            return result;
        }

        if (!holdingStock.getCurrentStockPrice().equals(stockSale.getStockPrice())) {
            result.setMsg(FAIL_STOCK_PURCHASE_OR_SALE);
            result.setCode(FAIL);
            return result;
        }

        if (holdingStock.getStockCount() < stockSale.getStockCount()) {
            result.setMsg(FAIL_STOCK_SALE_QUANTITY);
            result.setCode(FAIL);
            return result;
        }

        Integer holdingMoney = member.getUserMoney();
        Integer stockSaleProfit = calculateTotalPrice(stockSale.getStockPrice(), stockSale.getStockCount());
        member.setUserMoney(holdingMoney + stockSaleProfit);

        holdingStock.setStockCount(holdingStock.getStockCount() - stockSale.getStockCount());
        holdingStock.setCurrentTotalPrice(holdingStock.getStockCount() * holdingStock.getCurrentStockPrice());
        holdingStock.setTotalPrice(holdingStock.getStockCount() * holdingStock.getStockPrice());
        if (holdingStock.getStockCount() <= 0) {
            stockOrderRepository.delete(holdingStock);
        } else {
            stockOrderRepository.save(holdingStock);
        }
        memberRepository.save(member);

        result.setMsg(SUCCESS_STOCK_SALE);
        result.setCode("S");
        return result;
    }


    public Integer calculateTotalPrice(Integer price, Integer count) {
        return price * count;

    }
}
