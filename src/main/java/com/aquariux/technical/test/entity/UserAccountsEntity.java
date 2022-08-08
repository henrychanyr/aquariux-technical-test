package com.aquariux.technical.test.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_accounts")
@Data
public class UserAccountsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "wallet_balance")
    private float walletBalance;
    @Column(name = "existing_btcusdt_quantity")
    private int existingBtcusdtQuantity;
    @Column(name = "existing_ethusdt_quantity")
    private int existingEthusdtQuantity;

    @CreationTimestamp
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @UpdateTimestamp
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
}
