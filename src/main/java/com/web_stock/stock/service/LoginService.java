package com.web_stock.stock.service;

import com.web_stock.stock.dto.LoginDto;
import com.web_stock.stock.dto.SignUpDto;
import com.web_stock.stock.entity.Member;
import com.web_stock.stock.repository.MemberRepository;
import com.web_stock.stock.util.LoginConst;
import com.web_stock.stock.util.Result;
import com.web_stock.stock.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;

import static com.web_stock.stock.util.LoginConst.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberRepository memberRepository;

    public Result signUp(SignUpDto signUpDto, HttpServletRequest request) {
        Result result = new Result();
        Member member = new Member();
        member.setUserId(signUpDto.getUserId());
        member.setUserPassword(signUpDto.getUserPassword());
        member.setUserNickName(signUpDto.getUserNickName());
        member.setRole("USER");
        member.setUserMoney(10_000_000);
        memberRepository.save(member);
        request.getSession().setAttribute("loginUser", signUpDto);

        result.setCode(SUCCESS);
        result.setMsg(SUCCESS_LOGIN);
        return result;
    }


    public Result login(LoginDto loginDto, HttpServletRequest request) {
        Result result = new Result();
        Member findMember = memberRepository.findByUserId(loginDto.getUserId());

        if (findMember == null) {
            log.error("로그인 정보를 찾을 수 없습니다.");
            result.setMsg(FAIL_LOGIN);
            result.setCode(FAIL);
            return result;
        }

        if (!verifyPassword(loginDto, findMember)) {
            log.error("로그인 비밀번호와 저장된 로그인 유저의 비밀번호가 같지 않습니다.");
            result.setMsg(FAIL_LOGIN);
            result.setCode(FAIL);
            return result;
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, findMember);

        result.setCode(SUCCESS);
        result.setMsg(SUCCESS_LOGIN);

        return result;
    }

    /**
     *  암호화 예정 SHA-256 + SALT
     * @param loginDto
     * @param member
     * @return
     */
    private boolean verifyPassword(LoginDto loginDto,Member member) {
        String loginPassword = loginDto.getUserPassword();
        String findMemberPassword = member.getUserPassword();

        if (loginPassword.equals(findMemberPassword)) {
            return true;
        }
        return false;
    }

}
