package com.web_stock.stock.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    @Size(min = 4, max = 16, message = "아이디는 4자 부터 최대 16자 까지 입력 해야 합니다.")
    @NotNull
    private String userId;
    @Size(min = 4, max = 16, message = "비밀번호는 최소 4자 부터 최대 16자 입력 해야 합니다.")
    @NotNull
    private String userPassword;
    @Size(min = 1, max = 8, message = "닉네임은 최대 8자 까지 입력 가능 합니다.")
    @NotNull
    private String userNickName;

    @Override
    public String toString() {
        return "SignUpDto{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickName='" + userNickName + '\'' +
                '}';
    }
}
