package com.copanote.SpringTest.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    @Column(updatable = false)
    private String createdBy;

    private LocalDateTime updatedDate;
    private String lastModifiedBy;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        createdBy = "CREATOR";
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
        lastModifiedBy = "UPDATOR";
    }


}
