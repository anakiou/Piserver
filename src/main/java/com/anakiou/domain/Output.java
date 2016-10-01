package com.anakiou.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Output {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "ID", nullable = false, updatable = false)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "OUTPUT_NAME", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(unique = true, name = "OUTPUT_NUMBER", nullable = false)
    private Integer outputNumber;

    @Getter
    @Setter
    @Column(name = "OUTPUT_STATUS")
    private Integer outputStatus;

    @Getter
    @Setter
    @Column(name = "DATE_UPDATED", nullable = false)
    private Long dateUpdated;

    @PrePersist
    public void onPersist() {
        this.dateUpdated = System.currentTimeMillis();
    }

    @PreUpdate
    public void onUpdate() {
        this.dateUpdated = System.currentTimeMillis();
    }
}

