package com.tistory.jaimemin.api.repository;

import com.tistory.jaimemin.api.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
