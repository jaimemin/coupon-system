package com.tistory.jaimemin.consumer.consumer;

import com.tistory.jaimemin.consumer.domain.Coupon;
import com.tistory.jaimemin.consumer.domain.FailedEvent;
import com.tistory.jaimemin.consumer.repository.CouponRepository;
import com.tistory.jaimemin.consumer.repository.FailedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;

    private final FailedEventRepository failedEventRepository;

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        try {
            couponRepository.save(new Coupon(userId));
        } catch (Exception e) {
            log.error(String.format("failed to craete coupon: %d", userId));

            failedEventRepository.save(new FailedEvent(userId));
        }
    }
}
