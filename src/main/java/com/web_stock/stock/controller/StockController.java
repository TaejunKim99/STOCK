package com.web_stock.stock.controller;
import com.web_stock.stock.entity.Member;
import com.web_stock.stock.util.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import com.web_stock.stock.entity.Stock;
import com.web_stock.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;


import java.util.List;

@Slf4j
@Controller
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stocklist")
    public String stocklist(Model model,@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member) {

        model.addAttribute("member", member);
        addPaginationAttributes(model, 0, 10);
        return "stock/stockList";
    }


    @GetMapping("/stocklist/{pageIndex:\\d+}")
    public String stocklistWithPage(Model model,@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member
            , @PathVariable("pageIndex") int pageIndex) {
        model.addAttribute("member", member);
        addPaginationAttributes(model, pageIndex, 10);
        return "stock/stockList";
    }

    @GetMapping("/stock/result")
    public String resultPage() {
        return "/stock/result"; // 결과 페이지 템플릿 이름
    }

    private void addPaginationAttributes(Model model, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Long allStockCount = stockRepository.countBy();
        Page<Stock> stockList = stockRepository.findAll(pageable);

        model.addAttribute("stockList", stockList);
        model.addAttribute("allStockCount", allStockCount);
        model.addAttribute("currentPage", pageIndex);
    }
}
