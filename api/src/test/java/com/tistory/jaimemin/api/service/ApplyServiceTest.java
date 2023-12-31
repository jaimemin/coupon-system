package com.tistory.jaimemin.api.service;

import com.tistory.jaimemin.api.repository.CouponRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void 한번만응모() {
        applyService.apply(1L);
        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    /**
     * race condition에 의해 의도한대로 동작 안하는 테스트
     *
     * @throws InterruptedException
     */
    @Test
    public void 여러명응모() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = i;

            executorService.submit(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        long count = couponRepository.count();

        // kafka에서 처리되는 속도를 감안해서 넉넉하게
        Thread.sleep(1000 * 10);

        assertThat(count).isEqualTo(100L);
    }

    @Test
    public void 한명당_한개의쿠폰만_발급() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            long userId = 1L;

            executorService.submit(() -> {
                try {
                    applyService.apply(userId);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        long count = couponRepository.count();

        // kafka에서 처리되는 속도를 감안해서 넉넉하게
        Thread.sleep(1000 * 10);

        assertThat(count).isEqualTo(1L);
    }
}