package com.tistory.jaimemin.consumer.repository;

import com.tistory.jaimemin.consumer.domain.FailedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {
}
