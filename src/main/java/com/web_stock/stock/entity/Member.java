package com.web_stock.stock.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sequenceId;
    @Column(unique=true)
    @Size(min = 4, max = 16, message = "아이디는 4자 부터 최대 16자 까지 입력 해야 합니다.")
    private String userId;
    @Column
    @Size(min = 4, max = 16, message = "비밀번호는 최소 4자 부터 최대 16자 입력 해야 합니다.")
    private String userPassword;
    @Column
    private Integer userMoney;
    @Column
    private String userNickName;
    @Column
    private String role;

    @OneToMany(mappedBy = "member")
    private List<StockOrder> stockOrderList = new ArrayList<>();

    public Member(String userId, String userPassword, Integer userMoney, String userNickName) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userMoney = userMoney;
        this.userNickName = userNickName;
    }

    @Override
    public String toString() {
        return "Member{" +
                "sequenceId=" + sequenceId +
                ", userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userMoney=" + userMoney +
                ", userNickName='" + userNickName + '\'' +
                ", role='" + role + '\'' +
                ", stockOrderList=" + stockOrderList +
                '}';
    }
}
