package com.web_stock.stock.dto;

import com.web_stock.stock.util.LoginConst;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.web_stock.stock.util.LoginConst.*;

@Getter
@Setter
public class LoginDto {
    @NotNull
    @Size(min = 4, max = 16, message = FAIL_LOGIN)
    private String userId;
    @NotNull
    @Size(min = 4, max = 16, message = FAIL_LOGIN)
    private String userPassword;

    @Override
    public String toString() {
        return "LoginDto{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
