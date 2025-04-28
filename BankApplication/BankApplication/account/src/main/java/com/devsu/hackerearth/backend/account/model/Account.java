package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account extends Base {

    private String number;
    private String type;

    @Column(name = "initial_amount")
    private double initialAmount;

    private boolean isActive;

    @Column(name = "client_id")
    private Long clientId;

    @PrePersist
    public void prePersist() {
        setActive(true);
    }
}
