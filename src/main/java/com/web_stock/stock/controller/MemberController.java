package com.web_stock.stock.controller;

import com.web_stock.stock.dto.LoginDto;
import com.web_stock.stock.dto.SignUpDto;
import com.web_stock.stock.repository.StockOrderRepository;
import com.web_stock.stock.service.LoginService;
import com.web_stock.stock.util.Print;
import com.web_stock.stock.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.web_stock.stock.repository.MemberRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Objects;

import static com.web_stock.stock.util.LoginConst.*;


@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;
    private final StockOrderRepository stockOrderRepository;
    private final LoginService loginService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Validated SignUpDto signUpDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            Print.printAlertMessage(response, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return "/login/login-page";
        }
        Result result = loginService.signUp(signUpDto, request);
        if(FAIL.equals(result.getCode())) {
            Print.printAlertMessage(response, result.getMsg());
            return "/login/login-page";
        }
        return "stock/stockList";
    }

    @GetMapping("/login")
    public String loginInit(Model model) {
        return "login/login-page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated LoginDto loginDto,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            log.info(bindingResult.getAllErrors().toString());
            Print.printAlertMessage(response, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            return "/login/login-page";
        }
        Result result = loginService.login(loginDto, request);
        if (FAIL.equals(result.getCode())) {
            Print.printAlertMessage(response, result.getMsg());
            return "/login/login-page";
        }
        return "redirect:/";
    }


}
