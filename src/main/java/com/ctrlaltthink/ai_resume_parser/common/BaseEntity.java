package com.ctrlaltthink.ai_resume_parser.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreatedDate
    @Column(name="CREATED_DATE", updatable = false, nullable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(name="LAST_MODIFIED_DATE", insertable = false)
    private LocalDate lastModifiedDate;


    @CreatedBy
    @Column(name="CREATED_BY", nullable = false,updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name="MODIFIED_BY", insertable = false)
    private String modifiedBy;

}
