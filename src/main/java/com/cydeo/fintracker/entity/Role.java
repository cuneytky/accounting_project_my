package com.cydeo.fintracker.entity;

import com.cydeo.fintracker.entity.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "description")
    private String description;

}
