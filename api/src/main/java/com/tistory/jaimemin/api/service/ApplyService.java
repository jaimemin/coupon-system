package com.tistory.jaimemin.api.service;

import com.tistory.jaimemin.api.domain.Coupon;
import com.tistory.jaimemin.api.repository.CouponCountRepository;
import com.tistory.jaimemin.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final static int MAX_COUPON_CNT = 100;

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    public ApplyService(CouponRepository couponRepository
            , CouponCountRepository couponCountRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
    }

    /**
     * redis incr key:value를 통해 race condition 해결 가능
     *
     * @param userId
     */
    public void apply(Long userId) {
        long count = couponCountRepository.increment();

        if (count > MAX_COUPON_CNT) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
