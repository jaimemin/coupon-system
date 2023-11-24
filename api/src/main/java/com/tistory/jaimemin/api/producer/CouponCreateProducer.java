package com.tistory.jaimemin.api.producer;

import com.tistory.jaimemin.api.repository.CouponCountRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponCreateProducer {

    private static final String TOPIC = "coupon_create";

    private final KafkaTemplate<String, Long> kafkaTemplate;

    public CouponCreateProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void create(Long userId) {
        kafkaTemplate.send(TOPIC, userId);
    }
}
