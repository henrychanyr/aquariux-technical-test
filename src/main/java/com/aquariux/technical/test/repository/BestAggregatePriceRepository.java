package com.aquariux.technical.test.repository;

import com.aquariux.technical.test.entity.BestAggregatePricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BestAggregatePriceRepository")
public interface BestAggregatePriceRepository extends JpaRepository<BestAggregatePricesEntity, String> {
}
