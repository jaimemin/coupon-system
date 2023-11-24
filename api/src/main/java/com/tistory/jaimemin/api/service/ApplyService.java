package com.tistory.jaimemin.api.service;

import com.tistory.jaimemin.api.producer.CouponCreateProducer;
import com.tistory.jaimemin.api.repository.AppliedUserRepository;
import com.tistory.jaimemin.api.repository.CouponCountRepository;
import com.tistory.jaimemin.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final static int MAX_COUPON_CNT = 100;

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    private final CouponCreateProducer couponCreateProducer;

    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository
            , CouponCountRepository couponCountRepository
            , CouponCreateProducer couponCreateProducer
            , AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    /**
     * redis incr key:value를 통해 race condition 해결 가능
     *
     * @param userId
     */
    public void apply(Long userId) {
        Long appliedCnt = appliedUserRepository.add(userId);

        if (appliedCnt != 1) {
            return;
        }

        long count = couponCountRepository.increment();

        if (count > MAX_COUPON_CNT) {
            return;
        }

        couponCreateProducer.create(userId);
    }
}
