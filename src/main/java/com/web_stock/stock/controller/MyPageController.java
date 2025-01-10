package com.web_stock.stock.controller;

import com.web_stock.stock.entity.Member;
import com.web_stock.stock.entity.StockOrder;
import com.web_stock.stock.repository.StockOrderRepository;
import com.web_stock.stock.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageController {

    private final StockOrderRepository stockOrderRepository;

    @GetMapping("/")
    public String index(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member, Model model) {
        model.addAttribute("member", member);
        return "lesser/index";
    }

    @GetMapping("/mypage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member, Model model) {
        List<StockOrder> havingStockList = stockOrderRepository.findByMember(member);
        int totalAsset = havingStockList.stream()
                .mapToInt(StockOrder::getTotalPrice)
                .sum();
        int currentTotalAsset = havingStockList.stream()
                .mapToInt(StockOrder::getCurrentTotalPrice)
                .sum();
        model.addAttribute("stockList", havingStockList);
        model.addAttribute("totalAsset", totalAsset);
        model.addAttribute("currentTotalAsset", currentTotalAsset);
        model.addAttribute("member", member);
        return "mypage/myinfo";
    }

    @GetMapping("/about")
    public String about(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member, Model model) {

        return "lesser/about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "lesser/contact";
    }
}
