package com.anakiou.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"id", "dateUpdated"})
public class Input {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "ID", nullable = false, updatable = false)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "INPUT_NAME", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(unique = true, name = "INPUT_NUMBER", nullable = false)
    private Integer inputNumber;

    @Getter
    @Setter
    @Column(name = "INPUT_STATUS")
    private Integer inputStatus;

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
