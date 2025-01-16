package com.web_stock.stock.interceptor;

import com.web_stock.stock.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[Interceptor] preHandle");
        StringBuffer requestURL = request.getRequestURL();
        log.info("인증 체크 인터셉터 {} ", requestURL);
        String remoteAddr = request.getRemoteAddr();
        log.info("LOGIN IP {}" , remoteAddr);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login");
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 요청 처리 후 작업 가능
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 완료 후 정리 작업 가능
    }
}