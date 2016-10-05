package com.anakiou.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"id"})
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
    @Column
    private String ioName;

    @Getter
    @Setter
    @Column(name = "IO_VALUE")
    private Integer ioValue;

    @Getter
    @Setter
    @Column(name = "IO_NUMBER")
    private Integer ioNumber;


    @Getter
    @Setter
    @Column(name = "DATE_CREATED", nullable = false)
    private Long dateCreated;

    @PrePersist
    public void onPersist() {
        this.dateCreated = System.currentTimeMillis();
    }

}
