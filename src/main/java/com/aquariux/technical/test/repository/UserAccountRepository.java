package com.aquariux.technical.test.repository;

import com.aquariux.technical.test.entity.UserAccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("UserAccountRepository")
public interface UserAccountRepository extends JpaRepository<UserAccountsEntity, Long> {
    Optional<UserAccountsEntity> findByUsername(String username);
}
