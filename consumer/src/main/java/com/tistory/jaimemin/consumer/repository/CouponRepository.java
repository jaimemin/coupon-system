package com.tistory.jaimemin.consumer.repository;

import com.tistory.jaimemin.consumer.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
