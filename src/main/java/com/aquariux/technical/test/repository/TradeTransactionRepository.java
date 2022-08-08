package com.aquariux.technical.test.repository;

import com.aquariux.technical.test.entity.TradeTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransactionEntity, Long> {
    List<TradeTransactionEntity> findByUsername(String username);
}
