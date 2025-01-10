package com.web_stock.stock.repository;

import com.web_stock.stock.entity.Member;
import com.web_stock.stock.entity.StockOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserId(String userId);
    Boolean existsByUserNickName(String memberName);
}
