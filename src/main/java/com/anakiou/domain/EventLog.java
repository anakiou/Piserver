package com.anakiou.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "ID", nullable = false, updatable = false)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false, updatable = false)
    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    @Column(name = "OUTPUT_VALUE")
    private Boolean outputValue;

    @Getter
    @Setter
    @Column(name = "PIN_NUMBER")
    private Integer pinNumber;

    @Getter
    @Setter
    @Column(name = "DATE_CREATED", nullable = false)
    private Long dateCreated;

    @PrePersist
    public void onPersist() {
        this.dateCreated = System.currentTimeMillis();
    }

}
