package com.nimbleways.springboilerplate.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.time.LocalDateTime;

// we can refactor entities using BaseEntity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//   we can add this important fields(it depends to business logic), but to avoid regression we are gonna comment its
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//    private String createdBy;
//    private String updatedBy;
//    private Boolean deleted = false; softDelete
//    @Version
//    private int version;
}
