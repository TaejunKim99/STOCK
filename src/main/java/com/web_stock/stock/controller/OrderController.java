package com.web_stock.stock.controller;

import com.web_stock.stock.dto.StockOrderDto;
import com.web_stock.stock.entity.Member;
import com.web_stock.stock.entity.Stock;
import com.web_stock.stock.repository.MemberRepository;
import com.web_stock.stock.service.OrderService;
import com.web_stock.stock.util.MessageConst;
import com.web_stock.stock.util.Print;
import com.web_stock.stock.util.Result;
import com.web_stock.stock.util.SessionConst;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.web_stock.stock.util.MessageConst.SUCCESS;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberRepository memberRepository;

    @GetMapping("/stock/purchase")
    public String purchasePageInit(@ModelAttribute StockOrderDto stockOrderDto, Model model) {
        model.addAttribute("stock", stockOrderDto);
        return "stock/order";
    }

    @PostMapping("/stock/purchase")
    public String purchase(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member, @ModelAttribute StockOrderDto purchaseStock, RedirectAttributes redirectAttributes,
                           HttpServletResponse response) {
        Result result = orderService.stockPurchase(member, purchaseStock);
        if (SUCCESS.equals(result.getCode())) {
            Print.printAlertMessage(response, result.getMsg());
            return "redirect:/stocklist";
        } else {
            Print.printAlertMessage(response, result.getMsg());
            return "redirect:/stocklist";
        }
    }

    @GetMapping("/stock/sale")
    public String salePageInit(@ModelAttribute StockOrderDto stockOrderDto, Model model) {
        model.addAttribute("stock", stockOrderDto);
        return "stock/sale";
    }

    @PostMapping("/stock/sale")
    public String sale(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) Member member, @ModelAttribute StockOrderDto stockOrderDto,
                       HttpServletResponse response) {
        Result result = orderService.stockSale(member, stockOrderDto);
        if (SUCCESS.equals(result.getCode())) {
            Print.printAlertMessage(response, result.getMsg());
            return "redirect:/stock/result";
        } else {
            Print.printAlertMessage(response, result.getMsg());
            return "redirect:/stock/result";
        }
    }
}
